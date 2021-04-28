<template>
    <!--中间内容-->
<div id="waypoint" class="m-container-small m-padded-tb-big animated fadeIn">\
    <remote-css href="https://cdn.bootcss.com/twitter-bootstrap/4.2.1/css/bootstrap-grid.min.css"></remote-css>
    <remote-css href="https://cdn.jsdelivr.net/semantic-ui/2.2.4/semantic.min.css"></remote-css>
  <div class="ui container">
    <div class="ui top attached segment">
      <div class="ui horizontal link list">
        <div class="item">
          <img :src="blog.avatar"   alt="" class="ui avatar image">
          <div class="content"><a href="#" class="header" >{{blog.studentName}}</a></div>
        </div>
        <div class="item">
          <i class="calendar icon"></i> <span >{{blog.gmtCreate}}</span>
        </div>
        <!-- <div class="item">
          <i class="eye icon"></i> <span >2342</span>
        </div> -->
      </div>
    </div>
    <!-- <div class="ui attached segment">
      图片区域
      <img :src="https://unsplash.it/800/450?image=1005"  alt="" class="ui fluid rounded image">
    </div> -->
    <div class="ui  attached padded segment">
      <!--内容-->
      <div class="ui right aligned basic segment">
        <div class="ui orange basic label" >原创</div>
      </div>

      <h2 class="ui center aligned header" >{{blog.blogTitle}}</h2>
      <br>
      <!--中间主要内容部分-->
      <!-- <div id="content" v-html="blog.blog" class="typo  typo-selection js-toc-content m-padded-lr-responsive m-padded-tb-large" > -->
      <div style="margin-left:10px" v-html="blog.blog" class="markdown-body">
      </div>

      <!-- </div > -->
      <!--标签-->
      <!-- <div class="m-padded-lr-responsive">
        <div class="ui basic teal left pointing label"  >方法论</div>
      </div> -->

    </div>
    <div class="ui attached positive message" >
      <!--博客信息-->
      <div class="ui middle aligned grid">
        <div class="eleven wide column">
          <ui class="list">
            <li>作者：<span >{{blog.studentName}}</span><a href="#"  target="_blank">（联系作者）</a></li>
            <li>发表时间：<span >{{blog.gmtCreate}}</span></li>
            <li>版权声明：自由转载-非商用-非衍生-保持署名（创意共享3.0许可证）</li>
            <li>公众号转载：请在文末添加作者公众号二维码</li>
          </ui>
        </div>
        <div class="five wide column">
          <!-- <img src="../static/images/wechat.jpg" th:src="@{/images/wechat.jpg}" alt="" class="ui right floated rounded bordered image" style="width: 110px"> -->
        </div>
      </div>
    </div>
    <div  class="ui bottom attached segment">
      <!--留言区域列表-->
      <div id="comment-container"  class="ui teal segment" >
        <div>
          <div class="ui threaded comments" style="max-width: 100%;">
            <h3 class="ui dividing header">评论</h3>
            <div class="comment" v-for="comment in comments" :key="comment.id">
              <a class="avatar">
                <img :src="comment.avatar" >
              </a>
              <div class="content">
                <a class="author" >
                  <span >{{comment.studentName}}</span>
                  <!-- <div class="ui mini basic teal left pointing label m-padded-mini" > 博主</div> -->
                </a>
                <div class="metadata">
                  <span class="date" >{{comment.gmtCreate}}</span>
                </div>
                <div class="text" >
                  {{comment.content}}
                </div>
                <div class="actions">
                  <a class="reply" data-commentid="1" data-commentnickname="Matt"  @click="reply(comment)">回复</a>
                </div>
              </div>
              <div class="comments" v-if="comment.replyComments.length>0">
                <div class="comment" v-for="reply in comment.replyComments" :key="reply.id">
                  <a class="avatar">
                    <img :src="reply.avatar" >
                  </a>
                  <div class="content">
                    <a class="author" >
                      <span >{{reply.studentName}}</span>
                      <!-- <div class="ui mini basic teal left pointing label m-padded-mini" > 博主</div> -->
                      &nbsp;<span  class="m-teal" style="color:#00B5AD">@  {{reply.parentName}}</span>
                    </a>
                    <div class="metadata">
                      <span class="date" >{{reply.gmtCreate}}</span>
                    </div>
                    <div class="text" >
                      {{reply.content}}
                    </div>
                    <div class="actions">
                      <a class="reply" data-commentid="1" data-commentnickname="Matt" @click="replyTwo(reply)">回复</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="comment-form" class="ui form">
        <input type="hidden" name="parentComment.id" v-model="comment.parentCommentId">
        <div class="field">
          <textarea name="content" :placeholder="tip" v-model="comment.content"></textarea>
        </div>
        <div class="fields">
          <div class="field m-mobile-wide m-margin-bottom-small">
            <div class="ui left icon input">
              <i class="user icon"></i>
              <input type="text" name="nickname" placeholder="姓名" v-model="loginInfo.studentName" disabled>
            </div>
          </div>
          <div class="field m-mobile-wide m-margin-bottom-small">
            <div class="ui left icon input">
              <i class="mail icon"></i>
              <input type="text" name="email" placeholder="邮箱" v-model="loginInfo.email" disabled>
            </div>
          </div>
          <div class="field  m-margin-bottom-small m-mobile-wide">
            <button id="commentpost-btn" type="button" class="ui teal button m-mobile-wide" @click="addComment"><i class="edit icon"></i>发布</button>
          </div>
        </div>

      </div>
    </div>
  </div>
</div>
</template>
<script>
import cookie from 'js-cookie'
import blog from '@/api/blog'
export default{
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
    data() {
      return {
         blog:{},
         loginInfo:{},
         comment:{
           parentCommentId:0
         },
         id:"",
         comments:[],
         tip:"请输入评论信息...",
      }
    },
    methods:{
            replyTwo(reply){
               this.comment.leavl=reply.leavl
               this.comment.parentCommentId=reply.id
               this.tip="@   "+reply.studentName
              console.log(reply);
            },
            reply(comment){
                  this.comment.leavl=comment.leavl
                  this.comment.parentCommentId=comment.id
                   this.tip="@   "+comment.studentName
                   console.log(comment);
            },
            init(){
                 blog.listById(this.id).then(res=>{
                   this.blog=res.data.data.blog
                  //  console.log(res.data.data);
                 })
                 this.listComment()
            },
            listComment(){
                blog.listAllComment(this.id).then(res=>{
                    this.comments=res.data.data.comments

                        console.log(this.comments);
                })
            },
            addComment(){
                 this.comment.studentId=this.loginInfo.id
                 this.comment.blogId=this.id
                 blog.aaddComment(this.comment).then(res=>{
                   this.comment.content=""
                   this.comment.parentCommentId=0
                   this.tip="请输入评论信息..."
                    this.init()
                 })
            },
            
    },
    
    created(){
        if(cookie.get('guli_token')==undefined||cookie.get('guli_token')==""){
         this.$router.push('/login');
      }
      this.id=this.$route.params.id
       this.init()
        var jsonStr = cookie.get("guli_ucenter");
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr)
        // console.log(this.loginInfo);
      }
    }
}
</script>

<style scoped>

</style>