<template>
  <div id="answer" style=" background-color: #eee;">
    <!--顶部信息栏-->
     <!-- <button @click="reset">重新计时</button> -->
     <div class="top">
       <ul class="item">
         <li class="aaa"><i class="iconfont icon20" style="font-size:25px" ref="toggle" @click="slider_flag = !slider_flag">&#xe619;</i></li>
         <li>期末考试-{{examData.paperName}}</li>
         <li @click="flag = !flag" class="aaa">
           <i class="iconfont " style="font-size:35px">&#xe603;</i>
           <div class="msg"  v-if="flag" @click="flag = !flag">
             <p>姓名：{{loginInfo.studentName}}</p>
             <p>准考证号:  {{loginInfo.studentId}}</p>
           </div>
         </li>
         <li class="aaa"><i class="iconfont icon20 ">&#xe606;</i></li>
       </ul>
     </div>
     <div class="flexarea">
        <!--左边题目编号区-->
        <transition name="slider-fade">
          <div class="left" v-if="slider_flag">
            <ul class="l-top">
              <li>
                <a href="javascript:;"></a>
                <span>当前</span>
              </li>
              <li>
                <a href="javascript:;"></a>
                <span>未答</span>
              </li>
              <li>
                <a href="javascript:;"></a>
                <span>已答</span>
              </li>
              <li>
                <a href="javascript:;"></a>
                <span>标记</span>
              </li>
            </ul>
            <div class="l-bottom">
              <div class="item">
                <p>单选题部分</p>
                <ul>
                  <li v-for="(list, index1) in topicDX" :key="index1">
                    <a href="javascript:;" 
                      @click="change(index1)"
                      :class="{'border': index == index1 && currentType == 1,'bg': bg_flag && topicDX[index1].isClick == true}"> 
                      <span :class="{'mark': topicDX[index1].isMark == true}"></span>
                      {{index1+1}}
                    </a>
                  </li>
                </ul>
              </div>
              <div class="item">
                <p>判断题部分</p>
                <ul>
                  <li v-for="(list, index3) in topicPD" :key="index3">
                    <a href="javascript:;" @click="judge(index3)" :class="{'border': index == index3 && currentType == 3,'bg': bg_flag && topicPD[index3].isClick == true}">
                        <span :class="{'mark':topicPD[index3].isMark == true}"></span>
                        {{topicCountDX+index3+1}} 
                        </a>
                  </li>
                </ul>
              </div>
              <div class="final" @click="commit()">结束考试</div>
            </div>
          </div>
        </transition>  
        <!--右边选择答题区-->
        <transition name="slider-fade">
        <div class="right">
          <div class="title" style="font-size:17px">
            <p style="font-size:19px">{{title}}</p>
       <p></p>  <p></p>  <p></p>  <p></p>  <p></p>  <p></p>  <p></p>     <p></p>  <p></p>  <p></p>  <p></p>  <p></p>  <p></p>  <p></p>  <p></p>  <p></p>  <p></p>  <p></p>
            <p style="font-size:19px;color:red">{{valee}}</p>
            <i class="iconfont icon-shixiangyoujiantou- auto-right" style="font-size:22px;"></i>
            <span style="font-size:20px">全卷共{{topicCountDX + topicCountPD }}题  <i class="iconfont icon-daojishi" style="color:blue;font-size:19px;"></i>倒计时：<b> {{timerCount2}}:{{timerCount1}}</b></span>
          </div>
          <div class="content">
            <p class="topic"><span class="number">{{number}}</span>{{showQuestion}}</p>
            <div v-if="currentType == 1">
              <el-radio-group v-model="radio[index]" @change="getChangeLabel" >
                <el-radio :label="1">{{showAnswer.optionA}}</el-radio>
                <el-radio :label="2">{{showAnswer.optionB}}</el-radio>
                <el-radio :label="3">{{showAnswer.optionC}}</el-radio>
                <el-radio :label="4">{{showAnswer.optionD}}</el-radio>
              </el-radio-group>
              <div class="analysis" v-if="isPractice">
                <!-- <ul>
                  <li> <el-tag type="success">正确姿势：</el-tag><span class="right">{{reduceAnswer.rightAnswer}}</span></li>
                  <li><el-tag>题目解析：</el-tag></li>
                  <li>{{reduceAnswer.analysis == null ? '暂无解析': reduceAnswer.analysis}}</li>
                </ul> -->
              </div>
            </div>
            <div class="judge" v-if="currentType == 3">
              <el-radio-group v-model="judgeAnswer[index]" @change="getJudgeLabel" v-if="currentType == 3">
                <el-radio :label="1">正确</el-radio>
                <el-radio :label="2">错误</el-radio>
              </el-radio-group>
              <div class="analysis" v-if="isPractice">
                <ul>
                  <li> <el-tag type="success">正确姿势：</el-tag><span class="right">{{topicPD[index].answer}}</span></li>
                  <li><el-tag>题目解析：</el-tag></li>
                  <li>{{topicPD[index].analysis == null ? '暂无解析': topicPD[index].analysis}}</li>
                </ul>
              </div>
            </div>
          </div>
          <div class="operation">
            <ul class="end">
              <li @click="previous()"><i class="iconfont icon-zuojiantou1" style="font-size:20px;"></i><span style="font-size:17px;">上一题</span></li>
              <li @click="mark()"><i class="iconfont">&#xe612;</i><span>标记</span></li>
              <li @click="next()"><span style="font-size:17px;">下一题</span><i class="iconfont icon-youjiantou1"  style="font-size:19px;"></i></li>
            </ul>
          </div>
        </div>
        </transition>
     </div> 
  </div>
</template>
<script>
import paper  from '@/api/paper'
import score  from '@/api/score'
import cookie from 'js-cookie'
export default {
        layout: 'answer',
 data() {
    return {
      timeSeconds: 0,
      timeMinutes: 0,
      seconds: 59, // 秒
      minutes: 0, // 分
      timer: null,
      reduceAnswer:[],  //vue官方不支持3层以上数据嵌套,如嵌套则会数据渲染出现问题,此变量直接接收3层嵌套时的数据。
      answerScore: 0, //答题总分数
      bg_flag: false, //已答标识符,已答改变背景色
      isFillClick: false, //选择题是否点击标识符
      slider_flag: true, //左侧显示隐藏标识符
      flag: false, //个人信息显示隐藏标识符
      currentType: 1, //当前题型类型  1--选择题  2--填空题  3--判断题
      radio: [], //保存考生所有选择题的选项
      title: "请选择正确的选项",
      index: 0, //全局index
      userInfo: { //用户信息
        name: null,
        id: null
      },
      topicCount: [],//每种类型题目的总数
      topicCountDX: [],//每种类型题目的总数
      topicCountPD: [],//每种类型题目的总数
     // score: [],  //每种类型分数的总数
      examData: { //考试信息
        // source: null,
        // totalScore: null,
      },
      topic: {  //试卷信息

      },
      showQuestion: [], //当前显示题目信息
      showAnswer: {}, //当前题目对应的答案选项
      number: 1, //题号
      part: null, //填空题的空格数量
      fillAnswer: [[]], //二维数组保存所有填空题答案
      judgeAnswer: [], //保存所有判断题答案
      topic1Answer: [],  //学生选择题作答编号,
      rightAnswer: '',
      paperId:"",
      topicPD:[],
      topicDX:[],
      isPractice:false,
      Score:{} ,//成绩单,
      loginInfo:{},
      valee:"考试加油！！！",
    }
  },
  methods:{
      num(n) {
   return n < 10 ? "0" + n : "" + n;
  },
  // 清除
  clear() {
   localStorage.removeItem("startTime1");
   localStorage.removeItem("startTime2");
   sessionStorage.setItem("answered", 1);
   clearInterval(this.timer);
  },
   // 重新计时
  reset() {
   sessionStorage.removeItem("answered");
   window.location.reload();
   localStorage.removeItem("startTime1");
   localStorage.removeItem("startTime2");
   clearInterval(this.timer);
  },
  // 倒计时
  timing() {
        let [startTime1, startTime2] = [ localStorage.getItem("startTime1"), localStorage.getItem("startTime2") ];
        let nowTime = new Date().getTime();
        if (startTime1) {
          let surplus = this.seconds - parseInt((nowTime - startTime1) / 1000);
          this.timeSeconds = surplus <= 0 ? 0 : surplus;
        } else {
          this.timeSeconds = this.seconds;
          localStorage.setItem("startTime1", nowTime); //存储秒
        }
        if (startTime2) {
          this.timeMinutes = startTime2;
        } else {
          this.timeMinutes = this.minutes;
          localStorage.setItem("startTime2", this.minutes); //存储分
        }
        this.timer = setInterval(() => {
          if ( this.timeSeconds == 0 && this.timeMinutes != 0 && this.timeMinutes > 0 ) {
          let nowTime = new Date().getTime();
          this.timeSeconds = this.seconds;
          localStorage.setItem("startTime1", nowTime);
          this.timeMinutes--;
          localStorage.setItem("startTime2", this.timeMinutes);
          } else if (this.timeMinutes == 0 && this.timeSeconds == 0) {
          this.timeSeconds = 0;
          this.clear();
          alert("考试时间到,系统强制交卷！！！");
          this.commit()
          } else {
          this.timeSeconds--;
          }
        }, 1000);
  },
    //计算分数
      commit(){
      let topic1Answer = this.topic1Answer
        console.log(topic1Answer+"************")
      let finalScore = 0
       topic1Answer.forEach((element,index) => { //循环每道选择题根据选项计算分数
        let right = null
        if(element != null) {
          switch(element) { //选项1,2,3,4 转换为 "A","B","C","D"
            case 1:
              right = "A"
              break
            case 2:
              right = "B"
              break
            case 3:
              right = "C"
              break
            case 4:
              right = "D"
          }
         // console.log("长度1111111111111"+this.examData.totalScore)
         // console.log("长度"+this.examData.totalScore/this.topicDX.length)
          if(right == this.topicDX[index].answer) { // 当前选项与正确答案对比
            finalScore += (this.examData.totalScore/2)/this.topicDX.length // 计算总分数
          }
        //  console.log("成绩"+right,this.topicDX[index].answer)
           console.log("单选题分数"+finalScore)
        }
     })
       /** 计算判断题总分 */
      let topic3Answer = this.judgeAnswer
      topic3Answer.forEach((element,index) => {
        let right = null
        switch(element) {
          case 1:
            right = "T"
            break
          case 2:
            right = "F"
        }
        if(right == this.topicPD[index].answer) { // 当前选项与正确答案对比
            finalScore += (this.examData.totalScore/2)/this.topicPD.length // 计算总分数
          }
           console.log("判断成绩"+right,this.topicPD[index].answer)
      })
       console.log(`目前总分${finalScore}`)
         this.Score.score=finalScore.toFixed(1)
         this.Score.paperId=this.paperId
         this.Score.studentId=this.loginInfo.id
        // console.log(this.score);

            // this.topicDX[this.index].options=val
        this.topicDX.forEach((element,index) => {
            let right = ""
            switch(element.options) { //选项1,2,3,4 转换为 "A","B","C","D"
            case 1:
              right = "A"
              break
            case 2:
              right = "B"
              break
            case 3:
              right = "C"
              break
            case 4:
              right = "D"
          }
          //  console.log(JSON.stringify(element.options)+"///////////////");
          element.options=right
          // console.log(JSON.stringify(element.options)+"///////////////");
          })
 
       this.topicPD.forEach((element,index) => {
            let right = ""
                switch(element.options) {
                case 1:
                  right = "T"
                  break
                case 2:
                  right = "F"
              }
          //  console.log(JSON.stringify(element.options)+"///////////////");
          element.options=right
          // console.log(JSON.stringify(element.options)+"///////////////");
          })
        //提交分数跟试卷
        score.addScore(this.Score).then(res=>{
          if(res.data.success){
                 paper.addPaperDetail(this.loginInfo.id,this.topicPD).then(res=>{
                 paper.addPaperDetail(this.loginInfo.id,this.topicDX).then(res1=>{
                      this.reset()
                      window.location.href = "/paper"
                 })
             })
           }
          })
    
      },
       getTime(date) { //日期格式化
      let year = date.getFullYear()
      let month= date.getMonth()+ 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day=date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours=date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes=date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds=date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    },
       listDXQuestion(){
             paper.listQuestionDX(this.paperId,0).then(res=>{
              this.topicDX=res.data.data.paperQuestionsDX
                this.topicCountDX=res.data.data.paperQuestionsDX.length
                  console.log(this.topicDX)
                      this.showQuestion =  this.topicDX[0].questionName
                     this.showAnswer =  this.topicDX[0]
             })
       },
        listPDQuestion(){
             paper.listQuestionPD(this.paperId,1).then(res=>{
             this.topicPD=res.data.data.paperQuestionsPD
                this.topicCountPD=res.data.data.paperQuestionsPD.length
                 console.log( this.topicPD)
             })
       },
       //获取试卷的相关信息
       getExamData(){
             let date = new Date()
             this.startTime = this.getTime(date)
            paper.GetById(this.paperId).then(res=>{
              console.log(res.data.data);
                this.examData=res.data.data.paper
                this.minutes = this.examData.time;
              
          })
            this.listDXQuestion()
             this.listPDQuestion()
       },
       change(index) { //选择题
  //  console.log(index+"899999999999999999");
            this.index = index
            let reduceAnswer = this.topicDX[this.index]
            this.reduceAnswer = reduceAnswer
            this.isFillClick = true
            this.currentType = 1
            let len = this.topicDX.length
            if(this.index < len) {
                if(this.index <= 0){
                this.index = 0
                }
                // console.log(`总长度${len}`)
                // console.log(`当前index:${index}`)
                this.title = "请选择正确的选项"
                let Data = this.topicDX
            
                this.showQuestion = Data[this.index].questionName //获取题目信息
                this.showAnswer = Data[this.index]
         // console.log(Data[this.index]+"111111111111111111111111111")
                this.number = this.index + 1
            }else if(this.index >= len) {
                this.index = 0
             this.judge(this.index)
            }
    },
     judge(index) { //判断题
      let len = this.topicPD.length
      this.currentType = 3
      this.index = index
      if(this.index < len) {
        if(this.index < 0){
          this.index = this.topicPD.length - 1
          this.fill(this.index)
        }else {
        //   console.log(`总长度${len}`)
        //   console.log(`当前index:${this.index}`)
          this.title = "请作出正确判断"
          let Data = this.topicPD
          console.log(Data)
          this.showQuestion = Data[index].questionName //获取题目信息
          this.number = this.topicCountDX+ index + 1
        }
      }else if (this.index >= len) {
        this.index = 0
        this.change(this.index)
      }
    },
     getChangeLabel(val) { //获取选择题作答选项
      this.radio[this.index] = val //当前选择的序号
      if(val) {
        let data = this.topicDX
        this.bg_flag = true
        data[this.index]["isClick"] = true
      }
      /* 保存学生答题选项 */
      this.topic1Answer[this.index] = val 
      this.topicDX[this.index].options=val
     // console.log( JSON.stringify(this.topicDX) +"////////////////////////???");
    },
    getJudgeLabel(val) {  //获取判断题作答选项
      this.judgeAnswer[this.index] = val
      this.topicPD[this.index].options=val
      if(val) {
        let data = this.topicPD
        this.bg_flag = true
        data[this.index]["isClick"] = true
      }
    },
     previous() { //上一题
      this.index --
      switch(this.currentType) {
        case 1: 
          this.change(this.index)
          break
        case 3:
          this.judge(this.index)
          break
      }
    },
    next() { //下一题
      this.index ++
      switch(this.currentType) {
        case 1: 
          this.change(this.index)
          break
        case 3:
          this.judge(this.index)
          break
      }
    },
    mark() { //标记功能
      switch(this.currentType) {
        case 1:
          this.topicDX[this.index]["isMark"] = true //选择题标记
          break
        case 3:
          this.topicPD[this.index]["isMark"] = true //判断题标记
      }
    },
  },
   mounted() {
     this.minutes=cookie.get("time")-1
        if (sessionStorage.getItem("answered") != 1) {
        this.timing();
        }
        console.log(this.timeSeconds+"***********"+this.timeMinutes);
        console.log(this.seconds+"***********"+this.minutes);
        if(this.timeSeconds==0&&this.timeMinutes==0){
          this.$message({
          message: '考试时间已结束！！！',
          type: 'success'
        });
            this.commit()
        }
 },
 computed: {
  timerCount1() {
   return this.timeSeconds < 10 ? "0" + this.timeSeconds : "" + this.timeSeconds;
  },
  timerCount2() {
   return this.timeMinutes < 10 ? "0" + this.timeMinutes : "" + this.timeMinutes;
  }
 },
 destroyed() {
  // 退出后清除计时器
  if (this.timer) {
   clearInterval(this.timer);
  }
 },
  created(){
      if(cookie.get('guli_token')==undefined||cookie.get('guli_token')==""){
         this.$router.push('/login');
      }
      
    //  this.$router.push('/test');
          this.paperId=this.$route.params.id
          this.getExamData()
            var jsonStr = cookie.get("guli_ucenter");
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr)
      }
  },
 
}
</script>
<style >

.aaa :hover{
 cursor:default
}
.iconfont.icon-time {
  color: #2776df;
  margin: 0px 6px 0px 20px;
}
/* .analysis {
  margin-top: 20px;
  .right {
    color: #2776df;
    font-size: 18px;
    border: 1px solid #2776df;
    padding: 0px 6px;
    border-radius: 4px;
    margin-left: 20px;
  }
  ul li:nth-child(2) {
    margin: 20px 0px;
  }
  ul li:nth-child(3) {
    padding: 10px;
    background-color: #d3c6c9;
    border-radius: 4px;
  }
} */
.analysis {
  margin-top: 20px;
}
.analysis .right {
  color: #2776df;
  font-size: 18px;
  border: 1px solid #2776df;
  padding: 0px 6px;
  border-radius: 4px;
  margin-left: 20px;
}
.analysis ul li:nth-child(2) {
  margin: 20px 0px;
}
.analysis ul li:nth-child(3) {
  padding: 10px;
  background-color: #d3c6c9;
  border-radius: 4px;
}

.analysis span:nth-child(1) {
  font-size: 18px;
}
.mark {
  position: absolute;
  width: 4px;
  height: 4px;
  content: "";
  background-color: red;
  border-radius: 50%;
  top: 0px;
  left: 22px;
}
.border {
  position: relative;
  border: 1px solid #FF90AA !important;
}
.bg {
  background-color: #5188b8 !important;
}
/* .fill .el-input {
  display: inline-flex;
  width: 150px;
  margin-left: 20px;
  .el-input__inner {
    border: 1px solid transparent;
    border-bottom: 1px solid #eee;
    padding-left: 20px;
  }
} */
.fill .el-input {
  display: inline-flex;
  width: 150px;
  margin-left: 20px;
}
.fill .el-input .el-input__inner {
  border: 1px solid transparent;
  border-bottom: 1px solid #eee;
  padding-left: 20px;
}
/* slider过渡效果 */
.slider-fade-enter-active {
  transition: all .3s ease;
}
.slider-fade-leave-active {
  transition: all .3s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slider-fade-enter, .slider-fade-leave-to {
  transform: translateX(-100px);
  opacity: 0;
}

.operation .end li:nth-child(2) {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgb(39, 118, 223);
  border-radius: 50%;
  width: 50px;
  height: 50px;
  color: #fff;
}
.operation .end li {
  cursor: pointer;
  margin: 0 100px;
}
.operation {
  background-color: #fff;
  border-radius: 4px;
  padding: 10px 0px;
  margin-right: 10px;
  margin-top:15px
}
.operation .end {
  display: flex;
  justify-content: center;
  align-items: center;
  color: rgb(39, 118, 223);
}
.content .number {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 20px;
  height: 20px;
  background-color: rgb(39, 118, 223);
  border-radius: 4px;
  margin-right: 4px;
}
.content {
  padding: 0px 20px;
}
.content .topic {
  padding: 20px 0px;
  padding-top: 30px; 
}
.right .content {
  background-color: #fff;
  margin: 10px;
  margin-left: 0px;
  height: 410px;
  font-size:20px;
}
.content .el-radio-group label {
  color: #000;
  margin: 5px 0px;
}
.content .el-radio-group {
  display: flex;
  flex-direction:column;
}
.right .title p {
  margin-left: 20px;
}
.flexarea {
  display: flex;
}
.flexarea .right {
  flex: 1;
}
.auto-right {
  margin-left: auto;
  color: #2776df;
  margin-right: 10px;
}
.right .title {
  margin-right: 10px;
  padding-right: 30px;
  display: flex;
  margin-top: 10px;
  background-color: #fff;
  height: 50px;
  line-height: 50px;
}
.clearfix {
  clear: both;
}
.l-bottom .final {
  cursor: pointer;
  display: inline-block;
  text-align: center;
 background-color: rgb(39, 118, 223);
  width: 240px;
  margin: 20px 0px 20px 10px;
  border-radius: 4px;
  height: 30px;
  line-height: 30px;
  color: #fff;
  margin-top: 22px;
}
#answer .left .item {
  padding: 0px;
  font-size: 16px;
}
.l-bottom {
  border-radius: 4px;
  background-color: #fff;
}
.l-bottom .item p {
  margin-bottom: 15px;
  margin-top: 10px;
  color: #000;
  margin-left: 10px;
  letter-spacing: 2px;
}
.l-bottom .item li {
  width: 15%;
  margin-left: 5px;
  margin-bottom: 10px;
}
.l-bottom .item {
  display: flex;
  flex-direction: column;
}
.l-bottom .item ul {
  width: 100%;
  margin-bottom: -8px;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}
.l-bottom .item ul li a { 
  position: relative;
  justify-content: center;
  display: inline-flex;
  align-items: center;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #eee;
  text-align: center;
  color: #000;
  font-size: 16px;
}
.left .l-top {
  display: flex;
  justify-content: space-around;
  padding: 16px 0px;
  border: 1px solid #eee;
  border-radius: 4px;
  margin-bottom: 10px;
  background-color: #fff;
}
.left {
  width: 260px;
  height: 100%;
  margin: 10px 10px 0px 10px;
}
.left .l-top li:nth-child(2) a {
  border: 1px solid #eee;
}
.left .l-top li:nth-child(3) a {
  background-color: #5188b8;
  border: none;
}
.left .l-top li:nth-child(4) a {
  position: relative;
  border: 1px solid #eee;
}
.left .l-top li:nth-child(4) a::before {
  width: 4px;
  height: 4px;
  content: " ";
  position: absolute;
  background-color: red;
  border-radius: 50%;
  top: 0px;
  left: 16px;
}
.left .l-top li {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.left .l-top li a {
  display: inline-block;
  padding: 10px;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #FF90AA;
}
#answer .top {
  background-color: rgb(39, 118, 223);
}
#answer .item {
  color: #fff;
  display: flex;
  padding: 20px;
  font-size: 20px;
}
#answer .top .item li:nth-child(1) {
  margin-right: 10px;
}
#answer .top .item li:nth-child(3) {
  position: relative;
  margin-left: auto;
}
#answer {
  padding-bottom: 30px;
}
.icon20 {
  font-size: 20px;
  font-weight: bold;
}
.item .msg {
  padding: 10px 15px;
  border-radius: 4px;
  top: 47px;
  right: -30px;
  color: #6c757d;
  position: absolute;
  border: 1px solid rgba(0,0,0,.15);
  background-color: #fff;
}
.item .msg p {
  font-size: 16px;
  width: 200px;
  text-align: left;
}
</style>
