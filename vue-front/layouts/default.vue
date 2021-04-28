<template>
  <div class="in-wrap" >
    <!-- 公共头引入 -->
    <header id="header" style="margin-top:-20px">
      <section class="container">
        <h1 id="logo">
          <a href="#" title="在线考试系统">
            <img src="~/assets/img/logo.png" width="100%" alt="在线考试系统">
          </a>
        </h1>
        <div class="h-r-nsl">
          <ul class="nav">
           <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/paper" tag="li" active-class="current">
              <a>考试中心</a>
            </router-link>
            <router-link to="/testPaper" tag="li" active-class="current">
              <a>测试中心</a>
            </router-link>
            <router-link to="/MyExam" tag="li" active-class="current">
              <a>我的考试</a>
            </router-link>
            <router-link to="/question" tag="li" active-class="current">
              <a>交流区</a>
            </router-link>
          </ul>
           <!-- / nav -->
            <ul class="h-r-login">
                <!-- <li v-if="!loginInfo.id" id="no-login">
                    <a href="/login" title="登录">
                        <em class="icon18 login-icon">&nbsp;</em>
                        <span class="vam ml5">登录</span>
                    </a>
                    |
                    <a href="/register" title="注册">
                        <span class="vam ml5">注册</span>
                    </a>
                </li> -->
                <li v-if="loginInfo.id"  class="mr10">
                   
                    <q class="red-point" style="display: none">&nbsp;</q>
                </li>
                <li v-if="loginInfo.id" class="h-r-user" >
                   <div style="float:right" class="h_cover"><img
                            :src="loginInfo.avatar"
                            width="50"
                            height="50"
                            class="vam picImg"
                            >
                            <a  @click="xiugai" class="avatar-cover" style="font-size:10px;color: #fff" >修改头像</a>
                    </div>
                    <a  title>
                      <el-dropdown trigger="click">
                    <span class="el-dropdown-link">
                       <span id="userName" class="vam disIb">{{ loginInfo.studentName }}</span><i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                     <a id="headerMsgCountId" href="/ucenter"  title="个人资料"> 
                     <el-dropdown-item icon="el-icon-user"> 
                        <span>个人资料</span>
                    </el-dropdown-item>
                    </a>
                    <a id="updateUcenter" href="/updateUcenter"  title="修改个人资料">
                      <el-dropdown-item icon="el-icon-circle-plus">
                      <span>修改个人资料</span>
                      </el-dropdown-item>
                    </a>
                      <a  href="/blog"   title="退出" >
                      <el-dropdown-item icon="el-icon-chat-square">
                        <span>发布信息交流</span>
                        </el-dropdown-item>
                      </a>
                      <a href="javascript:void(0);" title="退出" @click="logout()" >
                   <el-dropdown-item icon="el-icon-error"><span>退出</span></el-dropdown-item>
                   </a>
                    </el-dropdown-menu>
                  </el-dropdown>
                       
                    </a>
                   
                </li>
                <!-- /未登录显示第1 li；登录后显示第2，3 li -->
            </ul>
          <aside class="h-r-search">
            <form action="#" method="post">
              <!-- <label class="h-r-s-box">
                <input type="text" placeholder="输入你想学的课程" name="queryCourse.courseName" value>
                <button type="submit" class="s-btn">
                  <em class="icon18">&nbsp;</em>
                </button>
              </label> -->
            </form>
          </aside>
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>
    <!-- /公共头引入 -->
    <nuxt/>
    <!-- 公共底引入 -->
    <footer id="footer" style="margin-top:49px">
      <section class="container" >
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
               <span>项目链接</span> <a href="http://www.atguigu.com/" title="github" target="_blank">github地址</a>|
              </section>
              <section class="b-f-link">
                <a href="http://www.hnuit.edu.cn/" title="关于我们" target="_blank">关于学校</a>|
                <span>招生热线:    0731-84098666</span>
                <span>Email：1799044809@qq.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>©2021-湖南信息学院软件工程毕设项目   Designer--李恒彪 </span>
              </section>
            </section>
         </section>
         <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wx-icon.png" alt>
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wb-icon.png" alt>
              </span>
            </section>
          </aside>
          <div class="clear"></div>

      </section>
    </footer>
    <!-- /公共底引入 -->
            <el-dialog
              title="修改头像"
              :visible.sync="dialogVisible"
              width="30%"
              >
              <el-form >
            <el-form-item  style="margin-left:60px" class="aaa">
                <el-upload
            class="avatar-uploader"
            action="http://localhost:8001/avatar"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar" width="350" height="350">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          </el-form-item>
         </el-form>
                <el-button type="primary" style="margin-left:40%" @click="queding">确 定</el-button>
        </el-dialog>
  </div>
</template>
<script>
import "~/assets/css/reset.css";
import "~/assets/css/theme.css";
import "~/assets/css/global.css";
import "~/assets/css/web.css";
import cookie from 'js-cookie'
import login from '@/api/login'
export default {

  data() {
    return {
      dialogVisible:false,
      imageUrl:"",
      token: '',
      loginInfo: {
        id: '',
        age: '',
        avatar: '',
        mobile: '',
        nickname: '',
        sex: '',
      },
      student:{},
    }
  },

  created() {
    this.token=this.$route.query.token
    // if(this.token){
    //    this.wxLogin()
    // }
    this.showInfo()
  },

  methods: {
    // wxLogin(){
    //         cookie.set('guli_token',this.token, { domain: 'localhost' })
    //         cookie.set('guli_ucenter', "", { domain: 'localhost' })

    //         userApi.getUserInfo().then(res=>{
    //           this.loginInfo = res.data.data.userInfo
    //             cookie.set('guli_ucenter',  this.loginInfo , { domain: 'localhost' })
    //         })
    // },
      handleAvatarSuccess(res, file) {
       this.imageUrl = res.data.url;
       
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
    showInfo() {
      //debugger
      var jsonStr = cookie.get("guli_ucenter");
       //console.log(jsonStr);
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr)
      }
        login.listById(this.loginInfo.id).then(res=>{
               this.student=res.data.data.student
               this.imageUrl = this.student.avatar;
        })
    },

    logout() {
      //debugger
      cookie.set('guli_ucenter', "", {domain: 'localhost'})
      cookie.set('guli_token', "", {domain: 'localhost'})
      //跳转页面
      window.location.href = "/login"
    },
    xiugai(){
      console.log(this.dialogVisible);
           this.dialogVisible = true
    },
    queding(){
         this.dialogVisible = false
          console.log(this.student);
          this.student.avatar= this.imageUrl 
              login.update(this.student).then(res=>{
                  console.log(res.data);
                    cookie.set('guli_ucenter', this.student, { domain: 'localhost' })
                     this.showInfo()
              })
    },

  }
};
</script>
<style >
.h_cover :hover{
   opacity: 1;
}
.avatar-cover{
    position: absolute;
    top: 0;
    left: 0;
    width: 50px;
    height: 50px;
    background: rgba(0, 0, 0, 0.6);
    /* font-size: 1px; */
    /* color: rgb(248, 6, 6); */
    /* line-height: 64px; */
    text-align: center;
    opacity: 0;
    /* border-radius: 50%; */
    transition: opacity .2s;
    margin-left:53px;
}
</style>