<template>
<div>
  <remote-css href="https://cdn.bootcss.com/twitter-bootstrap/4.2.1/css/bootstrap-grid.min.css"></remote-css>
  <remote-css href="https://cdn.jsdelivr.net/semantic-ui/2.2.4/semantic.min.css"></remote-css>

<div  class="m-container m-padded-tb-big">
    <div class="ui container">
      <form id="blog-form" action="#" class="ui form">
     <input type="hidden" name="published">
        <input type="hidden" name="id" >
        <div class="required field">
          <div class="ui left labeled input">
            <div class="ui selection compact teal basic dropdown label">
              <input type="hidden" value="原创"  name="flag" >
              <div class="text">原创</div>
              <div class="menu">
                <div class="item" data-value="原创">原创</div>
              </div>
            </div>
            <input type="text" name="title" placeholder="标题" v-model="blog.blogTitle" >
          </div>
        </div>
         <v-container fluid>
              <mavon-editor :toolbars="markdownOption" style="width: 100%; height: 432px;"
                            :ishljs="true" @change="updateDoc" @save="saveDoc"/>
          </v-container>
            <div class="required field">
            <div class="ui left labeled action input">
              <label class="ui compact teal basic label">简略描述语</label>
            
                 <input type="text" v-model="blog.comment" placeholder="请输入简略描述语"  >
          
            </div>
          </div>
    
       <div class="required field">
            <div class="ui left labeled action input">
              <label class="ui compact teal basic label">分类</label>
               <el-select v-model="blog.typeId" placeholder="请选择分类" class="bankId-input">
                <el-option
                  v-for="item in types"
                  :key="item.id"
                  :label="item.type"
                  :value="item.id">
                </el-option>
                </el-select>
            </div>
          </div>
        <div class="ui error message"></div>
        <div class="ui right aligned container">
          <!-- <button type="button" id="save-btn" class="ui secondary button">保存</button> -->
          <button type="button" id="publish-btn" class="ui teal button" @click="fabu">发布</button>
        </div>

      </form>
    </div>
  </div>
</div>
</template>
<script>
import blog from '@/api/blog'
import cookie from 'js-cookie'
export default {
    components:{
       'remote-css': {
            render(createElement) {            
              return createElement('link', { attrs: { rel: 'stylesheet', href: this.href }});
            },
            props: {
            href: { type: String, required: true },
            },
        },
        'remote-js': {
            render(createElement) {
              return createElement('script', { attrs: { type: 'text/javascript', src: this.src }});            
            },
            props: {
            src: { type: String, required: true },
            },
        },

    },
  name: "baseReport",
  data() {
    return {
        types:[],
        loginInfo:{},
        blog:{},
        markdownOption: {
          bold: true, // 粗体
          italic: true, // 斜体
          header: true, // 标题
          underline: true, // 下划线
          strikethrough: true, // 中划线
          mark: true, // 标记
          // superscript: true, // 上角标
          // subscript: true, // 下角标
          quote: true, // 引用
          ol: true, // 有序列表
          ul: true, // 无序列表
          link: true, // 链接
          imagelink: true, // 图片链接
          code: true, // code
          table: true, // 表格
          fullscreen: false, // 全屏编辑
          readmodel: false, // 沉浸式阅读
          htmlcode: true, // 展示html源码
          help: true, // 帮助
          undo: true, // 上一步
          redo: true, // 下一步
          trash: true, // 清空
          save: true, // 保存（触发events中的save事件）
          navigation: true, // 导航目录
          alignleft: true, // 左对齐
          aligncenter: true, // 居中
          alignright: true, // 右对齐
          subfield: true, // 单双栏模式
          preview: true, // 预览
        },
        html:""
    }
  },
  mounted(){
   
  },
  methods:{
    updateDoc(markdown, render) {
        this.html = render;
        this.markdown = markdown;
        console.log(this.html+"*");
              //  console.log(this.markdown+"**");
      },
      saveDoc(markdown, render) {
        this.html = render;
        this.markdown = markdown;
        // console.log(this.html+"***");
        // console.log(this.markdown)+"****";
      },
   fabu(){
     this.blog.blog=this.html 
       this.blog.blogId=this.loginInfo.id
              console.log(this.blog);
       blog.aaddBlog(this.blog).then(res=>{
              this.$router.push("/question")
       })
   },
    getAllType(){
         blog.listAllType().then(res=>{
           this.types=res.data.data.types
           console.log(this.types);
         })
       }
  },
  created(){
       if(cookie.get('guli_token')==undefined||cookie.get('guli_token')==""){
         this.$router.push('/login');
      }
      this.getAllType()
      var jsonStr = cookie.get("guli_ucenter");
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr)
        console.log(this.loginInfo);
      }
      
  }
};
</script>

<style scoped>
/* .question-input >>> .el-input{
 
  width: 00px;
 border-color: #303133;
} */
</style>