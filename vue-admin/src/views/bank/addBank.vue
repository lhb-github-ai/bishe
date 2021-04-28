<template>
  <el-row :gutter="20">
 <el-col :span="8"><div class="grid-content "></div>
  <!-- <router-link :to="'/student/batch'">
    <el-button type="primary"  class="btn1">点击此处进行批量添加</el-button>
  </router-link> -->
   </el-col>
 <el-col :span="8">
 <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="类型名称" prop="name"  class="aaa">
      <el-input v-model="ruleForm.name" class="option-input"></el-input>
    </el-form-item>
    <el-form-item label="类型说明" prop="explain1"  class="aaa">
      <el-input v-model="ruleForm.explain1" class="option-input"></el-input>
    </el-form-item>
    <!-- <el-form-item label="类型数量" prop="bankCount"  class="aaa">
      <el-input v-model="ruleForm.bankCount" readonly class="option-input"></el-input>
    </el-form-item> -->
    <el-button type="primary" @click="saveOrUpdate('ruleForm')">保存</el-button>
    <el-button @click="resetForm('ruleForm')">重置</el-button>
  </el-form>
 </el-col>
</el-row>
</template>

<script>
  import bank  from '@/api/bank'
  export default {
    data() {
      return {
        ruleForm: {
          id: '',
          name: '',
          explain1: '',
          bankCount: '',
        },
        rules: {
          name: [
            { required: true, message: '请输入类型名称', trigger: 'blur' },
          ],
        }
      };
    },
    methods: {
      resetForm(){
         this.ruleForm={}
      }, 
      saveOrUpdate(){
         if(!this.ruleForm.id){  
                this.addBank()
             }else{
               this.updateBank()
             }
      },
      updateBank(){
        bank.updateBank(this.ruleForm).then(res=>{
          console.log(res);
              if (res.data.repeat==='repeat'){
                this.$alert('此题库已经存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })
              }else {
                this.$alert('修改成功,点击跳转到类型列表', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push("/bank/list");
                  }
                })
              }
        })
      },
      addBank(){
        bank.addBank(this.ruleForm).then(res=>{
          // console.log(res);
              if (res.data.repeat==='repeat'){
                this.$alert('此类型已经存在,点击重新输入', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })
              }else {
                this.$alert('添加成功,点击跳转到类型列表', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push("/bank/list");
                  }
                })
              }
        })
      },
      getOneById(id){
          bank.listById(id).then(res=>{
            // console.log(res);
            this.ruleForm=res.data.bank
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
</style>
