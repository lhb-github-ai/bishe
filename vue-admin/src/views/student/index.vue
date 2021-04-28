<template>
  <div>
    <!--    搜索框-->
    <el-form :inline="true" class="user-search">
      <el-form-item label="搜索：">
         <el-input size="small" v-model="studentQuery.studentId" placeholder="输入学生的学号"></el-input>
      </el-form-item>
     <el-form-item>
         <el-input size="small" v-model="studentQuery.studentName" placeholder="输入学生的姓名"></el-input>
      </el-form-item>
     <el-form-item>
         <el-input size="small" v-model="studentQuery.clazz" placeholder="输入学生所在班级"></el-input>
      </el-form-item>
      <el-form-item>
         <el-input size="small" v-model="studentQuery.institute" placeholder="输入学生所属学院"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="getListByPage()">搜索</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>
    <el-table
       v-loading="listLoading"
      :data="students"
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
        label="学号"
        prop="studentId"
        v-model="students.studentId">
      </el-table-column>
      <el-table-column 
       align="center"
        label="学生姓名"
        prop="studentName"
        v-model="students.studentName">
      </el-table-column>
      <el-table-column
       align="center" width="55"
        label="性别"
        prop="sex"
        v-model="students.sex">
      </el-table-column>
      <el-table-column width="50"
       align="center"
        label="年龄"
        prop="age"
        v-model="students.age">
      </el-table-column>
       <el-table-column
       align="center"
        label="所属班级"
        prop="clazz"
        v-model="students.clazz">
      </el-table-column>
      <el-table-column
       align="center"
        label="所属学院"
        prop="institute"
        v-model="students.institute">
      </el-table-column>
      <el-table-column  width="110"
       align="center"
        label="电话号码"
        prop="tel"
        v-model="students.tel">
      </el-table-column>
      <el-table-column width="110"
       align="center"
        label="邮箱"
        prop="email"
        v-model="students.email">
      </el-table-column>
       <el-table-column
       align="center"
        label="密码"
        prop="pwd"
        v-model="students.pwd">
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
         v-model="students.gmtCreate">
      </el-table-column>
      <el-table-column
        align="center"
        label="操作">
        <template slot-scope="scope">
           <router-link :to="'/student/editStudent/'+scope.row.id">
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
import student from '@/api/student'

  export default {
    name: "Student",
    data() {
      return {
        students:[],
        page:1,
        limit:4,
        total:0,
        listLoading:false,
        studentQuery:{studentName:"",studentId:"",institute:"",clazz:""}
       
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
        student.getListByPage(this.page,this.limit,this.studentQuery).then(res=>{
          console.log(res.data.records);
          this.students=res.data.records
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
              student.deleteStudent(id).then(response=>{
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
        this.studentQuery={}
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
