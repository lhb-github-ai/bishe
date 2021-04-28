<template>
<el-row :gutter="20">
 <el-col :span="6"><div class="grid-content "></div>
  <!-- <router-link :to="'/student/batch'">
    <el-button type="primary"  class="btn1">点击此处进行批量添加</el-button>
  </router-link> -->
   </el-col>
 <el-col :span="10">
 <el-form :model="teacher" label-width="100px" class="demo-ruleForm">
    <el-form-item label="工号"  class="aaa">
      <el-input v-model="teacher.teacherId" class="option-input" placeholder="请输入老师工号"></el-input>
    </el-form-item>
    <el-form-item label="老师姓名" class="aaa">
      <el-input v-model="teacher.teacherName" class="option-input" placeholder="请输入老师姓名"></el-input>
    </el-form-item>
    <el-form-item label="性别"  class="aaa bankId-input">
       <el-select v-model="teacher.sex" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
  </el-select>
    </el-form-item>
    <el-form-item label="职称"  class="aaa">
      <el-input v-model="teacher.type"  class="option-input" placeholder="请输入老师职称"></el-input>
    </el-form-item>
     <el-form-item label="电话号码"  class="aaa">
      <el-input v-model="teacher.tel"  class="option-input" placeholder="请输入电话号码"></el-input>
    </el-form-item>
    <el-form-item label="邮箱"  class="aaa">
      <el-input v-model="teacher.email"  class="option-input" placeholder="请输入邮箱"></el-input>
    </el-form-item>
     <!-- <el-form-item label="密码"  class="aaa">
      <el-input v-model="student.pwd"  class="option-input" placeholder="请输入密码"></el-input>
    </el-form-item> -->
    <el-form-item label="头像" class="aaa">
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
    <br>
    <el-button type="primary" @click="saveOrUpdate()" class="btn">添加</el-button>
  </el-form>
 </el-col>
</el-row>
</template>

<script>
import teacher from '@/api/teacher'  
  export default {
    data() {
      return {
        // 
         imageUrl: 'https://www.hualigs.cn/image/6058803b2e8a1.jpg',
         teacher:{},
         options: [{
          value: '男',
          label: '男'
        }, {
          value: '女',
          label: '女'
        },{
          value: '未知',
          label: '未知'
        }],    
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
      update(){
         this.teacher.avatar= this.imageUrl 
           teacher.updateTeacher(this.teacher).then(res=>{
          console.log(res);
              if (res.data.repeat==='repeat'){
                this.$alert('该题目已存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })
              } else {
                this.$alert('修改成功,点击跳转到老师信息列表', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push("/teacher/index");
                  }
                })
              }
        })
      },
      saveOrUpdate(){
            if(!this.teacher.id){  
                this.submitForm()
             }else{
               this.update()
             }
      },
      submitForm(){
 this.teacher.avatar= this.imageUrl 
        console.log(this.teacher);
                 teacher.addTeacher(this.teacher).then(res=>{
              if (res.data.repeat==='repeat'){
                this.$alert('此题目已经存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })
              }else {
                this.$alert('添加成功,点击跳转到老师信息列表', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push("/teacher/index");
                  }
                })
              }
        })
       
      },
      getOneById(id){
        teacher.listById(id).then(res=>{
          this.teacher=res.data.teacher
          this.imageUrl=this.teacher.avatar
           console.log(res);
        })
      },
      init(){
          if (this.$route.params && this.$route.params.id) {
                      const id = this.$route.params.id
                            this.getOneById(id)
                          // console.log(this.$route.params.id);
              }else{
                      this.teacher={}
                   }   
      },
    },
    created(){
       this.init()
    },
       watch:{
         $route(to,from){
             this.init();
         }
    },
    
  }
</script>
<style scoped>


.question-input >>> .el-textarea__inner{
  height: 90px;
  width: 70%;
 border-color: #303133;
 
}
/* el-textarea */
/*  */
.option-input >>> .el-input__inner{
 border-color: #303133;
}
.analysis-input >>> .el-input__inner{
 border-color: #303133;
}

.aaa  >>> .el-form-item__label{
   color: black;
   font-weight: 52px;
   text-align: center;
}


.left{

    padding-left: 15px;
    color: black;
    font-weight: 550;
 
}   
  .grid-content {
    min-height: 1px;
  }
  .bankId-input >>> .el-input--suffix .el-input__inner{
          margin-bottom: 15px;
           margin-top:10px;
            border-color: #303133;

  }
  .btn{
   margin-left: 50%;
   font-size: 20px;
   
  }
  .btn1{
    margin-top: 20px;
    margin-left: 22px;
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
    width: 178px;
    height: 178px;
    display: block;
  }
    .bankId-input >>> .el-input--suffix .el-input__inner{
          margin-bottom: 15px;
           margin-top:10px;
            border-color: #303133;

  }
</style>
