<template>
  <el-row :gutter="20">
 <el-col :span="5"><div class="grid-content "></div>
  <!-- <router-link :to="'/student/batch'">
    <el-button type="primary"  class="btn1">点击此处进行批量添加</el-button>
  </router-link> -->
  <div>&nbsp;</div>
   </el-col>
 <el-col :span="8" style="margin-top:50px">
     <p><b style="font-size:20px;margin-left:170px;">添加轮播图</b></p>
 <el-form :model="banner"  ref="banner" label-width="100px" class="demo-ruleForm">
    <el-form-item label="轮播图" class="aaa">
                <el-upload
            class="avatar-uploader"
            action="http://localhost:8001/avatar"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
    <!-- <el-form-item label="类型数量" prop="bankCount"  class="aaa">
      <el-input v-model="ruleForm.bankCount" readonly class="option-input"></el-input>
    </el-form-item> -->
    <el-button style="margin-left:140px;margin-top:20px" type="primary" @click="saveOrUpdate()">保存</el-button>
  </el-form>
 </el-col>
</el-row>
</template>

<script>
  import banner  from '@/api/banner'
  export default {
    data() {
      return {
          imageUrl: 'https://www.hualigs.cn/image/6058803b2e8a1.jpg',
          banner: {
          id: '',
          imageUrl: '',
          adminId:''
        },
      };
    },
    methods: {
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
    
      saveOrUpdate(){
         if(!this.imageUrl.id){  
                this.addNotice()
             }else{
               this.updateNotice()
             }
      },
      updateNotice(){
          this.banner.imageUrl= this.imageUrl 
          console.log(this.banner);
        banner.updateNotice(this.notice).then(res=>{
          console.log(res);
              if (res.data.repeat==='repeat'){
                this.$alert('此题库已经存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })
              }else {
                this.$alert('修改成功,点击跳转到轮播图列表界面', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push("/banner/index");
                  }
                })
              }
        })
      },
      addNotice(){
          this.banner.imageUrl= this.imageUrl 
          console.log(this.banner);
        banner.addNotice(this.banner).then(res=>{
          // console.log(res);
              if (res.data.repeat==='repeat'){
                this.$alert('此类型已经存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })
              }else {
                this.$alert('添加成功,点击跳转到轮播图列表页面', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push("/banner/index");
                  }
                })
              }
        })
      },
      getOneById(id){
          banner.listById(id).then(res=>{
            // console.log(res);
            this.notice=res.data.notice
          })
      },
      init(){
            if (this.$route.params && this.$route.params.id) {
                      const id = this.$route.params.id
                            this.getOneById(id)
                          // console.log(this.$route.params.id);
              }else{
                      this.ruleForm={}
                   }
         }
    },
     watch:{
         $route(to,from){
             this.init();
         }
    },
    created(){
       this.init();
       this.banner.adminId=localStorage.getItem("id")
    }
  }
</script>
<style scoped>
.option-input >>> .el-input__inner{
 border-color: #303133;
}
.aaa  >>> .el-form-item__label{
   color: black;
   font-weight: 52px;
   text-align: center;
}
.question-input >>> .el-textarea__inner{
  height: 90px;
  width: 70%;
 border-color: #303133;
 
}
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 500px;
    height: 300px;
    display: block;
  }
</style>
