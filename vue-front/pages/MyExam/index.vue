<template>
    <el-main style="padding-top: 10px">
      <div class="main">
        <div class="bread">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item to="/"  style=" font-size:17px;">首页</el-breadcrumb-item>
            <el-breadcrumb-item style=" font-size:17px;">我的考试</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <el-table
          :data="exams.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
          highlight-current-row v-loading="loading"
          border element-loading-text="拼命加载中"
          style="width: 100% ">
        <el-table-column
            label="序号"
            width="80"
            align="center">
            <template slot-scope="scope">
            {{ (page - 1) * limit + scope.$index + 1 }}
            </template>
        </el-table-column>
          <el-table-column align="center"
            label="试卷名称"
            prop="paperName"
            v-model="exams.paperName">
          </el-table-column>
          <el-table-column label="分数" align="center" > 
            <template slot-scope="scope">
                {{scope.row.score}} 分
            </template>
          </el-table-column>
            <el-table-column align="center"
            label="第几次测试">
             <template slot-scope="scope">
                 第  {{scope.row.frequency}} 次
             </template>
          </el-table-column>
          <el-table-column align="center">
            <template slot="header" slot-scope="scope">
              <div align="center">操作</div>
            </template>
            <template slot-scope="scope">
              <el-button
                type="primary" size="small"
                @click="ToDetail(scope.row)" >查看详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-row style="margin-top:33px">
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
    </el-main>
</template>
<script>
  import cookie from 'js-cookie'
  import score  from '@/api/score'
  export default {
    name: "MyExam",
    data() {
      return {
        exams: [{
          paperName: '',
          score: '',
          status: ''
        }],
        page:1,
        limit:4,
        totals: 0,
        totalPage: 0,
        search: '',
        exam: {
          name: '',
          score: ''
        },
        loading: false,
         loginInfo:{},
      }
    },
    methods:{
            ToDetail(row){
                this.$router.push({path:'/MyexamDetail',query: {
                        id:row.paperId, 
                        frequency:row.frequency,
                        score:row.score
                      }})
            //    this.$router.push({
            //         path: `/MyExam/${row.id}`,
            //         });
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
                score.getList(this.page,this.limit,this.loginInfo.id).then(res=>{
                console.log(res.data);
                this.exams=res.data.data.records
                this.totals=res.data.data.total;
                // console.log(res.data);
                })
            },
          init(){
                this.getListByPage();
          },
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
        this.init()
    },
  }
 </script>
 <style scoped>
 .main{
    height: 100%;
    margin-bottom:294px
 }
 </style>