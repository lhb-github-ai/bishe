<template>
<el-row :gutter="20">
 <el-col :span="6"><div class="grid-content "></div>
  <router-link :to="'/question/batch'">
    <el-button type="primary"  class="btn1">点击此处进行批量添加</el-button>
  </router-link>
   </el-col>
 <el-col :span="10">
 <el-form :model="choiceQuestion" label-width="100px" class="demo-ruleForm">
   <span class="left">请选择题库名  &nbsp; &nbsp;</span>
    <el-select v-model="choiceQuestion.bankId" placeholder="请选择题库名" class="bankId-input">
    <el-option
      v-for="item in banks"
      :key="item.id"
      :label="item.name"
      :value="item.id">
    </el-option>
    </el-select>
    <el-form-item label="题目内容" prop="question" class="aaa">
      <el-input v-model="choiceQuestion.question" placeholder="请输入题目内容" class="question-input " :rows="4" resize="none" size="medium" type="textarea" ></el-input>
    </el-form-item>
    <el-form-item label="选项A" prop="optionA"  class="aaa">
      <el-input v-model="choiceQuestion.optionA" class="option-input" placeholder="请输入选项A"></el-input>
    </el-form-item>
    <el-form-item label="选项B" prop="optionB" class="aaa">
      <el-input v-model="choiceQuestion.optionB" class="option-input" placeholder="请输入选项B"></el-input>
    </el-form-item>
    <el-form-item label="选项C" prop="optionC" class="aaa">
      <el-input v-model="choiceQuestion.optionC" class="option-input" placeholder="请输入选项C"></el-input>
    </el-form-item>
    <el-form-item label="选项D" prop="optionD" class="aaa">
      <el-input v-model="choiceQuestion.optionD" class="option-input" placeholder="请输入选项D"></el-input>
    </el-form-item>
    <el-form-item label="正确答案" prop="answer" class="aaa">
      <el-input v-model="choiceQuestion.answer"  class="option-input" placeholder="请输入正确答案"></el-input>
    </el-form-item>
     <el-form-item label="答案解析" prop="analysis" class="aaa">
      <el-input v-model="choiceQuestion.analysis" class="question-input " placeholder="请输入答案解析" :rows="4" resize="none" size="medium" type="textarea" ></el-input>
    </el-form-item>
      <span class="left">请选择难度等级</span>
    <el-select v-model="choiceQuestion.level" placeholder="请选择难度等级" class="bankId-input" value="1">
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>
    <br>
    <el-button type="primary" @click="saveOrUpdate()" class="btn">添加</el-button>
  </el-form>
 </el-col>
</el-row>
</template>

<script>
import choiceQuestion from '@/api/ChoiceQuestion'  
import bank  from '@/api/bank'
  export default {
    data() {
      return {
        choiceQuestion: {
          id:"",
          bankName:"",
          question:"",
          questionA:"",
          questionB:"",
          questionC:"",
          questionD:"",
          answer:"",
          analysis:"",
          level:""
        },
          options: [{
          value: '1',
          label: '1'
        }, {
          value: '2',
          label: '2'
        }, {
          value: '3',
          label: '3'
        }, {
          value: '4',
          label: '4'
        }, {
          value: '5',
          label: '5'
        }],
        banks:[]
      };
    },
    methods: {
      update(){
           choiceQuestion.update(this.choiceQuestion).then(res=>{
          // console.log(res);
              if (res.data.repeat==='repeat'){
                this.$alert('该题目已存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })
              }else if(res.data.nonExistent==='nonExistent'){
                 this.$alert('此题目所属题库名不存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                })
              } else {
                this.$alert('修改成功,点击跳转到题库列表', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push("/question/choiceQuestion");
                  }
                })
              }
        })
      },
      saveOrUpdate(){
            if(!this.choiceQuestion.id){  
                this.submitForm()
             }else{
               this.update()
             }
      },
      submitForm(){
                 choiceQuestion.addChoiceQuestion(this.choiceQuestion).then(res=>{
              if (res.data.repeat==='repeat'){
                this.$alert('此题目已经存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })
              }else {
                this.$alert('添加成功,点击跳转到单选题列表', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push("/question/choiceQuestion");
                  }
                })
              }
        })
       
      },
      getOneById(id){
        choiceQuestion.listById(id).then(res=>{
          this.choiceQuestion=res.data.choiceQuestion
          //  console.log(res);
        })
      },
      init(){
          if (this.$route.params && this.$route.params.id) {
                      const id = this.$route.params.id
                            this.getOneById(id)
                          // console.log(this.$route.params.id);
              }else{
                      this.choiceQuestion={}
                   }   
      },
       findAllBank(){
            bank.findAllList().then(res=>{
              // console.log(res.data);
              this.banks=res.data.list
            })
       }
    },
    created(){
       this.init()
       this.findAllBank()
    },
       watch:{
         $route(to,from){
             this.init();
         }
    },
    
  }
</script>
<style scoped>


.question-input >>> .el-textarea__inner{
  height: 90px;
  width: 70%;
 border-color: #303133;
 
}
/* el-textarea */
/*  */
.option-input >>> .el-input__inner{
 border-color: #303133;
}
.analysis-input >>> .el-input__inner{
 border-color: #303133;
}

.aaa  >>> .el-form-item__label{
   color: black;
   font-weight: 52px;
   text-align: center;
}


.left{

    padding-left: 15px;
    color: black;
    font-weight: 550;
 
}   
  .grid-content {
    min-height: 1px;
  }
  .bankId-input >>> .el-input--suffix .el-input__inner{
          margin-bottom: 15px;
           margin-top:10px;
            border-color: #303133;

  }
  .btn{
   margin-left: 50%;
   font-size: 20px;
   
  }
  .btn1{
    margin-top: 20px;
    margin-left: 22px;
  }
</style>
