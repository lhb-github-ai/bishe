<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新试卷</h2>
    <el-steps :active="3" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写试卷基本信息"/>
      <el-step title="选择试题"/>
     <el-step title="试卷设置及预览"/>
    </el-steps>
    <el-form label-width="120px">
      <div  style="background-color:#0001;height: 405px;">
       
       <div class="blue-div"></div> <span class="shezhiZT">试卷总分:</span>
       <el-form-item prop="totalScore"  class="shezhiZT">
         <el-input v-model="paper.totalScore"  class="option-input"  placeholder="请输入试卷总分"></el-input>
        </el-form-item>
        <!-- label="考试时间:" -->
         <div class="blue-div"></div> <span class="shezhiZT">考试时间:</span>
     <el-form-item  prop="time"  class="shezhiZT">
         <el-input v-model="paper.time" class="option-input"  placeholder="请输入考试时间"></el-input>
    </el-form-item>
     <div class="blue-div"></div> <span class="shezhiZT">设置考试时间范围:</span>
     <div style="margin-left:85px;" class="rili">
          <el-date-picker 
              v-model="time_frame"
              type="datetimerange"
              value-format=" yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
          </el-date-picker>
          </div>
          <el-button style="margin-left:17px;margin-top:20px;"  type="primary" @click="dialogDXTable = true" >点击预览单选题题目</el-button>
          <el-button style="margin-left:17px;margin-top:20px;"  type="primary" @click="dialogPDTable = true" >点击预览多选题题目</el-button>
    </div>
         <el-form-item>
        <el-button style="margin-left:29%" @click="previous"  type="primary">上一步</el-button>
        <el-button   type="primary" @click="save">保存</el-button>
        <el-button  type="primary" @click="publish">保存并发布</el-button>
       </el-form-item>
    </el-form>
    <el-dialog title="单选题试题详情" :visible.sync="dialogDXTable" width=77% top=100px>
     <el-table :data="DXData">
      <el-table-column property="questionName" label="题目" width="250"></el-table-column>
      <el-table-column property="optionA" label="A选项" width="150"></el-table-column>
      <el-table-column property="optionB" label="B选项" width="150"></el-table-column>
      <el-table-column property="optionC" label="C选项" width="150"></el-table-column>
      <el-table-column property="optionD" label="D选项" width="150"></el-table-column>
      <el-table-column property="answer" label="答案" width="70"></el-table-column>
      <el-table-column property="analysis" label="解析" width="175"></el-table-column>
    </el-table>
  </el-dialog>

   <el-dialog title="判断题试题详情" :visible.sync="dialogPDTable"  width=40%>
     <el-table :data="PDData">
      <el-table-column property="questionName" label="题目" width="280"></el-table-column>
      <el-table-column property="answer" label="答案" width="100"></el-table-column>
      <el-table-column property="analysis" label="解析" width="180"></el-table-column>
    </el-table>
  </el-dialog>
  </div>
</template>
<script>
import paper  from '@/api/paper'
import bank  from '@/api/bank'
import paperQuestion from '@/api/paperQuestion'
export default {
     data(){
       return{
                  dialogDXTable :false,
                  dialogPDTable:false,
                  paperId: "",
                  paper:{},
                  time_frame:[],
                  DXData: [{
                    }],
                  PDData: [{
                    }],
                    isZOrS:"",
                  }
     },
     methods:{
        listPDQuestion(){
             paperQuestion.listQuestionDX(this.paperId,0).then(res=>{
               this.isZOrS=res.data.paperQuestionsDX[0].isZOrS
                console.log(res.data.paperQuestionsDX[0].isZOrS+"**********");
                this.DXData=res.data.paperQuestionsDX

             })
       },
        listDXQuestion(){
             paperQuestion.listQuestionPD(this.paperId,1).then(res=>{
                console.log(res);
                this.PDData=res.data.paperQuestionsPD
             })
       },
       save(){
          this.paper.timeFrame=this.time_frame[0]+"至"+this.time_frame[1]
          this.paper.paperId=this.paperId
          this.paper.status=0
                 paper.savePaper(this.paper).then(res=>{
                     this.$message({
                      type: 'success',
                      message: '保存成功!'
                  });
                   this.$router.push({path:"/paper/list"})
              })
       },
       publish(){
          this.paper.timeFrame=this.time_frame[0]+"至"+this.time_frame[1]
          this.paper.paperId=this.paperId
          this.paper.status=1
                 paper.savePaper(this.paper).then(res=>{
                    this.$message({
                      type: 'success',
                      message: '保存并发布成功!'
                  });
                   this.$router.push({path:"/paper/list"})
              })

          //  this.$router.push({path:"/course/publish"})
       },
       previous(){
                 if(this.isZOrS=="0"){
                  this.$router.push({path:"/paper/Paper/"+this.paperId})
                 }else{
                    this.$router.push({path:"/paper/paper_question/"+this.paperId})
                 }
            
       },
        getOneById(paperId){
             paper.findById(paperId).then(res=>{
                  this.paper=res.data.paper
             //     console.log(this.paper);
                  var timeFrame= this.paper.timeFrame
                  var aa =  timeFrame.split("至")
                  this.time_frame = ['','']
                     this.time_frame[0]=aa[0] 
                     this.time_frame[1]=aa[1]
             })
       }
     },
     created(){
          this.paperId= this.$route.params.paperId
            if (this.$route.params && this.$route.params.paperId) {
                      const paperId = this.$route.params.paperId
                            this.getOneById(paperId)
                               this.listPDQuestion();
                               this.listDXQuestion();
                          // console.log(this.$route.params.id);
              }
    
     }
}
</script>
<style scoped>
  .rili /deep/ .el-input__inner{
  border-color: #303133;
  margin-left: 72px;
  margin-top: 10px;
  height: 45px;
}
.option-input /deep/  .el-input__inner{
      border-color: #303133;
      width: 300px;
      height: 42px;
      margin-left: 38px;
}
  .shezhiZT {
    font-size:17.8px;
    font-weight: normal;
    font-stretch: normal;
    letter-spacing: 0px;
    color: #333333;
  }
 .blue-div{
    width: 5px;
    height: 20px;
    background-color: #36aafd;
    display: inline-block;
    margin-right: 2px;
    vertical-align: text-bottom;
 }
</style>