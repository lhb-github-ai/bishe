<template>
<div>
  <remote-css href="https://cdn.bootcss.com/twitter-bootstrap/4.2.1/css/bootstrap-grid.min.css"></remote-css>
  <remote-css href="https://cdn.jsdelivr.net/semantic-ui/2.2.4/semantic.min.css"></remote-css>
   <div  class="m-container-small m-padded-tb-big">
  <div class="ui container">
    <!--header-->
    <div class="ui top attached segment">
      <div class="ui middle aligned two column grid">
        <div class="column">
          <h3 class="ui teal header">分类</h3>
        </div>
        <div class="right aligned column">
          共 <span class="ui orange header m-inline-block m-text-thin"> {{totals}} </span> 个
        </div>
      </div>
    </div>

    <div class="ui attached segment m-padded-tb-large">
      <div class="ui labeled button m-margin-tb-tiny" v-for="item in types"    :key="item.id" style="margin-left:18px">
        <a   href="#"  class="ui basic  button "  :class="item.id===activeTypeId?'teal':''" @click="changeType(item.id)">{{item.type}}</a>
        <div class="ui basic  left pointing label " :class="item.id===activeTypeId?'teal':''" >{{item.lenght}}</div>
      </div>
    </div>

    <div class="ui top attached teal segment" v-for="item1 in blogs" :key="item1.id">
      <div class="ui padded vertical segment m-padded-tb-large" >
        <div class="ui middle aligned mobile reversed stackable grid" >
          <div class="eleven wide column">
            <h3 class="ui header" style="font-size:22px" ><a   href="#"  @click="detail(item1.id)" class="m-black" >{{item1.blogTitle}}</a></h3>
            <p class="m-text" v-html="item1.comment"></p>
            <div class="ui grid">
              <div class="eleven wide column">
                <div class="ui mini horizontal link list">
                  <div class="item">
                    <img :src="item1.avatar"   alt="" class="ui avatar image">
                    <div class="content"><a href="#" class="header"  >{{item1.studentName}}</a></div>
                  </div>
                  <div class="item">
                    <i class="calendar icon"></i><span >{{item1.gmtCreate}}</span>
                  </div>
                  <!-- <div class="item">
                    <i class="eye icon"></i> <span >2342</span>
                  </div> -->
                </div>
              </div>
            </div>
          </div>
          <div class="five wide column">
            <a href="#"  target="_blank">
              <img :src="item1.avatar"   alt="" class="ui rounded image">
            </a>
          </div>

        </div>
      </div>
    </div>

  </div>
</div>
      <el-row style="margin-top:10px">
          <el-col :span="10" :offset="8">
        <el-pagination
          background
          layout="total,prev, pager, next, jumper, sizes"
          prev-text="上一页"
          next-text="下一页"
          :current-page="page"
          :page-size="limit"
          :page-sizes="[2, 4, 6, 8,10]"
          @current-change="findPage"
          @size-change="handleSizeChange"
          :total="totals">
        </el-pagination>
          </el-col>
        </el-row>
</div>
</template>
<script>
 import blog from '@/api/blog'
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
      blogs:[],
      types:[],
      activeTypeId:"",
      page:1,
      limit:4,
      totals: 0,
      blogQuery:{},
    }
  },
  mounted(){
   
  },
  methods:{
    detail(id){
          this.$router.push({
                path: `/question/${id}`,
              });
    },
     findPage(page){
        this.page = page;
        this.getListByPage(page,this.limit);
      },
      handleSizeChange(limit){
        this.limit = limit;
        this.getListByPage(this.page,limit);
      },
      getListByPage(page=1){
        // console.log(this.loginInfo.id+"**************");
        blog.listByPage(this.page,this.limit,this.blogQuery).then(res=>{
           console.log(res.data);
          this.blogs=res.data.data.records
          this.totals=res.data.data.total;
          // console.log(res.data);
        })
      },
      changeType(id){
            this.page=1
            this.activeTypeId=id
            console.log(id);
            this.blogQuery.typeId=id
            this.getListByPage()
      },
       getAllType(){
         blog.listAllType().then(res=>{
           this.types=res.data.data.types
           console.log(this.types);
         })
       }
  },
  created(){
    this.getAllType()
    this.getListByPage();
  }
};
</script>
<style scoped>
</style>