<template>
    <div>
      <p>判断题题库</p></p>
         <el-form >
             <div style="margin-top: 20px">
              <span class="span1">题目</span>  
              <span  class="span2 ">
              请 选 择 等 级    
                <el-select  size="medium" v-model="level" placeholder="请 选 择 等 级"  @change="change">
                  <el-option class="span3"
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value" >
                  </el-option>
                </el-select>
                </span> 
                  <span class="span1">总 数 ：<span >{{total}}</span> 个</span>  
                <el-checkbox-group  @change="change1"
                    v-model="checkedJudgeQuestions"
                    :min="1"
                    :max="6">
                   <el-checkbox :border="true" class="checkbox__label checkbox__inner"  v-for="JudgeQuestion in JudgeQuestions" :label="JudgeQuestion.id" :key="JudgeQuestion.id">{{JudgeQuestion.question}} </el-checkbox>
                   
                </el-checkbox-group>
                </div>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button class="button" type="primary" @click="queding1">提  交</el-button>
            </div>
    </div>
</template>
<script>

 import paperQuestion from '@/api/paperQuestion'
export default {
     data(){
         return{
                  checkedJudgeQuestions: [],
                  JudgeQuestions: [],
                  Question:{
                     paperId:'',
                     doubleCheck:"1",
                  },
                   options: [{
                      value: '全部',
                      label: '全部'
                    },{
                      value: '1',
                      label: '一'
                    }, {
                      value: '2',
                      label: '二'
                    }, {
                      value: '3',
                      label: '三'
                    }, {
                      value: '4',
                      label: '四'
                    }, {
                      value: '5',
                      label: '五'
                    }],
                    level:"全部",
                    total:"0"
         }
     },
     methods:{
       change1(value1){
            this.total=value1.length
          //  console.log(value1.length);
       },
         change(value1){
            //  console.log(value1);
              paperQuestion.listByBankId(this.Question.paperId,value1).then(res=>{
                this.JudgeQuestions=res.data.judgeQuestion
            //  console.log(res);
          })
          },     
    listJudgeQuestion(){
         paperQuestion.listJudgeQuestion(this.Question.paperId,1).then(res=>{
            
               var arr = []
                  for (let i in res.data.paperJudgeQuestions) {
                      let aa = {};
                    this.checkedJudgeQuestions[i] = res.data.paperJudgeQuestions[i].questionId;
                  
                  }
                    //  console.log(res.data.paperJudgeQuestions);
                    this.total=this.checkedJudgeQuestions.length
                     console.log(this.checkedJudgeQuestions.length);
             })
       },

          queding1(){
            this.doubleCheck="2"
              paperQuestion.addPaperQuestion(this.Question,this.checkedJudgeQuestions).then(res=>{
                              this.$router.push({path:"/paper/paper_question/"+this.Question.paperId})
              }) 
        //   this.$router.push({path:"/paper/paper_question/"+this.paperId})
          // console.log(this.checkedChoiceQuestions);      
           },
         
         listByBankId(){
          //  console.log(this.level);
            paperQuestion.listByBankId(this.Question.paperId,this.level).then(res=>{
                this.JudgeQuestions=res.data.judgeQuestion
            //  console.log(res);
          })
      },
     },
     created(){
         this.Question.paperId= this.$route.params.paperId
         this.listByBankId()
         this.listJudgeQuestion()
     } 
}
</script>
<style scoped>
p{
  font-size: 30px;
  margin-left: 500px;
   font-weight: 600;
}
.button{
     font-size: 18px;
     margin-left:42%;
}
.span1{

  margin-left: 4%;
  font-size: 28px;
  font-weight: 500;
}
.span2{

  margin-left: 70%;
  font-size: 28px;
  font-weight: 500;

}


.el-checkbox{
   display: block;
   margin-left: 10px;
   margin-top: 12px;
   padding: 35px;
 

}

.checkbox__label >>> .el-checkbox__label{
    font-size: 25px;

    
}
</style>