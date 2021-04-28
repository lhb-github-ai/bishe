<template>
<div style="margin-top:120px;margin-bottom:525px;">

    <h2 style="margin-left:25%;font-size:25px;">基础信息</h2>
    <p class="line"></p>
    <p  style="margin-left:30%">年龄:<span>{{loginInfo.age}}</span></p>
    <p  >性别:<span>{{loginInfo.sex}}</span></p>
    <p  style="margin-left:30%">班级:<span>{{loginInfo.clazz}}</span></p>
    <p  >学院:<span>{{loginInfo.institute}}</span></p>
    <p  style="margin-left:30%">电话号码:<span>{{loginInfo.tel}}</span></p>
    <p  >邮箱:<span>{{loginInfo.email}}</span></p>
</div>

</template>
<script>
import cookie from 'js-cookie'
export default {
    // layout: 'self',
    data () {
      return {
        student:{
          studentId:'',
          password:''
        },
        loginInfo:{}
      }
    },
    methods:{

    },
    created(){
       if(cookie.get('guli_token')==undefined||cookie.get('guli_token')==""){
         this.$router.push('/login');
      }
           var jsonStr = cookie.get("guli_ucenter");
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr)
        console.log(this.loginInfo);
      }
    }
}
</script>
<style scoped>
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
