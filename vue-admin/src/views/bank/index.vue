<template>
  <div>
    <!--    搜索框-->
    <el-form :inline="true" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small" v-model="bankQuery.name" placeholder="输入类型名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="getListByPage()">搜索</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>
    <el-table
       v-loading="listLoading"
      :data="banks"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
      style="width: 100%">
       <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
       align="center"
        label="类型名称"
        prop="name"
        v-model="banks.name">
      </el-table-column>
      <el-table-column
       align="center"
        label="类型描述"
        prop="explain1"
        v-model="banks.explain1">
      </el-table-column>
      <el-table-column
       align="center"
        label="题库数量"
        prop="bankCount"
        v-model="banks.bankCount">
      </el-table-column>
      <el-table-column
       align="center"
        label="创建时间"
        prop="gmtCreate"
         v-model="banks.gmtCreate">
      </el-table-column>
      <el-table-column
       align="center"
        label="更新时间"
        prop="gmtModified"
        v-model="banks.gmtModified">
      </el-table-column>
      <el-table-column
        align="center"
        label="操作">
        <template slot-scope="scope">
           <router-link :to="'/bank/editBank/'+scope.row.id">
          <el-button
            size="mini"
            type="primary"
           >修改
          </el-button>
          </router-link>
          <el-button
            type="danger" size="small"
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
import bank from '@/api/bank'

  export default {
    name: "Bank",
    data() {
      return {
        banks:[],
        page:1,
        limit:4,
        total:0,
        listLoading:false,
        bankQuery:{name:""}
       
      }
    },
    methods: {
      findPage(page){
        this.page = page;
        this.getListByPage(page,this.limit);
      },
      handleSizeChange(limit){
        this.limit = limit;
        this.getListByPage(this.page,limit);
      },
      getListByPage(page=1){
        bank.getListByPage(this.page,this.limit,this.bankQuery).then(res=>{
          this.banks=res.data.records
          this.total=res.data.total;
          // console.log(res.data);
        })
      },
      handleDelete(id){
          //  console.log(id);
         this.$confirm('此操作将永久删除题库记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
              bank.deleteBank(id).then(response=>{
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
        this.bankQuery={}
        this.getListByPage()
      }
    },
    created() {
     this.getListByPage()          
    }
  }
</script>

<style scoped>

</style>
