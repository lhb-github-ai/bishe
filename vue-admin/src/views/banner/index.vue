<template>
  <div>
    <!--    搜索框-->
    <el-form :inline="true" class="user-search">
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="getListByPage()">搜索</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>
    <el-table
       v-loading="listLoading"
      :data="notices"
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
        label="轮播图"
       >
        <template slot-scope="scope">
         <img :src="scope.row.imageUrl"  width="200" height="120" ></img>
           </template>
      </el-table-column>
      <el-table-column
       align="center"
        label="管理员姓名"
        prop="adminName"
        v-model="notices.adminName">
      </el-table-column>
      <el-table-column width="80px" prop="type"
           align="center"    label="是否发布" >
            <template slot-scope="scope" >
             <el-switch
              v-model="scope.row.status"
                active-color="#13ce66"
                inactive-color="#909399"
                active-value="true"
                 inactive-value="false" @change="ChangeStatus($event,scope.row)">
          </el-switch>
            </template>
      </el-table-column>
      <el-table-column
       align="center"
        label="创建时间"
        prop="gmtCreate"
         v-model="notices.gmtCreate">
      </el-table-column>
      <el-table-column
       align="center"
        label="更新时间"
        prop="gmtModified"
        v-model="notices.gmtModified">
      </el-table-column>
      <el-table-column
        align="center"
        label="操作">
        <template slot-scope="scope">
           <router-link :to="'/banner/editBanner/'+scope.row.id">
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

import banner from '@/api/banner'

  export default {
    name: "Bank",
    data() {
      return {
        notices:[],
        page:1,
        limit:4,
        total:0,
        listLoading:false,
        banner:{}
       
      }
    },
    methods: {
      ChangeStatus($event,row){
        		console.log(row);
           this.banner.status=$event
           this.banner.id=row.id
                   banner.updateNotice(this.banner).then(res=>{
                      this.getListByPage()         
              })
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
        banner.listByPage(this.page,this.limit).then(res=>{
          console.log(res.data);
          this.notices=res.data.records
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
              banner.remove(id).then(response=>{
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
