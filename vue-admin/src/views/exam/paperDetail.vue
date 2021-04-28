<template>
 <div style="background-color: #eee;">
    <div style="font-size:23px;">
      <p style="margin-bottom:10px;margin-left: 100px;">试卷名称:<span >{{examData.paperName}}</span></p>
      <div style=" border: solid 1px;">
        <p style="float: left;margin-top:10px ;margin-left: 100px;">
          <span>共</span><i class="content_lit" >{{topicDX.length+topicPD.length}}</i><span>题，</span><span>合计</span><i
          class="content_fs" >{{examData.totalScore}}</i><span>分</span>
        </p>
      </div>
      <p style="float: left;margin-left:27%;margin-top:10px ;">分数:<span style="color: red;font-size: 23px;">{{score}} 分</span></p>
    </div>
    <br>
    <br>
    <br>
    <ul style="margin-left:27%;width:45%;">
      <div style=" background: rgb(224 216 216);">
      <li id="qu_0_0" highlight-current-row v-loading="loading" v-for="(wor1, index1) in topicDX" :key="wor1.id"
          border element-loading-text="拼命加载中">
        <div class="test_content_nr_tt" v-if="index1==0">
        <div class="test_content_title"> <h3  style="line-height:50px;padding-left:20px;font-size:21px;font-weight: normal;">单选题</h3></div>
        </div>
        <div  class="test_content_nr_tt"><i >{{index1+1}}</i> <span >{{wor1.question}}</span> </div>
        <div class="test_content_nr_main">
          <ul>
            <li class="option">
              <input type="radio" v-model="wor1.options" v-bind:value="'A'" >
              <label>
                A.
                <p class="ue" style="display: inline;" >{{wor1.optionA}}</p>
              </label>
            </li>

            <li class="option">

              <input type="radio" v-model="wor1.options" v-bind:value="'B'">
              <label>
                B.
                <p class="ue" style="display: inline;" >{{wor1.optionB}}</p>
              </label>
            </li>

            <li class="option">

              <input type="radio" v-model="wor1.options" v-bind:value="'C'">
              <label>
                C.
                <p class="ue" style="display: inline;" >{{wor1.optionC}}</p>
              </label>
            </li>

            <li class="option">

              <input type="radio" v-model="wor1.options" v-bind:value="'D'">
              <label>
                D.
                <p class="ue" style="display: inline;" >{{wor1.optionD}}</p>
              </label>
              <div>
                <br>
                <div style="font-size: 17px;border: solid 1px red;padding-left:20px;margin-left:-10px;">
                  <div v-if="(wor1.answer)==(wor1.options)" style="color: green;font-weight:bold;font-size: 20px "
                       >正确</div>
                  <div v-if="(wor1.answer)!=(wor1.options)" style="color: red;font-weight:bold;font-size: 20px"
                      >错误</div>
                  正确答案:    <span style="font-size:20px;color:blue" >{{wor1.answer  }}       </span>   你的答案: <span >{{wor1.options}}</span>
                  <div>解析：<span>{{wor1.analysis}}</span></div>
                  </div>
                  
              </div>
            </li>
          </ul>
        </div>
      </li>
      </div>
      <!-- 判断题 -->
          <div style=" background: rgb(224 216 216);">
      <li id="qu_0_0" highlight-current-row v-loading="loading" v-for="(wor2, index) in topicPD" :key="wor2.id"
          border element-loading-text="拼命加载中">
        <div class="test_content_nr_tt" v-if="index==0">
          <div class="test_content_title"> <h3  style="line-height:50px;padding-left:20px;font-size:21px;font-weight: normal;">判断题</h3></div>
        </div>
        <div  class="test_content_nr_tt"> <i >{{topicDX.length+index+1}}</i> <span >{{wor2.question}}</span></div>
        <div class="test_content_nr_main">
          <ul>
            <li class="option">
              <input type="radio" v-model="wor2.options" v-bind:value="'T'">
              <label>
                T
              </label>
            </li>
            <li class="option">
              <input type="radio" v-model="wor2.options" v-bind:value="'F'">
              <label>
                F
              </label>
              <div >
                <br>
                <div style="font-size: 17px;border: solid 1px red;padding-left:20px;margin-left:-10px;">
                  <div v-if="(wor2.answer)==(wor2.options)" style="color: green;font-weight:bold;font-size: 20px ">正确</div>
                  <div v-if="(wor2.answer)!=(wor2.options)" style="color: red;font-weight:bold;font-size: 20px">错误</div>
                     正确答案:    <span style="font-size:20px;color:blue"  >{{wor2.answer}}</span> 你的答案:    <span >{{wor2.options}}</span>
                  <div>解析： <span>{{wor2.analysis}}</span></div>
                  </div>
              </div>
            </li>
          </ul>
        </div>
      </li>
          </div>
    </ul>
    <br>
    <br>
    <el-link href="/MyExam" type="primary" icon="el-icon-back" style="float: left;font-size: 17px">返回我的考试
    </el-link>
    <br>
    <br>
  </div>
</template>
<script>
 import paper from '@/api/paper';
 export default {
    data() {
      return {
         app: '只能提交一次',
        flag2: false,
        flag1: true,
        flag: false,
        falg: false,
        loading: false,
        topicDX: [{
          studentId:"1",
          id:"1",
          options: 'A',
          questions: '多选',
          question: '题目内容',
          answer: 'D',
          optionA: 'A',
          optionB: 'B',
          optionC: 'C',
          optionD: 'D',}],
        topicPD: [{

          id:"1",
          options: 'A',
          questions: '多选',
          question: '题目内容',
          answer: 'D',
           }],
        examData: {},
        exams: {
          bankId:1,
          name: '',
          username: '',
          paperName: '',
          score: '',
          status: '',
          uid:4
        },
        Score:{},
        paperId:"",
        loginInfo:{},
        frequency:""
      }
    },
    methods:{
         init(){
            //     console.log(this.paperId);
            //  console.log(this.frequency);
               paper.getPaperDetail(this.paperId,this.frequency).then(res=>{
                  console.log(res);
                    this.topicDX=res.data.paperDetailDX
                    this.topicPD=res.data.paperDetailPD
               })
         },
    },
    created(){
  
        this.paperId=this.$route.params.id
        this.frequency=this.$route.params.frequency
        this.score=this.$route.params.score
         console.log(this.frequency+"***");
       this.init()
    }
    }
</script>
<style scoped>
li {
    list-style: none;
  }

  .test_title p {
    padding: 0 20px;
    display: block;
    float: left;
  }

  .test_time i {
    font-size: 20px;
    height: 45px;
    line-height: 40px;
    display: block;
    float: left
  }

  .test_time b {
    font-size: 16px;
    height: 45px;
    display: block;
    float: left;
    margin-left: 10px;
  }

  .test_dtk i {
    font-size: 22px;
    height: 45px;
    line-height: 45px;
    display: block;
    float: left;
    color: #389fc3;
  }

  .test_dtk span {
    font-size: 14px;
    height: 45px;
    display: block;
    float: left;
    margin-left: 10px;
  }

  .test_title font {
    width: 80px;
    height: 45px;
    display: block;
    float: right;
    text-align: center;
  }

  .test_title font input {
    background: #389fc3;
    border: none;
    display: block;
    width: 80px;
    height: 45px;
    cursor: pointer;
    color: #fff;
    font-size: 16px;
    font-weight: bold;
  }

  .test_title font input:active {
    background: #79aef0;
  }

  .test_content_title h2 {
    padding: 0 30px;
    font-size: 16px;
    font-weight: normal;
    display: block;
    float: left
  }
  .test_content_title {
    width: 750px;
    height: 50px;
    line-height: 50px;
    background: #e0d8d8;
    margin-left:-57px;
  }


  .test_content_title p {
    padding: 0 10px;
    height: 40px;
    line-height: 40px;
    background: #389fc3;
    color: #fff;
    border-radius: 30px;
    display: block;
    float: left;
    margin-top: 5px;
  }

  .test_content_nr > ul > li {
    width: 100%;
    border-bottom: 5px solid #efefef;
    padding-top: 10px;
  }

  .test_content_nr_tt {
    width: 85%;
    height: auto;
    line-height: 32px;
    margin: 0 auto;
    /* border-bottom: 2px solid #e4e4e4; */
    font-size: 18px;
  }

  .test_content_nr_tt i {
    width: 30px;
    height: 30px;
    line-height: 29px;
    text-align: center;
    display: block;
    float: left;
    background: #5d9cec;
    border-radius: 50%;
    margin-left: -50px;
    color: #fff;
    margin-top: 8px;
  }

  .test_content_nr_tt span {
    float: left;
    display: block;
    margin-top:10px;
    margin-left:7px
  }

  .test_content_nr_tt font {
    font-size: 14px
  }

  .test_content_nr_main {
    width: 85%;
    margin: 0 auto;
    padding: 10px 0;
    height: auto;
  }

  .option {
    line-height: 32px;
    display: block;
    background: #fff;
    color: #666;
    padding-top: 50px;
    font-size:18px;
    padding-left:10px
  }


  .option input {
    width: 20px;
    height: 20px;
    display: block;
    float: left;
    margin: 10px 10px 0 0;
  }

  .option label {
    margin: 0;
  }

  .test_click a {
    color: #fff;
  }


  .rt_nr1_title h1 {
    width: 130px;
    height: 45px;
    background: #389fc3;
    text-align: center;
    font-size: 14px;
    display: block;
    float: left;
    color: #fff;
  }

  .rt_nr1_title h1 i {
    padding: 0 5px;
    font-size: 14px;
    font-weight: normal;
  }

  .rt_nr1_title p {
    width: 141px;
    height: 43px;
    padding-right: 8px;
    border: 1px solid #e4e4e4;
    border-left: 0;
    display: block;
    float: right;
  }

  .rt_nr1_title p i {
    margin-left: 10px;
  }

  .rt_content_tt h2 {
    width: 150px;
    font-size: 14px;
    display: inline-block;
  }

  .rt_content_tt p {
    width: 100px;
    display: inline-block;
  }

  .answerSheet ul {
    padding: 10px;
    text-align: left;
  }

  .answerSheet li {
    display: inline-block;
    margin-bottom: 5px;
    height: 30px;
    width: 30px;
    line-height: 30px;
    text-align: center;
    border: 1px solid #e4e4e4;
  }

  .answerSheet li a {
    display: block;
  }

  .answerSheet li:hover {
    color: #389fc3;
    border-color: #389fc3;
  }

  .rt_more_tt h2 {
    width: 150px;
    font-size: 14px;
    display: inline-block;
  }

  .rt_more_tt p {
    width: 100px;
    display: inline-block;
  }

  .rt_more_nr ul {
    padding: 10px;
    text-align: left;
  }

  .rt_more_nr ul li {
    display: inline-block;
    margin: 5px;
    height: 30px;
    width: 30px;
    line-height: 30px;
    text-align: center;
    border: 1px solid #e4e4e4;
  }
</style>
