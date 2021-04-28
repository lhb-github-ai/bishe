<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新试卷</h2>
    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写试卷基本信息"/>
      <el-step title="选择试题"/>
      <el-step title="试卷设置及预览"/>
    </el-steps>
    <el-form label-width="120px">
    <el-form-item label="试卷名称:"   class="aaa">
      <el-input v-model="paper.paperName" style="   margin-left: 25px;" class="option-input"  placeholder="请输入试卷名称"></el-input>
    </el-form-item>
    <span class="left">请选择试卷类别:  &nbsp;</span>
        <el-select v-model="paper.bankId" placeholder="请选择试卷类型名称" class="bankId-input">
        <el-option
          v-for="item in banks"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
    </el-select>
    <el-form-item label="试卷描述:"   class="aaa">
          <el-input class="option-input1"
               type="textarea"
               placeholder="请输入试卷描述"
               v-model="paper.description"
               maxlength="30"
               show-word-limit=true
               >
          </el-input>
    </el-form-item>
    <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpadte">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import paper  from '@/api/paper'
import bank  from '@/api/bank'
export default {
     data(){
       return{
                  saveBtnDisabled:false,
                  paper:{},
                  banks:[],
                  textarea: ''
       }
     },
     methods:{
       saveOrUpadte(){
              if(this.paper.id) {
                 this.update()
              }else{
                this.save()
              }
       },
       update(){
              paper.updatePaper(this.paper).then(res=>{
                      if (res.data.repeat==='repeat'){
                this.$alert('此试卷名称已经存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })
              }else {
                this.$alert('修改成功,点击跳转到下一个步骤', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                   this.$router.push({path:"/paper/paper_question/"+this.paper.id})
                  }
                })
              }
              })
       },
       save(){
       var role=  localStorage.getItem('role')
       var id = localStorage.getItem('id')
       if(role=="1"){
         this.paper.authorId=id
       }
       if(role=="0"){
         this.paper.authorId="0000000000"
       }
       this.paper.total_score="0"
       this.paper.time="0"
         paper.addPaper(this.paper).then(res=>{
                  if (res.data.repeat==='repeat'){
                this.$alert('此试卷名称已经存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })
              }else {
                this.$alert('添加成功,点击跳转到下一个步骤', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                   this.$router.push({path:"/paper/paper_question/"+res.data.paperId})
                  }
                })
              }
         })
          
       },
      findAllBank(){
            bank.findAllList().then(res=>{
              // console.log(res.data);
              this.banks=res.data.list
            })
       },
       getOneById(paperId){
             paper.findById(paperId).then(res=>{
                  this.paper=res.data.paper
                  // console.log(this.paper);
             })
       }
     },
     created(){
       this.findAllBank()
        if (this.$route.params && this.$route.params.paperId) {
                      const paperId = this.$route.params.paperId
                            this.getOneById(paperId)
                          // console.log(this.$route.params.id);
              }
     }
}
</script>
<style scoped>
.option-input /deep/  .el-input__inner{
 border-color: #303133;
 width: 300px;
 height: 45px;
  color: black;
}
.option-input1 /deep/ .el-textarea__inner{
   border:1px solid #303133;;
   width: 300px;
   height: 120px;
   margin-left: 25px;
   margin-top: 7px;
   color: black;
}

.aaa  >>> .el-form-item__label{
   color: black;
   font-weight: 52px;
   text-align: center;
   font-size: 17px;
}
.left{

    padding-left: 15px;
    color: black;
    font-weight: 550;
 
}   

  .bankId-input >>> .el-input--suffix .el-input__inner{
            margin-bottom: 15px;
            margin-top:10px;
            border-color: #303133;
            width: 300px;
             color: black;

  }
</style>