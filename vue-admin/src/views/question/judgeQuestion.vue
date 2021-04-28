<template>
  <div>
    <!--    搜索框-->
    <el-form :inline="true" class="user-search">
      <el-form-item label="搜索:">
        <el-input size="small" v-model="choiceQuestionQuery.question" placeholder="输入题目内容"></el-input>
      </el-form-item>
        <el-select v-model="choiceQuestionQuery.bankName" placeholder="请选择试卷类型" class="bankId-input">
    <el-option
      v-for="item in banks"
      :key="item.id"
      :label="item.name"
      :value="item.name">
    </el-option>
    </el-select>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="getListByPage">搜索</el-button>
         <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="listLoading"
      :data="wors"
      element-loading-text="数据加载中"
      style="width: 100% ">
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
       <el-table-column
        label="题库名"
        prop="bankName"
        v-model="wors.bankName">
      </el-table-column>
      <el-table-column
        label="题目内容"
        prop="question"
        v-model="wors.question">
      </el-table-column>
      <el-table-column
        label="正确答案"
        prop="answer"
        v-model="wors.answer">
      </el-table-column>
      <el-table-column
        label="答案解析"
        prop="analysis"
        v-model="wors.analysis">
      </el-table-column> 
       <el-table-column
        label="难度级别"
        prop="level"
        v-model="wors.level">
      </el-table-column> 
      <el-table-column 
         label="操作"
         width="170px"align="center">
        <template slot-scope="scope">
          <router-link :to="'/question/editJudgeQuestion/'+scope.row.id">
          <el-button
            size="small"
            type="primary"
          >修改
          </el-button>
           </router-link>
          <el-button
            type="danger" size="small"
            @click="handleDelete(scope.row.id)" >删除
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
          :page-sizes="[5, 10, 15, 20]"
          @current-change="findPage"
          @size-change="handleSizeChange"
          :total="total">
        </el-pagination>
      </el-col>
    </el-row>
  </div>
</template>
<script>    
import judgeQuestion from '@/api/JudgeQuestion'       
import bank  from '@/api/bank'         
  export default {
    name: "Wor",
    data() {
      return {
        wors: [],
        page:1,
        limit:5,
        total:0,
        listLoading:false,
        choiceQuestionQuery:{},
        banks:[]
      }
    },
    methods: {
       handleDelete(id){
          //  console.log(id);
         this.$confirm('此操作将永久删除题库记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
              judgeQuestion.deleteQuestion(id).then(response=>{
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
        this.choiceQuestionQuery={}
        this.getListByPage()
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
        judgeQuestion.getListByPage(this.page,this.limit,this.choiceQuestionQuery).then(res=>{
          // console.log(res.data);
          this.wors=res.data.question
           this.total=res.data.totals;
        })
      },
     findAllBank(){
            bank.findAllList().then(res=>{
              // console.log(res.data);
              this.banks=res.data.list
            })
       }
    },
    created() {
     this.getListByPage()
     this.findAllBank()
    }
  }
</script>

<style scoped>

</style>
