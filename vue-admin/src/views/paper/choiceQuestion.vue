<template>
    <div>
      <p class="title">题库</p>
         <el-form >
             <div>    
                  <span class="span1">
                    单选题:<span class="total">{{Choicetotal}}</span>个
                   <span >判断题:</span><span class="total">{{Judgetotal}}</span>个
                   总计:<span class="total">{{total}}</span>个
                  </span>  
                <!-- <el-checkbox-group   @change="change1"
                    v-model="checkedChoiceQuestions"
                    :max="6">
                   <el-checkbox :border="true" class="checkbox__label checkbox__inner"  v-for="choiceQuestion in choiceQuestions" :label="choiceQuestion.id" :key="choiceQuestion.id">{{choiceQuestion.question}} </el-checkbox>
                   
                </el-checkbox-group> -->
                   <span  class="span2 ">
              请 选 择 等 级 难 度 等 级   
                <el-select  size="mini" v-model="level" placeholder="请 选 择 "  @change="change">
                  <el-option 
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value" >
                  </el-option>
                </el-select>
                 <el-button class="buttonBtn" type="primary" @click="queding1();queding2()">保 存</el-button>
                </span> 
                <el-container style="margin-top:20px">
                <el-aside width="650px">
                   <div class="titleDX">单选题题库:</div>
                <el-collapse v-for="(choiceQuestion,index) in choiceQuestions"  :key="choiceQuestion.id">
                   <input type="checkbox" checked  class="checkboxDX"  v-model="checkedChoiceQuestions" :value="choiceQuestion.id">
                   <div class="collapseDX collapse1">
                   <el-collapse-item>
                     <template slot="title">
                       <span class="index">{{index}}</span>
                       {{choiceQuestion.question}}
                     </template>
                     <div class="collapse1">
                        <p>A.<span> {{choiceQuestion.optionA}}</span></p>
                        <p>B.<span> {{choiceQuestion.optionB}}</span></p>
                        <p>C.<span> {{choiceQuestion.optionC}}</span></p>
                        <p>D.<span> {{choiceQuestion.optionD}}</span></p>
                        <div>正确答案：<span> {{choiceQuestion.answer}}</span></div>
                        <div>题目解析：<span> {{choiceQuestion.analysis}}</span></div>
                     </div>
                   </el-collapse-item>
                   </div>            
                 </el-collapse>
                </el-aside>
                  <el-container>
 <el-main>
     <div class="titlePD">判断题题库:</div>
   <el-collapse  v-for="(choiceQuestion,index) in JudgeQuestions"  :key="choiceQuestion.id">
                   <input type="checkbox" checked  class="checkboxPD"  v-model="checkedJudgeQuestions" :value="choiceQuestion.id">
                   <div class="collapsePD collapse1">
                   <el-collapse-item>
                     <template slot="title">
                       <span class="index">{{index}}</span>
                       {{choiceQuestion.question}}
                     </template>
                     <div class="collapse1">
                        <p>A.<span> T</span></p>
                        <p>B.<span> F</span></p>
                        <div>正确答案：<span> {{choiceQuestion.answer}}</span></div>
                        <div>题目解析：<span> {{choiceQuestion.analysis}}</span></div>
                     </div>
                   </el-collapse-item>
                   </div>            
                 </el-collapse>
              </el-main>
              </el-container>
   </el-container>
                </div>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button class="button" type="primary" @click="queding1();queding2()">保 存</el-button>
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
                  checkedChoiceQuestions: [],
                  choiceQuestions: [],
                  Question:{
                     paperId:'',
                     doubleCheck:"0",
                     IsZOrS:"0",
                  },
                   JudgeQuestion:{
                     paperId:'',
                     doubleCheck:"1",
                     IsZOrS:"0",
                  },
                    options: [{
                      value: '全部',
                      label: '全部'
                    },{
                      value: '1',
                      label: '一星难度'
                    }, {
                      value: '2',
                      label: '二星难度'
                    }, {
                      value: '3',
                      label: '三星难度'
                    }, {
                      value: '4',
                      label: '四星难度'
                    }, {
                      value: '5',
                      label: '五星难度'
                    }],
                    level:"全部",
                    total:0,
                    Choicetotal:0,
                    Judgetotal:0
         }
     },
      watch:{
        checkedChoiceQuestions:function(new_v,old_v){
             this.Choicetotal=this.checkedChoiceQuestions.length
            // this.Judgetotal=this.checkedChoiceQuestions.length
            this.total=  this.Choicetotal+this.Judgetotal
            // console.log(  this.checkedChoiceQuestions.length);
        },
          checkedJudgeQuestions:function(new_v,old_v){
             this.Choicetotal=this.checkedChoiceQuestions.length
            this.Judgetotal=this.checkedJudgeQuestions.length
            this.total=  this.Choicetotal+this.Judgetotal
            // console.log(  this.checkedChoiceQuestions.length);
        },
    },
     methods:{
         change(value1){
            //  console.log(value1);
              paperQuestion.listByBankId(this.Question.paperId,value1).then(res=>{
                this.choiceQuestions=res.data.choiceQuestionServiceOne
            //  console.log(res);
           })
              paperQuestion.listByBankId(this.Question.paperId,value1).then(res=>{
                this.JudgeQuestions=res.data.judgeQuestion
            //  console.log(res);
          })
          },     
       listQuestion(){
             paperQuestion.listQuestion(this.Question.paperId,0,1).then(res=>{
              //  console.log(res.data);
               var arr = []
                  for (let i in res.data.paperQuestions) {
                      let aa = {};
                    this.checkedChoiceQuestions[i] = res.data.paperQuestions[i].questionId;            
                  }
                            //  console.log(this.checkedChoiceQuestions);
                   this.Choicetotal=this.checkedChoiceQuestions.length            
                   this.total=  this.Choicetotal+this.Judgetotal
                //  console.log(res.data.paperQuestions);                   
             })

              paperQuestion.listJudgeQuestion(this.Question.paperId,1,1).then(res=>{
            
               var arr = []
                  for (let i in res.data.paperJudgeQuestions) {
                      let aa = {};
                    this.checkedJudgeQuestions[i] = res.data.paperJudgeQuestions[i].questionId;
                  
                  }
                    //  console.log(res.data.paperJudgeQuestions);
                     this.Judgetotal=this.checkedJudgeQuestions.length
                      this.total=  this.Choicetotal+this.Judgetotal
                    //  console.log(this.checkedJudgeQuestions.length);
             })
       },
          queding1(){
            // this.Question=this.Question.concat(this.JudgeQuestion)
              // console.log(this.Question+"1111")
              // console.log(this.checkedChoiceQuestions+"2222")
              this.Question.IsZOrS="0"
              paperQuestion.addPaperQuestion(this.Question,this.checkedChoiceQuestions).then(res=>{
                        //  console.log("11");
              }) 
          // this.$router.push({path:"/paper/paper_question/"+this.paperId})
          // console.log(this.checkedChoiceQuestions);      
             
           },
           queding2(){    
                this.Question.IsZOrS="0"
                   paperQuestion.addPaperQuestion(this.JudgeQuestion,this.checkedJudgeQuestions).then(res=>{
                      this.$router.push({path:"/paper/paper_question/"+this.JudgeQuestion.paperId})
              }) 
                // console.log(this.JudgeQuestion+"33333");
                // console.log(this.checkedJudgeQuestions+"4444");
          // this.$router.push({path:"/paper/paper_question/"+this.paperId})
          // console.log(this.checkedChoiceQuestions);      
             
           },
         listByBankId(){
            paperQuestion.listByBankId(this.Question.paperId,this.level).then(res=>{
                this.choiceQuestions=res.data.choiceQuestionServiceOne
                this.JudgeQuestions=res.data.judgeQuestion
                // console.log(res.data);
                 })
          
      },
     },
     created(){
         this.Question.paperId= this.$route.params.paperId
         this.JudgeQuestion.paperId= this.Question.paperId
         this.listByBankId()
         this.listQuestion()
     } 
}
</script>
<style scoped>
.index{
   font-size: 15px;
   font-weight: 600;
   color: #409EFF;
   border:  1.5px  solid #409EFF;
   width:28px;
   height:20px;
   text-align: center;
   display:block;
   line-height: 19px;
   float: left;
   margin-top: 14px;
   margin-right: 14px;
  
   
}
.leixing{
    margin-left:55%;
    font-size: 15px;
    color: darkturquoise;
}
  .checkboxDX   {
     float: left;
    margin-top: 20px;
    margin-left: 1.5%;
    width: 20px;
    height: 20px;
    
  }
  .checkboxPD   {
     float: left;
    margin-top: 20px;
    margin-left: -1%;
    width: 20px;
    height: 20px;
    
  }
 .collapse  {
/*float: left;*/
     border: #909399 solid 1.5px;
     margin: 10px 8px 15px 21%;
     padding-left: 20px ;

  }
   .collapseDX  {
/*float: left;*/
     border: #909399 solid 1.5px;
     margin: 10px 8px 15px 5.9%;
     padding-left: 20px ;

  }
   .collapsePD  {
/*float: left;*/
     border: #909399 solid 1.5px;
     margin: 10px 8px 15px 4.2%;
     padding-left: 20px ;

  }
   .collapse1:hover{
     border: #0086b3 solid 2px;
     }
   .collapse1  /deep/ .el-collapse-item:last-child {
     margin-bottom: 10px;
   }
.total{
  font-size: 25px;
  color: rgb(8, 116, 240);
}
.titleDX{
 
   font-size: 18px;
   margin-left: 21%;
   font-weight: 400;
   margin-top: 5px;
}
.titlePD{
 
   font-size: 18px;
   margin-left: 21%;
   font-weight: 400;
   margin-top: -14px;
}
.title{
   font-size: 27px;
   margin-left: 45%;
   font-weight: 500;
}
.buttonBtn{
     float:right;
     font-size: 18px;
     margin-left:42%;
}
.button{
     font-size: 18px;
     margin-left:42%;
}
.span1{
  margin-left: 75%;
  font-size: 20px;
}
.span2{
  margin-left: 3%;
  font-size: 25px;
}

</style>