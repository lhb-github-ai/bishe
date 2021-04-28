<template>
  <div>
    <!--    搜索框-->
    <el-form :inline="true" class="user-search">
      <el-form-item label="搜索：">
         <el-input size="small" v-model="teacherQuery.teacherId" placeholder="输入老师工号"></el-input>
      </el-form-item>
     <el-form-item>
         <el-input size="small" v-model="teacherQuery.teacherName" placeholder="输入老师姓名"></el-input>
      </el-form-item>
     <el-form-item>
         <el-input size="small" v-model="teacherQuery.type" placeholder="输入老师职称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="getListByPage()">搜索</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>
    <el-table
       v-loading="listLoading"
      :data="teachers"
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
        label="工号"
        prop="teacherId"
        v-model="teachers.teacherId">
      </el-table-column>
      <el-table-column 
       align="center"
        label="老师姓名"
        prop="teacherName"
        v-model="teachers.teacherName">
      </el-table-column>
      <el-table-column
       align="center" width="55"
        label="性别"
        prop="sex"
        v-model="teachers.sex">
      </el-table-column>
       <el-table-column
       align="center"
        label="职称"
        prop="type"
        v-model="teachers.type">
      </el-table-column>
      <el-table-column  width="110"
       align="center"
        label="电话号码"
        prop="tel"
        v-model="teachers.tel">
      </el-table-column>
      <el-table-column width="110"
       align="center"
        label="邮箱"
        prop="email"
        v-model="teachers.email">
      </el-table-column>
       <el-table-column
       align="center"
        label="密码"
        prop="pwd"
        v-model="teachers.pwd">
      </el-table-column>
      <el-table-column
       align="center"
        label="头像"
       >
        <template slot-scope="scope">
         <img :src="scope.row.avatar"  width="73" height="87" ></img>
           </template>
      </el-table-column>
      <el-table-column
       align="center"
        label="创建时间"
        prop="gmtCreate"
         v-model="teachers.gmtCreate">
      </el-table-column>
      <el-table-column
        align="center"
        label="操作">
        <template slot-scope="scope">
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

  export default {
    name: "Teacher",
    data() {
      return {
        teachers:[],
        page:1,
        limit:4,
        total:0,
        listLoading:false,
        teacherQuery:{teacherId:"",teacherName:"",type:""}
       
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
        teacher.getListByPage(this.page,this.limit,this.teacherQuery).then(res=>{
          console.log(res.data.records);
          this.teachers=res.data.records
          this.total=res.data.total;
          // console.log(res.data);
        })
      },
      handleDelete(id){
          //  console.log(id);
         this.$confirm('此操作将永久删除该学生信息, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
              teacher.deleteTeacher(id).then(response=>{
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
        this.teacherQuery={}
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
