<template>
  <div>
    <!--    搜索框-->
    <el-form :inline="true" class="user-search">
      <el-form-item label="搜索：">
         <el-input size="small" v-model="VoScore.studentName" placeholder="输入学生姓名"></el-input>
      </el-form-item>
     <el-form-item>
         <el-input size="small" v-model="VoScore.paperName" placeholder="输入试卷名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="getListByPage()">搜索</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>
    <el-table
       v-loading="listLoading"
      :data="scores"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
      style="width: 100%">
       <el-table-column
        label="序号"
        width="50"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column  width="115"
       align="center"
        label="试卷名称"
        prop="paperName"
        v-model="scores.paperName">
      </el-table-column>
     <el-table-column  width="115"
       align="center"
        label="学生姓名"
        prop="studentName"
        v-model="scores.studentName">
      </el-table-column>
     <el-table-column  width="115"
       align="center"
        label="分数">
        <template slot-scope="scope">
            {{scope.row.score}} 分
        </template>
      </el-table-column> 
      <el-table-column  width="115"
       align="center"
        label="第几次考试">
        <template slot-scope="scope">
          第  {{scope.row.frequency}} 次
        </template>
      </el-table-column> 
      <el-table-column
       align="center"
        label="考试结束时间"
        prop="gmtCreate"
         v-model="scores.gmtCreate">
      </el-table-column>
      <el-table-column v-if="VoScore.authorId==1"
        align="center"
        label="操作">
        <template slot-scope="scope">
           <el-button
            type="success" size="mini" style="margin-top:5px"
            @click="ToPaperDetail(scope.row)">查看试卷详情
          </el-button>
          </router-link>
           <router-link :to="'/teacher/editTeacher/'+scope.row.id">
          <el-button
            size="mini"
            type="primary" 
           >修改
          </el-button>
          </router-link>
          <el-button
            type="danger" size="mini" style="margin-top:5px"
            @click="handleDelete(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-row>
      <el-col :span="10" :offset="9">
        <el-pagination
          background
          layout="total,prev, pager, next, jumper, sizes"
          prev-text="上一页"
          next-text="下一页"
          :current-page="page"
          :page-size="limit"
          :page-sizes="[4, 8, 10, 15,20]"
          @current-change="findPage"
          @size-change="handleSizeChange"
          :total="total">
        </el-pagination>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import teacher from '@/api/teacher'
import exam from '@/api/exam'
  export default {
    name: "Teacher",
    data() {
      return {
        scores:[],
        page:1,
        limit:4,
        total:0,
        listLoading:false,
        VoScore:{paperName:"",studentName:"",authorId:""}
       
      }
    },
    methods: {
      ToPaperDetail(row){
           this.$router.push({path:"/exam/paperDetail/"+row.paperId+"/"+row.frequency+"/"+row.score})
            //  this.$router.push({path:'/exam/paperDetail',query: {
            //             // score: finalScore.toFixed(1), 
            //             // startTime: this.startTime,
            //             // endTime: this.endTime,
            //             // paperName:this.examData.paperName,
            //             // studentName:this.loginInfo.studentName,
            //             // paperId:this.paperId,
            //           }})
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
        exam.getListByPage(this.page,this.limit,this.VoScore).then(res=>{
          console.log(res.data);
          this.scores=res.data.Score
          this.total=res.data.totals;
        //   console.log(   res.data.Score.total);
        })
      },
      handleDelete(id){
          //  console.log(id);
         this.$confirm('此操作将永久删除该学生成绩, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
              exam.delete(id).then(response=>{
                this.getListByPage()
             }).catch(error=>{
              // console.log(error);
            })
            this.$message({
                type: 'success',
                message: '删除成功!'
            });
            })
      },
      resetData(){
        this.VoScore={}
        this.VoScore.authorId=localStorage.getItem("id")
        this.getListByPage()
      }
    },
    created() {
            this.VoScore.authorId=localStorage.getItem("id")
            this.getListByPage()          
    }
  }
</script>

<style scoped>

</style>
