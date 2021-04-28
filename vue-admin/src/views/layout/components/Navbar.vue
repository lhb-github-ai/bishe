<template>
  <el-menu class="navbar" mode="horizontal">
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>
    <breadcrumb />
    <el-dropdown class="avatar-container" trigger="click">
      <div class="avatar-wrapper">
        <img :src="user.avatar" class="user-avatar">
        <i class="el-icon-caret-bottom"/>
      </div>
      <el-dropdown-menu slot="dropdown" class="user-dropdown">
        <router-link class="inlineBlock" to="/">
          <el-dropdown-item>
            首页
          </el-dropdown-item>
        </router-link>
          <el-dropdown-item divided>
          <span style="display:block;" @click="dialog=true">修改个人资料</span>
        </el-dropdown-item>
         <el-dropdown-item divided>
          <span style="display:block;" @click="dialogVisible=true">修改头像</span>
        </el-dropdown-item>
        <el-dropdown-item divided>
          <span style="display:block;" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
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
        <el-dialog
              title="修改个人资料"
              :visible.sync="dialog"
              width="40%"
              >
          <el-form >
            <el-form-item label="电话号码" class="aaa">
                  <el-input v-model="user.tel" ></el-input>
            </el-form-item>
            <el-form-item label="邮箱" class="aaa">
                  <el-input v-model="user.email" ></el-input>
            </el-form-item>
            <el-form-item label="密码" class="aaa">
                  <el-input v-model="user.pwd" ></el-input>
            </el-form-item>
         </el-form>
                <el-button type="primary" style="margin-left:40%" @click="queding">确 定</el-button>
        </el-dialog>
  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import teacher from '@/api/teacher'
import { removeToken } from '@/utils/auth'
export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar'
    ])
  },
  data () {
    return {
      dialogVisible:false,
      dialog:false,
      imageUrl:"",
      student:{},
      user:{},
    }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },
    logout() {
        //  removeToken()
         localStorage.removeItem("id");
         localStorage.removeItem("role");
        //  this.$router.push('/login')
         this.$store.dispatch('LogOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    },
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
      queding(){
         this.dialog = false
         this.dialogVisible = false
          // console.log(this.user);
          this.user.avatar= this.imageUrl 
          if(this.user.role=="1"){
                   teacher.updateTeacher(this.user).then(res=>{
                       this.$message({
                      message: '修改成功',
                      type: 'success'
                    });
                  // console.log(res.data);
                    // cookie.set('guli_ucenter', this.student, { domain: 'localhost' })
                    //  this.showInfo()
              })
          }
           if(this.user.role=="0"){
                   teacher.updateAdmin(this.user).then(res=>{
                       this.$message({
                      message: '修改成功',
                      type: 'success'
                    });
                  // console.log(res.data);
                    // cookie.set('guli_ucenter', this.student, { domain: 'localhost' })
                    //  this.showInfo()
              })
          }
              
    },
    showInfo(){
     var id=  localStorage.getItem("id")
      var role=  localStorage.getItem("role")
       if(role=="0"){
        //  alert("0")
          teacher.listByIdAdmin(id).then(res=>{
            this.user=res.data.admin
            // console.log( this.user);
            this.imageUrl=this.user.avatar
          })
       }
       if(role=="1"){
          //  alert("2")
           teacher.listById(id).then(res=>{
            this.user=res.data.teacher
            // console.log( this.user);
            this.imageUrl=this.user.avatar
          })
       }
    }
  },
  created(){
    this.showInfo()
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    .avatar-wrapper {
      cursor: pointer;
      margin-top: 5px;
      position: relative;
      line-height: initial;
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 25px;
        font-size: 12px;
      }
    }
  }
}
</style>

