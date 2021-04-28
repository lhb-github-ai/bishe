<template>
<div style="margin-top:60px;margin-bottom:197px;">
    <h2 style="margin-left:25%;font-size:25px;">编辑资料</h2>
    <p class="line"></p>
    <p  style="margin-left:30%">年龄:<el-input value = "" v-model="student.age" ></el-input></p>
    <p  >性别:<el-input v-model="student.sex" ></el-input></p>
    <p  style="margin-left:30%">电话号码:<el-input v-model="student.tel" ></el-input></p>
    <p  >邮箱:<el-input v-model="student.email" ></el-input></p>
     <p  style="margin-left:30%">密码:<el-input v-model="student.pwd" ></el-input></p>
    <el-button type="primary" @click="update" style="margin-top: 330px;margin-left: -600px; " >保存</el-button>
</div>

</template>
<script>
import cookie from 'js-cookie'
import login from '@/api/login'
export default {
    // layout: 'self',

    data () {
      return {
        loginInfo:{},
        student:{},
        aa:"",
        
      }
    },
    methods:{
        update(){
              console.log(this.student);
              login.update(this.student).then(res=>{
                    cookie.set('guli_ucenter', this.student, { domain: 'localhost' })
                     this.$message({
                      message: '修改成功',
                      type: 'success'
                    });
              })
        },
        init(id){
             login.listById(id).then(res=>{
               this.student=res.data.data.student
        })
        },
    },
    created(){
       if(cookie.get('guli_token')==undefined||cookie.get('guli_token')==""){
         this.$router.push('/login');
      }
        var jsonStr = cookie.get("guli_ucenter");
      if (jsonStr) {
            this.loginInfo = JSON.parse(jsonStr)
         this.init(this.loginInfo.id)
      }
    }
}
</script>
<style >
.el-input__inner{
    width:280px;
    margin-top: 5px;
    margin-left: 45px;
    margin-bottom: 20px;
    border:2px solid  #4e5158;
}
 .line{
   float: right;
   width: 100%;
   height: 3px;
   background-color: #E9E9E9; 
   margin-top: 10px;
 }
 p{
   width:30%;
   float: left;
   font-size: 17px;
   color: #999;
   padding:4px 0;
   margin-top: 15px;
 }
 p span{
   margin-left: 7px;
   color: #333;
 }
</style>