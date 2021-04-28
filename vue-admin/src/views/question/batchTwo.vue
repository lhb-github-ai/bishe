<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
         <el-tooltip content="请点击模板进行下载模板填入相关信息，否则无法导入excel！！！" placement="top">
        <el-tag type="info">excel模版说明</el-tag>
         </el-tooltip>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="'/static/demo2.xlsx'">模版</a>
        </el-tag>
      </el-form-item>
      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API+'/exam/judge_question/addBatch'"
          name="file"
          accept="application/vnd.ms-excel">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload">上传到服务器</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
    data(){
        return{
            BASE_API: process.env.BASE_API, // 接口API地址
            // OSS_PATH: process.env.OSS_PATH, // 阿里云OSS地址
            importBtnDisabled: false, // 按钮是否禁用,
            loading: false
        }
    },
    created(){

    },
    methods:{
        fileUploadSuccess(res){
            console.log(res.data);
            if(res.data.msg=="fail"){
               this.$alert('您的excel表格中题库名不存在，请改正excel表格之后重试', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    location.reload();
                  }
                })  
            }
                this.loading = false
                this.$message({
                    type: 'success',
                    message: "添加判断题目成功"
                })
                 this.$router.push("/question/judgeQuestion");
        },
        submitUpload(){
            this.importBtnDisabled = true
            this.loading = true
            this.$refs.upload.submit()
        },
        fileUploadError(){
                    this.loading = false
                    this.$message({
                    type: 'error',
                    message: '添加判断题目失败'
                    })
                  }
            }
}
</script>
<style scoped>

</style>