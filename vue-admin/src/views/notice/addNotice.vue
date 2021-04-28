<template>
  <el-row :gutter="20">
 <el-col :span="5"><div class="grid-content "></div>
  <!-- <router-link :to="'/student/batch'">
    <el-button type="primary"  class="btn1">点击此处进行批量添加</el-button>
  </router-link> -->
  <div>&nbsp;</div>
   </el-col>
 <el-col :span="8" style="margin-top:70px">
     <p><b style="font-size:20px;margin-left:170px;">添加公告</b></p>
 <el-form :model="notice" :rules="rules" ref="notice" label-width="100px" class="demo-ruleForm">
    <el-form-item label="公告标题" prop="noticeTitle"  class="aaa">
      <el-input v-model="notice.noticeTitle" class="option-input" placeholder="请输入公告标题"></el-input>
    </el-form-item>
     <el-form-item label="公告内容" prop="notice" class="aaa">
      <el-input v-model="notice.notice" class="question-input " placeholder="请输入公告内容" :rows="6" resize="none" size="medium" type="textarea" ></el-input>
    </el-form-item>
    <!-- <el-form-item label="类型数量" prop="bankCount"  class="aaa">
      <el-input v-model="ruleForm.bankCount" readonly class="option-input"></el-input>
    </el-form-item> -->
    <el-button style="margin-left:140px;margin-top:20px" type="primary" @click="saveOrUpdate()">保存</el-button>
    <el-button @click="resetForm()">重置</el-button>
  </el-form>
 </el-col>
</el-row>
</template>

<script>
  import bank  from '@/api/bank'
    import notice  from '@/api/notice'
  export default {
    data() {
      return {
        notice: {
          id: '',
          noticeTitle: '',
          notice: '',
          adminId:''
        },
        rules: {
          name: [
            { required: true, message: '请输入类型名称', trigger: 'blur' },
          ],
        }
      };
    },
    methods: {
        resetForm(){
         this.notice={}
      }, 
      saveOrUpdate(){
         if(!this.notice.id){  
                this.addNotice()
             }else{
               this.updateNotice()
             }
      },
      updateNotice(){
          console.log(this.notice);
        notice.updateNotice(this.notice).then(res=>{
          console.log(res);
              if (res.data.repeat==='repeat'){
                this.$alert('此题库已经存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })
              }else {
                this.$alert('修改成功,点击跳转到公告列表界面', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push("/notice/index");
                  }
                })
              }
        })
      },
      addNotice(){
          console.log(this.notice);
        notice.addNotice(this.notice).then(res=>{
          // console.log(res);
              if (res.data.repeat==='repeat'){
                this.$alert('此类型已经存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })
              }else {
                this.$alert('添加成功,点击跳转到公告列表页面', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push("/notice/index");
                  }
                })
              }
        })
      },
      getOneById(id){
          notice.listById(id).then(res=>{
            // console.log(res);
            this.notice=res.data.notice
          })
      },
      init(){
            if (this.$route.params && this.$route.params.id) {
                      const id = this.$route.params.id
                            this.getOneById(id)
                          // console.log(this.$route.params.id);
              }else{
                      this.ruleForm={}
                   }
         }
    },
     watch:{
         $route(to,from){
             this.init();
         }
    },
    created(){
       this.init();
       this.notice.adminId=localStorage.getItem("id")
    }
  }
</script>
<style scoped>
.option-input >>> .el-input__inner{
 border-color: #303133;
}
.aaa  >>> .el-form-item__label{
   color: black;
   font-weight: 52px;
   text-align: center;
}
.question-input >>> .el-textarea__inner{
  height: 400px;
  width: 120%;
 border-color: #303133;
 
}
</style>
