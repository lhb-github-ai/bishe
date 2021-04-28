<template>
    <el-main style="padding-top:10px">
      <div class="main">
        <div class="bread" style="padding-bottom:10px">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }" style=" font-size:17px;">首页</el-breadcrumb-item>
            <el-breadcrumb-item style=" font-size:17px;">考试中心</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <el-table 
          :data="papers"
          highlight-current-row v-loading="loading"
          border element-loading-text="拼命加载中"
          style="width: 100%">
      <el-table-column
        label="序号"
        width="80"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
          <el-table-column  align="center" width="380px" 
            label="试卷名称"
            prop="paperName"
            v-model="papers.paperName">
          </el-table-column>
           <el-table-column width="130px" 
        label="试卷分数"
             align="center">
         <template slot-scope="scope" >
          {{scope.row.totalScore}} 分
        </template>
      </el-table-column>
           <el-table-column width="130px"
        label="考试时间" align="center" >
        <template slot-scope="scope" >
          {{scope.row.time}} 分钟
        </template>
      </el-table-column>
           <el-table-column width="180px"
            label="考试时间范围" align="center" 
            prop="timeFrame"
            v-model="papers.timeFrame">
          </el-table-column>
          <el-table-column align="center"  width="340px"
            label="试卷描述"
            prop="description"
            v-model="papers.description">
          </el-table-column>
           <el-table-column 
            align="center" width="180" >
            	<template slot="header" slot-scope="scope">
                 <div align="center">试卷状态</div>
              </template>
              <template slot-scope="scope" >
                 <span  v-if="scope.row.tip==='考试时间已结束'" style="color:#F56C6C;font-size:17px;font-weight:600">{{scope.row.tip}}</span>
                 <span  v-if="scope.row.tip==='考试时间还未开始'" style="color:#909399;font-size:17px;font-weight:600">{{scope.row.tip}}</span>
                 <span  v-if="scope.row.tip==='考试时间还未结束'" style="color:#67C23A;font-size:17px;font-weight:600">{{scope.row.tip}}</span>
                  <span  v-if="scope.row.tip==='考试时间已结束(已考试)'" style="color:#F56C6C;font-size:17px;font-weight:600">{{scope.row.tip}}</span>
                 <span  v-if="scope.row.tip==='考试时间还未开始(已考试)'" style="color:#909399;font-size:17px;font-weight:600">{{scope.row.tip}}</span>
                 <span  v-if="scope.row.tip==='考试时间还未结束(已考试)'" style="color:#67C23A;font-size:17px;font-weight:600">{{scope.row.tip}}</span>
            </template>
          </el-table-column>
          <el-table-column
            align="left" width="200px">
            <template slot="header" slot-scope="scope">
              <div align="center">操作</div>
            </template>
            <template slot-scope="scope"  >
                  <el-button  :disabled="scope.row.isAvailable" 
                    size="mini" style="margin-left:50px"
                    type="primary"
                    @click="ToAnswer(scope.row)">前往考试
                  </el-button>
            </template>
          </el-table-column>
        </el-table>
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
    </el-main>

</template>

<script>
  // import Topnav from "../public/Topnav";
  // import Foot from "../public/Foot";
  import paper  from '@/api/paper'
  import cookie from 'js-cookie'
  export default {
    name: "Answer_index",
   // components: {Foot, Topnav},
    data() {
      return {
        papers: [{}],
        loading: false,
        page:1,
        limit:4,
        totals: 0,
        IsAvailable:false,
        loginInfo:{},
        id:"",
      }
    },
    methods:{
     
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
       this.id=this.loginInfo.id
        console.log(   this.id+"***********");
        paper.getPaperList(this.page,this.limit,   this.id,true).then(res=>{
           console.log(res.data);
          this.papers=res.data.data.records
          this.totals=res.data.data.total;
          // console.log(res.data);
        })
      },
      ToAnswer(row){
        // console.log(row);
        this.$router.push({
          path: `/paper/${row.id}`,
        });
      },
      init(){
              this.getListByPage();
      },
    },
    created() {
        if(cookie.get('guli_token')==undefined||cookie.get('guli_token')==""){
         this.$router.push('/login');
      }
    var jsonStr = cookie.get("guli_ucenter");
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr)
        console.log(this.loginInfo);
      }
     this.init();
    }
  }
</script>

<style scoped>
  .main {
    background-color: white;
    height: 100%;
    margin-bottom:301px
  }

  .footer {
    height: 100%;
    background-color: #222c3a;
  }
  .bread{
    padding-top: 12px;
    padding-left: 8px;
  }
</style>
