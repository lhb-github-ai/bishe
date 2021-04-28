<template>
<div style=" background-color: #eee;">
 <div id="msg">
    <div class="title" style="padding-top:16px">
        <span style="font-size:16px">首页</span>
        \
        <span  style="font-size:16px">考试中心</span>
        \
        <span style="font-size:16px" class="c-333 fsize14">{{examData.paperName}}</span>
    </div>
    <div class="wrapper">
      <ul class="top">
        <li class="example">{{examData.paperName}}</li>
        <li><i class="iconfont icon-pen-"></i></li>
        <li><i class="iconfont icon-share"></i></li>
      </ul>
      <ul class="bottom">
        <li>更新于{{examData.gmtModified}}</li>
        <li>来自 {{examData.teacherName}} 老师</li>
        <li class="btn">期末考试</li>
        <li class="right"><el-button @click="toAnswer()">开始答题</el-button></li>
      </ul>
      <ul class="info">
        <li @click="dialogVisible = true"><a href="javascript:;"><i class="iconfont icon-info"></i>考生须知</a></li>
      </ul>
    </div>
    <div class="content">
      <el-collapse v-model="activeName" >
        <el-collapse-item class="header" name="0">
          <template slot="title" class="stitle" >
            <div class="title">
              <span>{{examData.paperName}}</span><i class="header-icon el-icon-info"></i>
              <span class="time">{{examData.totalScore}}分 / {{examData.time}}分钟</span>
              <el-button type="primary" size="small">点击查看试题详情</el-button>
            </div>
          </template>
          <el-collapse class="inner">
            <el-collapse-item>
              <template slot="title" name="1">
                <div class="titlei">单选题 (共{{topicCountDX}}题 共计50分)</div>
              </template>
              <!-- <div class="contenti">
                <ul class="question" v-for="(list, index) in topicDX" :key="index">
                  <li>{{index+1}}. {{list.questionName}}。</li>
                </ul>
              </div> -->
            </el-collapse-item>
            <el-collapse-item>
              <template slot="title" name="2">
                <div class="titlei">判断题 (共{{topicCountPD}}题 共计50分)</div>
              </template>
              <!-- <div class="contenti">
                <ul class="question" v-for="(list, index) in topicPD" :key="index">
                  <li>{{topicCountDX+index+1}}. {{list.questionName}}。</li>
                </ul>
              </div> -->
            </el-collapse-item>
          </el-collapse>
        </el-collapse-item>
        
      </el-collapse>
    </div>
    <!--考生须知对话框-->
    <el-dialog
      title="考生须知"
      :visible.sync="dialogVisible"
      width="30%">
      <span>{{examData.description}}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">知道了</el-button>
      </span>
    </el-dialog>
  </div>
  </div>
</template>

<script>
import paper  from '@/api/paper'
import cookie from 'js-cookie'
export default {
    data() {
    return {
      dialogVisible: false, //对话框属性
      activeName: '0',  //默认打开序号
      topicDX:{},
      topicPD:{},
      topicCount: [],//每种类型题目的总数
      topicCountDX: [],//每种类型题目的总数
      topicCountPD: [],//每种类型题目的总数
      score: [],  //每种类型分数的总数
      examData: { //考试信息
        // source: null,
        // totalScore: null,
      },
      topic: {  //试卷信息

      },
      paperId:"",
    }
  },
  methods:{
    init(){
          paper.GetById(this.paperId).then(res=>{
               // console.log(res.data.data);
                this.examData=res.data.data.paper
                cookie.set("time",this.examData.time)
          })
          this.listPDQuestion()
          this.listDXQuestion()
    },
    listDXQuestion(){
             paper.listQuestionDX(this.paperId,0).then(res=>{
             console.log(res.data.data)
                //this.DXData=res.data.paperQuestionsDX
                this.topicDX=res.data.data.paperQuestionsDX
                this.topicCountDX=res.data.data.paperQuestionsDX.length
                    console.log(res.data.data.paperQuestionsDX.length)

             })
       },
        listPDQuestion(){
             paper.listQuestionPD(this.paperId,1).then(res=>{
                console.log(res.data.data)
              //  this.PDData=res.data.paperQuestionsPD
               this.topicPD=res.data.data.paperQuestionsPD
                this.topicCountPD=res.data.data.paperQuestionsPD.length
                 console.log(res.data.data.paperQuestionsPD.length)
             })
       },
    toAnswer(id) {
        this.$router.push(`/answer/${this.paperId}`);
    //  this.$router.push({path:`/answer/${this.paperId}`,})
    }, 
  },
  created(){
      if(cookie.get('guli_token')==undefined||cookie.get('guli_token')==""){
         this.$router.push('/login');
      }
    this.paperId=this.$route.params.id
    this.init()
  }
};
</script>
<style  scoped>
.bottom  .right .el-button{
  color: #409EFF;
      border-color: #c6e2ff;
      background-color: #ecf5ff;
}
.right {
  margin-left: auto;
}
.inner .contenti .question {
  margin-left: 40px;
  color: #9a9a9a;
  font-size: 14px;
}
.content .inner .titlei {
  margin-left: 20px;
  font-size: 16px;
  color: #88949b;
  font-weight: bold;
}
.content .title .time {
  font-size: 16px;
  margin-left: 420px;
  color: #999;
}
.content .stitle {
  background-color: #0195ff;
}
.content .title span {
  margin-right: 10px;
}
#msg .content .title {
  font-size: 20px;
  margin: 0px;
  display: flex;
  align-items: center;
}
.content {
  margin-top: 20px;
  background-color: #fff;
}
.content .header {
  padding: 10px 30px;
}
.wrapper .info {
  margin: 20px 0px 0px 20px;
  border-top: 1px solid #eee;
  padding: 20px 0px 10px 0px;
}
.wrapper .info a {
  color: #88949b;
  font-size: 14px;
}
.wrapper .info a:hover {
  color: #0195ff;
}
.wrapper .bottom .btn {
  cursor: pointer;
  padding: 5px 10px;
  border: 1px solid #88949b;
  border-radius: 4px;
} 
.wrapper .bottom {
  display: flex;
  margin-left: 20px;
  color: #999;
  font-size: 14px;
  align-items: center;
}
.wrapper .bottom li {
  margin-right: 14px;
}
#msg {
  background-color: #eee;
  width: 980px;
  margin: 0 auto;
}
#msg .title {
  margin: 20px;
}
#msg .wrapper {
  background-color: #fff;
  padding: 10px;
}
.wrapper .top {
  display: flex;
  margin: 20px;
  align-items: center;
}
.wrapper .top .right {
  margin-left: auto;
}
.wrapper .top .example {
  color: #333;
  font-size: 22px;
  font-weight: 700;
}
.wrapper .top li i {
  margin-left: 20px;
  color: #88949b;
}
.wrapper .right .count {
  margin-right: 60px;
  color: #fff;
  padding: 4px 10px;
  background-color: #88949b;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
  border: 1px solid #88949b;
}
.wrapper .right .score {
  position: absolute;
  left: 53px;
  top: -5px;
  padding: 1px 12px;
  font-size: 20px;
  color: #88949b;
  border: 1px dashed #88949b;
  border-left: none;
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
  font-weight: bold;
}
.wrapper .right div {
  position: relative;
}
</style>
