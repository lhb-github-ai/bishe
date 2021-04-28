<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新试卷</h2>
    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写试卷基本信息"/>
       <el-step title="选择试题"/>
      <el-step title="试卷设置及预览"/>
    </el-steps>
    <p>添加试题到试卷: 《<span>{{paper.paperName}}</span>》</p>
    <el-form label-width="120px">
      <el-container>
      <el-main class="main" >
        <p class="zujuan">组卷方式</p>
          <div  class="div1">
           <a href="#"> <el-radio v-model="radio1" label="1" border @change="zidingyibtn" class="el-radio">自定义组卷</el-radio></a>
           <a href="#">   <el-radio v-model="radio1" label="2" border @change="suijibtn">随机组卷</el-radio></a>
          </div>
          <!-- 随机组卷 -->
          <div  v-show="suiji">
             <div class="div2">
              <p>
                 <span class="select">请选择是浏览单选还是判断：</span>
                  <el-select v-model="value" placeholder="请选择" @change="change">
                    <el-option  
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                   </el-option>
                  </el-select></p>         
                 <el-button class="el-button3" round type="primary" size="small " @click="suiji_question=true"><span class="icon iconfont icon-jiahao"></span> 填写随机组卷内容</el-button>
                 <el-button class="el-button3" round type="warning" size="small " @click="deletePaper"><span class="icon iconfont icon-jiahao"></span> 删除试题</el-button>
                 <div class="number">
                        单选题:<span class="gongji">{{SJChoicetotal}}</span>&#8197;题   &#8197;
                        判断题:<span class="gongji">{{SJJudgetotal}}</span>&#8197;题   &#8197;
                   现在共计<span class="gongji">{{SJtotal}}</span>&#8197;题
                 </div>
                 <div v-show="SJChoicetotal>0||SJJudgetotal>0">
                 <el-collapse v-for="(choiceQuestion,index) in SJchoiceQuestions"  :key="choiceQuestion.id" v-show="danxuan">
                   <input type="checkbox" class="checkbox" :value="choiceQuestion.id" v-model="checkedJudgeQuestions" >
                   <div class="collapse collapse1">
                   <el-collapse-item>
                     <template slot="title">
                    <span class="index">{{index}}</span>
                      {{choiceQuestion.questionName}}
                       <span class="leixing">所属类型:(<span>{{paper.bankName}}</span>)</span>
                           <el-tooltip content="修改改试题" placement="top">
                             <span @click.stop="updateChoicePaper" class="tubiao"><i class="el-icon-edit"></i></span>
                           </el-tooltip>
                           <el-tooltip content="从该试题卷中删除该试题" placement="top">
                             <span class="tubiao1" @click.stop="DeleteChoicePaper(choiceQuestion.id)"><i class="el-icon-delete"></i></span>
                           </el-tooltip>
                     </template>
                     <div class="collapse1">
                        <p>A.<span> {{choiceQuestion.optionA}}</span></p>
                        <p>B.<span> {{choiceQuestion.optionB}}</span></p>
                        <p>C.<span> {{choiceQuestion.optionC}}</span></p>
                        <p>D.<span> {{choiceQuestion.optionD}}</span></p>
                        <div class="answer">正确答案：<span class="answer1"> {{choiceQuestion.answer}}</span></div>
                        <div class="analysis">题目解析：<span> {{choiceQuestion.analysis}}</span></div>
                          <div class="updateTime">更新时间<span> {{choiceQuestion.updateTime}}</span></div>
                     </div>
                   </el-collapse-item>
                   </div>            
                 </el-collapse>
                </div>
              <div  v-if="SJchoiceQuestions==0&&SJJudgetotal==0">
               <img src="/static/kong.png">
               <div class="tips">点击<span class="tip">“添加试题“</span>,可题库随机选题、手工选题、直接编辑试题、Excel文档一键导入，每个人考试的试题一样！</div>
              </div>
                 <!-- 判断 -->
                 <el-collapse v-for="(choiceQuestion,index) in SJjudgeQuestion"  :key="choiceQuestion.id" v-show="panduan">
                <input type="checkbox" class="checkbox" :value="choiceQuestion.id" v-model="checkedJudgeQuestions" >
                   <div class="collapse collapse1">
                   <el-collapse-item>
                     <template slot="title">
                    <span class="index">{{index}}</span>
                      {{choiceQuestion.questionName}}
                       <span class="leixing">所属类型:(<span>{{paper.bankName}}</span>)</span>
                           <el-tooltip content="修改改试题" placement="top">
                             <span @click.stop="updateChoicePaper" class="tubiao"><i class="el-icon-edit"></i></span>
                           </el-tooltip>
                           <el-tooltip content="从该试题卷中删除该试题" placement="top">
                             <span class="tubiao1" @click.stop="DeleteJudgePaper(choiceQuestion.id)"><i class="el-icon-delete"></i></span>
                           </el-tooltip>
                     </template>
                     <div class="collapse1">
                        <p>A.<span> T</span></p>
                        <p>B.<span> F</span></p>
                        <div class="answer">正确答案：<span class="answer1"> {{choiceQuestion.answer}}</span></div>
                        <div class="analysis">题目解析：<span> {{choiceQuestion.analysis}}</span></div>
                        <div class="updateTime">更新时间<span> {{choiceQuestion.updateTime}}</span></div>
                     </div>
                   </el-collapse-item>
                   </div>            
                 </el-collapse>
             </div>
            <div class="suiji" v-if="false">
               <img src="/static/kong.png">
               <div class="tips">点击<span class="tip">“添加试题“</span>,可题库随机选题、手工选题、直接编辑试题、Excel文档一键导入，每个人考试的试题一样！</div>
             </div>  
          </div>
      </el-main>
      <el-aside width="270px">
        <div class="aside"> 
          <div class="asidePaper"> <p >试卷基本信息</p></div>
          <div class="time"><span class="time1">试卷名称</span></div>
          <div class="time2">{{paper.paperName}}</div>
            <div class="time">
               <span class="time3">总分</span>
               <span class="time4">考试时长</span>
               <span class="time5">类型名称</span>
              </div>
                 <span class="time6">{{paper.totalScore}}</span>
                 <span class="time7"><span>{{paper.time}}</span>分钟</span>
                 <span class="time8">{{paper.bankName}}</span>
          <el-button @click="previous" size="small"  type="primary" class="xiugaiBtn">返回编辑基本信息</el-button>
        </div>   
        </el-aside>
      </el-container>
         <el-form-item>
          <div class="el-button1 ">
            <el-button @click="previous"  type="primary">上一步</el-button>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
          </div>
       </el-form-item>
    </el-form>

    <!-- 弹框 -->
            <el-dialog title="填写随机试卷相关信息" :visible.sync="suiji_question" :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false">
             <div class="suijiTK">
               <div class="div1">
                  <el-radio v-model="radio2" label="1" border @change="danxuanti" style="margin-left:30px;">单选题</el-radio>
                  <el-radio v-model="radio2" label="2" border @change="panduanti">判断题</el-radio>
             </div>
               <el-form v-show="DXNum">
                <el-form-item label="请填写试卷单选题为一星难度的题目数量">
                <el-col :span="8">
                <el-input v-model="suijiNum.DXone" type= "text" @blur="suijiNumOne($event)"></el-input>
                </el-col>
               <el-col :span="2">
                 <div class="suijiTM">共有<span class="gongji">{{NumBerDX.NumOne}}</span>个</div>
               </el-col>
            </el-form-item>
             <el-form-item label="请填写试卷单选题为二星难度的题目数量" >
               <el-col :span="8">
              <el-input v-model="suijiNum.DXtwo" type= "text"  @blur="suijiNumTwo($event)"></el-input>
              </el-col>
              <el-col :span="2">
                 <div class="suijiTM">共有<span class="gongji">{{NumBerDX.NumTwo}}</span>个</div>
               </el-col>
            </el-form-item>
             <el-form-item label="请填写试卷单选题为三星难度的题目数量" >
              <el-col :span="8">
              <el-input v-model="suijiNum.DXthree" type= "text" @blur="suijiNumThree($event)"></el-input>
              </el-col>
              <el-col :span="2">
                 <div class="suijiTM">共有<span class="gongji">{{NumBerDX.NumThree}}</span>个</div>
               </el-col>
            </el-form-item>
             <el-form-item label="请填写试卷单选题为四星难度的题目数量" >
              <el-col :span="8">
              <el-input v-model="suijiNum.DXfour" type= "text" @blur="suijiNumFour($event)"></el-input>
              </el-col>
              <el-col :span="2">
                 <div class="suijiTM">共有<span class="gongji">{{NumBerDX.NumFour}}</span>个</div>
               </el-col>
            </el-form-item>
             <el-form-item label="请填写试卷单选题为五星难度的题目数量" >
              <el-col :span="8">
              <el-input v-model="suijiNum.DXfive" type= "text" @blur="suijiNumFive($event)"></el-input>
              </el-col>
              <el-col :span="2">
                 <div class="suijiTM">共有<span class="gongji">{{NumBerDX.NumFive}}</span>个</div>
               </el-col>
            </el-form-item>
          </el-form>
           <el-form v-show="PDNum">
             <el-form-item label="请填写试卷判断题为一星难度的题目数量">
                <el-col :span="8">
                <el-input v-model="suijiNum.PDone" type= "text" @blur="suijiNumPDOne($event)"></el-input>
                </el-col>
               <el-col :span="2">
                 <div class="suijiTM">共有<span class="gongji">{{NumBerPD.NumOne}}</span>个</div>
               </el-col>
            </el-form-item>
             <el-form-item label="请填写试卷判断题为二星难度的题目数量" >
               <el-col :span="8">
              <el-input v-model="suijiNum.PDtwo" type= "text"  @blur="suijiNumPDTwo($event)"></el-input>
              </el-col>
              <el-col :span="2">
                 <div class="suijiTM">共有<span class="gongji">{{NumBerPD.NumTwo}}</span>个</div>
               </el-col>
            </el-form-item>
             <el-form-item label="请填写试卷判断题为三星难度的题目数量" >
              <el-col :span="8">
              <el-input v-model="suijiNum.PDthree" type= "text" @blur="suijiNumPDThree($event)"></el-input>
              </el-col>
              <el-col :span="2">
                 <div class="suijiTM">共有<span class="gongji">{{NumBerPD.NumThree}}</span>个</div>
               </el-col>
            </el-form-item>
             <el-form-item label="请填写试卷判断题为四星难度的题目数量" >
              <el-col :span="8">
              <el-input v-model="suijiNum.PDfour" type= "text" @blur="suijiNumPDFour($event)"></el-input>
              </el-col>
              <el-col :span="2">
                 <div class="suijiTM">共有<span class="gongji">{{NumBerPD.NumFour}}</span>个</div>
               </el-col>
            </el-form-item>
             <el-form-item label="请填写试卷判断题为五星难度的题目数量" >
              <el-col :span="8">
              <el-input v-model="suijiNum.PDfive" type= "text" @blur="suijiNumPDFive($event)"></el-input>
              </el-col>
              <el-col :span="2">
                 <div class="suijiTM">共有<span class="gongji">{{NumBerPD.NumFive}}</span>个</div>
               </el-col>
            </el-form-item>
           </el-form>
        </div>
          <div slot="footer" class="dialog-footer">
            <el-button @click="suiji_question = false">取 消</el-button>
            <el-button type="primary" @click="add_suiji_question()">确 定</el-button>
           </div>
        </el-dialog>
  </div>
</template>
<script>
import paperQuestion from '@/api/paperQuestion'
import ChoiceQuestion from '@/api/ChoiceQuestion'
import JudgeQuestion from '@/api/JudgeQuestion'
export default {
     data(){
       return{
                  PDNum:false,
                  DXNum:true,
                  suijiNum:{
                     DXone:0,
                     DXtwo:0,
                     DXthree:0,
                     DXfour:0,
                     DXfive:0,
                     PDone:0,
                     PDtwo:0,
                     PDthree:0,
                     PDfour:0,
                     PDfive:0,
                     paperId:""
                  },
                  NumBerDX:{
                      NumOne:2,
                      NumTwo:2,
                      NumThree:2,
                      NumFour:2,
                      NumFive:2,
                  },
                   NumBerPD:{
                      NumOne:2,
                      NumTwo:2,
                      NumThree:2,
                      NumFour:2,
                      NumFive:2,
                  },
                  suiji_question:false,
                  checkedJudgeQuestions: [],
                  checkedChoiceQuestions: [],
                  panduan:false,
                  danxuan:true,
                  zidingyi:false,
                  suiji:true,
                  radio1: '2',
                  radio2:"1",
                  saveBtnDisabled:false,
                  paperId:"",
                  choiceQuestions:[],
                  SJchoiceQuestions:[],
                  judgeQuestion:[],
                  SJjudgeQuestion:[],
                  options: [{
                      value: '单选题',
                      label: '单选题'
                    }, {
                      value: '判断题',
                      label: '判断题'
                    }],
                    value: '单选题',
                    total:0,
                    SJtotal:0,
                    Choicetotal:0,
                    SJChoicetotal:0,
                    Judgetotal:0,
                    SJJudgetotal:0,
                    paper:{},
       }
     },
     methods:{
       listSuJiPDQuestion(){
             paperQuestion.listQuestion(this.paperId,0,0).then(res=>{
                // console.log(res);
                this.SJchoiceQuestions=res.data.paperQuestions
                this.SJChoicetotal=this.SJchoiceQuestions.length
                this.SJtotal=this.SJChoicetotal+this.SJJudgetotal
                // console.log(this.choiceQuestions.length);
             })
             paperQuestion.listByPaperId(this.paperId).then(res=>{
               this.paper=res.data.paper
              //  console.log(this.paper);
             })
       },
        listSuJiDXQuestion(){
             paperQuestion.listJudgeQuestion(this.paperId,1,0).then(res=>{
                // console.log(res);
                  this.SJjudgeQuestion=res.data.paperJudgeQuestions
                  this.SJJudgetotal=this.SJjudgeQuestion.length
                  this.SJtotal=this.SJChoicetotal+this.SJJudgetotal
             })
       },
       add_suiji_question(){
           if(this.SJchoiceQuestions!=0||this.SJjudgeQuestion!=0){
                this.$confirm('随机试卷中已经有题目了，继续将会删除随机试卷中的所有题目之后再随机生成，是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    showClose:false,
                    closeOnClickModal:false
                  }).then(() => {
                     this.suijiNum.paperId=this.paperId
                   paperQuestion.addSuiJiPaperQuestion(this.suijiNum).then(res=>{
                        this.suiji_question = false
                        this.listSuJiPDQuestion()
                        this.listSuJiDXQuestion()
          })
                  }).catch(() => {
                
                  });
           }else{
            //  console.log(this.suijiNum);
                   this.suijiNum.paperId=this.paperId
                   paperQuestion.addSuiJiPaperQuestion(this.suijiNum).then(res=>{
                        this.suiji_question = false
                        this.listSuJiPDQuestion()
                        this.listSuJiDXQuestion()
          })
           }
       },
       danxuanti(){
          this.PDNum=!this.PDNum
          this.DXNum=!this.DXNum
       },
      panduanti(){
          this.PDNum=!this.PDNum
          this.DXNum=!this.DXNum
       },
       CreateByLv(){ 
              ChoiceQuestion.listByLV(1,this.paperId).then(res=>{
                        this.NumBerDX.NumOne=res.data.number
           })
             ChoiceQuestion.listByLV(2,this.paperId).then(res=>{
                        this.NumBerDX.NumTwo=res.data.number
           })
             ChoiceQuestion.listByLV(3,this.paperId).then(res=>{
                        this.NumBerDX.NumThree=res.data.number
           })
             ChoiceQuestion.listByLV(4,this.paperId).then(res=>{
                        this.NumBerDX.NumFour=res.data.number
           })
             ChoiceQuestion.listByLV(5,this.paperId).then(res=>{
                        this.NumBerDX.NumFive=res.data.number
           })
             JudgeQuestion.listByLV(1,this.paperId).then(res=>{
                        this.NumBerPD.NumOne=res.data.number
           })
             JudgeQuestion.listByLV(2,this.paperId).then(res=>{
                        this.NumBerPD.NumTwo=res.data.number
           })
             JudgeQuestion.listByLV(3,this.paperId).then(res=>{
                        this.NumBerPD.NumThree=res.data.number
           })
             JudgeQuestion.listByLV(4,this.paperId).then(res=>{
                        this.NumBerPD.NumFour=res.data.number
           })
             JudgeQuestion.listByLV(5,this.paperId).then(res=>{
                        this.NumBerPD.NumFive=res.data.number
           })
            },
       suijiNumOne(e){
             let boolean=new RegExp("^[0-9][0-9]*$").test(this.suijiNum.DXone)
             if(!boolean){
               this.$message.warning('请输入正整数');
               this.suijiNum.DXone=0
             }else{
                        if(parseInt(this.suijiNum.DXone)>parseInt(this.NumBerDX.NumOne)){
                           this.$alert('', '输入的随机数量有误请重新输入', {
                                confirmButtonText: '确定',
                                showClose:false,
                                   callback: action => {
                                      this.suijiNum.DXone=0
                                   }
                              });
                               
                        }
             }
       },
        suijiNumPDOne(e){
             let boolean=new RegExp("^[0-9][0-9]*$").test(this.suijiNum.PDone)
             if(!boolean){
               this.$message.warning('请输入正整数');
               this.suijiNum.PDone=0
             }else{
                        if(parseInt(this.suijiNum.PDone)>parseInt(this.NumBerPD.NumOne)){
                           this.$alert('', '输入的随机数量有误请重新输入', {
                                confirmButtonText: '确定',
                                showClose:false,
                                   callback: action => {
                                      this.suijiNum.PDone=0
                                   }
                              });
                               
                        }
             }
       },
          suijiNumTwo(e){
             let boolean=new RegExp("^[0-9][0-9]*$").test(this.suijiNum.DXtwo)
             if(!boolean){
               this.$message.warning('请输入正整数');
               this.suijiNum.DXtwo=0
             }else{
                        if(parseInt(this.suijiNum.DXtwo)>parseInt(this.NumBerDX.NumTwo)){
                           this.$alert('', '输入的随机数量有误请重新输入', {
                                confirmButtonText: '确定',
                               showClose:false,
                                   callback: action => {this.suijiNum.DXtwo=0}
                              });
                           
                        }
             }
       },
           suijiNumPDTwo(e){
             let boolean=new RegExp("^[0-9][0-9]*$").test(this.suijiNum.PDtwo)
             if(!boolean){
               this.$message.warning('请输入正整数');
               this.suijiNum.PDtwo=0
             }else{
                        if(parseInt(this.suijiNum.PDtwo)>parseInt(this.NumBerPD.NumTwo)){
                           this.$alert('', '输入的随机数量有误请重新输入', {
                                confirmButtonText: '确定',
                               showClose:false,
                                   callback: action => {this.suijiNum.PDtwo=0}
                              });
                           
                        }
             }
       },
        suijiNumThree(e){
             let boolean=new RegExp("^[0-9][0-9]*$").test(this.suijiNum.DXthree)
             if(!boolean){
               this.$message.warning('请输入正整数');
              this.suijiNum.DXthree=0
             }else{
                        if(parseInt(this.suijiNum.DXthree)>parseInt(this.NumBerDX.NumThree)){
                           this.$alert('', '输入的随机数量有误请重新输入', {
                                confirmButtonText: '确定',
                               showClose:false,
                                  callback: action => {this.suijiNum.DXthree=0}
                              });
                        }
             }
       },
            suijiNumPDThree(e){
             let boolean=new RegExp("^[0-9][0-9]*$").test(this.suijiNum.PDthree)
             if(!boolean){
               this.$message.warning('请输入正整数');
              this.suijiNum.PDthree=0
             }else{
                        if(parseInt(this.suijiNum.PDthree)>parseInt(this.NumBerPD.NumThree)){
                           this.$alert('', '输入的随机数量有误请重新输入', {
                                confirmButtonText: '确定',
                               showClose:false,
                                  callback: action => {this.suijiNum.PDthree=0}
                              });
                        }
             }
       },
          suijiNumFour(e){
             let boolean=new RegExp("^[0-9][0-9]*$").test(this.suijiNum.DXfour)
             if(!boolean){
               this.$message.warning('请输入正整数');
               this.suijiNum.DXfour=0
             }else{
                        if(parseInt(this.suijiNum.DXfour)>parseInt(this.NumBerDX.NumFour)){
                           this.$alert('', '输入的随机数量有误请重新输入', {
                                confirmButtonText: '确定',
                               showClose:false,
                               callback: action => {this.suijiNum.DXfour=0}
                              });
                        }
             }
       },
         suijiNumPDFour(e){
             let boolean=new RegExp("^[0-9][0-9]*$").test(this.suijiNum.PDfour)
             if(!boolean){
               this.$message.warning('请输入正整数');
               this.suijiNum.PDfour=0
             }else{
                        if(parseInt(this.suijiNum.PDfour)>parseInt(this.NumBerPD.NumFour)){
                           this.$alert('', '输入的随机数量有误请重新输入', {
                                confirmButtonText: '确定',
                               showClose:false,
                               callback: action => {this.suijiNum.PDfour=0}
                              });
                        }
             }
       },
          suijiNumFive(e){
             let boolean=new RegExp("^[0-9][0-9]*$").test(this.suijiNum.DXfive)
             if(!boolean){
               this.$message.warning('请输入正整数');
               this.suijiNum.DXfive=0
             }else{
                        if(parseInt(this.suijiNum.DXfive)>parseInt(this.NumBerDX.NumFive)){
                           this.$alert('', '输入的随机数量有误请重新输入', {
                                confirmButtonText: '确定',
                               showClose:false,
                                callback: action => {this.suijiNum.DXfive=0}
                              });
                        }
             }
       },
        suijiNumPDFive(e){
             let boolean=new RegExp("^[0-9][0-9]*$").test(this.suijiNum.PDfive)
             if(!boolean){
               this.$message.warning('请输入正整数');
               this.suijiNum.PDfive=0
             }else{
                        if(parseInt(this.suijiNum.PDfive)>parseInt(this.NumBerPD.NumFive)){
                           this.$alert('', '输入的随机数量有误请重新输入', {
                                confirmButtonText: '确定',
                               showClose:false,
                                callback: action => {this.suijiNum.PDfive=0}
                              });
                        }
             }
       },
       deletePaper(){
           //批量
           console.log(this.checkedJudgeQuestions);
            this.$confirm('确定删除选中的这些试题吗？, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
              paperQuestion.removeBath(this.checkedJudgeQuestions).then(res=>{
            // console.log(res);
              this.listSuJiPDQuestion()
                this.listSuJiDXQuestion()
               this.listQuestion()
               this.listJudgeQuestion()
                 this.$message({
                type: 'success',
                message: '删除成功!'
            });
            })
          }).catch(error=>{
              // console.log(error);
            })
       },
       change(){
            this.danxuan=!this.danxuan
            this.panduan=!this.panduan
       },
       updateChoicePaper(){
           console.log(1);
       },
       suijibtn(){
                 this.$router.push({path:"/paper/Paper/"+this.paperId})
       },
       zidingyibtn(){
             this.$confirm('确定跳转到自定义组卷页面吗？跳转到自定义组卷页面将删除您刚才所有随机生成的试卷题目, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    showClose:false,
                    closeOnClickModal:false
                  }).then(() => {
                       paperQuestion.deleteByPaperId(this.paperId,0).then(res=>{
                     this.$router.push({path:"/paper/paper_question/"+this.paperId})
                    })
                  }).catch(() => {
                   this.radio1="2"
                  });
       },
       DeleteChoicePaper(id){
            this.$confirm('确定删除试卷中的该试题吗？, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
                paperQuestion.delete(id).then(res=>{
            // console.log(res);
                this.listSuJiPDQuestion()
                this.listSuJiDXQuestion()
               this.listQuestion()
               this.listJudgeQuestion()
                 this.$message({
                type: 'success',
                message: '删除成功!'
            });
            }) 
          }).catch(error=>{
              // console.log(error);
            })
           
       },
       DeleteJudgePaper(id){
          // console.log(id);
           this.$confirm('确定删除试卷中的该试题吗？, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
                paperQuestion.delete(id).then(res=>{
            // console.log(res);
              this.listSuJiDXQuestion()
               this.listQuestion()
               this.listQuestion()
               this.listJudgeQuestion()
                 this.$message({
                type: 'success',
                message: '删除成功!'
            });
            })    
          }).catch(error=>{
              // console.log(error);
            })
          
       },
       listQuestion(){
             paperQuestion.listQuestion(this.paperId,0,1).then(res=>{
                // console.log(res);
                // this.choiceQuestions=res.data.paperQuestions
                // this.Choicetotal=this.choiceQuestions.length
                // this.total=this.Choicetotal+this.Judgetotal
                // console.log(this.choiceQuestions.length);
             })
             paperQuestion.listByPaperId(this.paperId).then(res=>{
               this.paper=res.data.paper
              //  console.log(this.paper);
             })
       },
        listJudgeQuestion(){
             paperQuestion.listJudgeQuestion(this.paperId,1,1).then(res=>{
                console.log(res);
                //   this.judgeQuestion=res.data.paperJudgeQuestions
                //   this.Judgetotal=this.judgeQuestion.length
                //   this.total=this.Choicetotal+this.Judgetotal
             })
       },

     //判断题
      judge_question(){
           this.$router.push({path:"/paper/JudgeQuestion/"+this.paperId})
        },

       //选择题题目
       choice_question(){
         this.$router.push({path:"/paper/choiceQuestion/"+this.paperId})

       },
       saveOrUpdate(){
           this.$router.push({path:"/paper/publish/"+this.paperId})
       },
       previous(){
           this.$router.push({path:"/paper/editPaper/"+this.paperId})
       }
     },
     created(){
        this.paperId= this.$route.params.paperId
        this.CreateByLv();
        this.listSuJiPDQuestion()
        this.listSuJiDXQuestion()
     }
}
</script>
<style scoped>
.suijiTK{
   background-color: #dee0e4;
}
.updateTime{
   font-size: 12px;
   margin-top: 10px;
}
.xiugaiBtn{
  margin-left: 58px;
  margin-top: 11px;
}
.time2{
   font-size:15px;
   line-height: 38px;
   margin-left: 95px;
   margin-top: 10px;
   margin-bottom: 10px;
}
.time5{
   font-size:14px;
   line-height: 30px;
   margin-left: 20px;
   font-weight: 550
}
.time6{
   font-size:15px;
   line-height: 48px;
   margin-left: 35px;
}
.time7{
   font-size:15px;
   line-height: 48px;
   margin-left: 28px;
}
.time8{
   font-size:15px;
   line-height: 48px;
   margin-left: 32px;
}
.time4{
   font-size:14px;
   line-height: 30px;
   margin-left: 23px;
   font-weight: 550
}
.time3{
   font-size:14px;
   line-height: 30px;
   margin-left: 14px;
   font-weight: 550
}
.time1{
   font-size:14px;
   line-height: 30px;
   margin-left: 82px;
   font-weight: 550;
}
.time{
   border-radius: 7px;
   background-color: #ebe4e4;;
   margin-left: 20px;
   width: 230px;
   height: 30px;
   border: 1px solid #b6b9be;
}
.asidePaper{
   margin-left: 77px;
   font-size: 17px;
   margin-top: 30px;
   font-weight: 550;
}
.aside{
  background-color: rgb(255, 255, 255);
  margin-top: 23px;
  width: 270px;
  height: 300px;
  border: 1px solid #b6b9be;
  border-radius: 10px;
}
.answer{
  font-size: 15px;
  color: #409EFF;
  margin-bottom: 7px;
}
.analysis{
  font-size: 13px;
}
.answer1{
  font-weight: 600;
}
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
    float: right;
    margin-left:3%;
    font-size: 15px;
    color: darkturquoise;
}
.tubiao1{
   float: right;
   margin-right: -50px;
   font-size: 18px;
}
.tubiao{
  float: right;
  margin-right: 5%;
  font-size: 18px;
}
.gongji{
  font-size: 19px;
  color: chocolate;
}
.number{
  font-size: 15px;
  margin-left: 28px;
  margin-top: 20px;
  margin-bottom: 28px;
}
.select{
  margin-left: 20px;
  font-size: 18px;
}
  .checkbox   {
    margin-top: 20px;
    margin-left: 27px;
    width: 17px;
    height: 17px;
    display: inline-block;
  }
   .collapse  {
/*float: left;*/
     border: #909399 solid 1.5px;
     margin: 10px 25px 15px 60px;
     padding-left: 20px ;

  }
   .collapse:hover{
     border: #0086b3 solid 1.5px;
     }
   .collapse1  /deep/ .el-collapse-item:last-child {
     margin-bottom: 10px;
   }

.tips{
  width: 500px;
  height: 50px;
  font-size: 16px;
  margin-left: 30%;
}
.tip{
 color:#36aafd
}
.el-radio{
  margin-top: 10px;
  margin-right: 50px;
}
img{
  width: 150px;
  height:150px;
  margin-top: 40px;
  margin-left: 40%;
}
.app-container{
  background-color: rgb(240, 240, 240);
  width: 100%;
  height: 100%;
}
.center{
   background-color: red;
   height: 50px;
}
.div2{
  background-color: rgb(255, 255, 255);
  margin-top: 10px;
  width: 99%;
  border: 1px solid #b6b9be;
}
.div1{
  border-radius: 20px;
  background-color: rgb(255, 255, 255);
  height: 55px;
  width: 99%;
  border: 1px solid #b6b9be;

}
.zujuan{
  float: left;
  margin-top: 20px;
  margin-right: 50px;
  margin-left: 20px;

}
  .el-button2{
     margin-left:42%;
  }
  .el-button1{
     margin-top: 25px;
     margin-left:25%;
  }
  .el-button3{
    margin-top: -10px;
    margin-left:18px;
  }
</style>
