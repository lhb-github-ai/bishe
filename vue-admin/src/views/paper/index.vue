<template>
  <div>
    <!--    搜索框-->
    <el-form :inline="true" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small" v-model="paperQuery.paperName" placeholder="输入试卷名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="getListByPage()">搜索</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>
    <el-table
       v-loading="listLoading"
      :data="papers"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
      style="width: 100%">
       <el-table-column
        label="序号"
        width="60"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        label="试卷名称"
        prop="paperName"   width="110" align="center"
        v-model="papers.paperName">
      </el-table-column>
      <el-table-column align="center" width="120px"
        label="试卷描述"
        prop="description"
        v-model="papers.description">
      </el-table-column>
      <el-table-column width="80px"
        label="试卷分数"
             align="center">
         <template slot-scope="scope" >
          {{scope.row.time}} 分
        </template>
      </el-table-column>
    <el-table-column width="80px"
        label="考试时间" align="center" >
        <template slot-scope="scope" >
          {{scope.row.time}} 分钟
        </template>
      </el-table-column>
       <el-table-column
            align="center"   width="166px"
        label="考试时间范围"
        prop="timeFrame"
        v-model="papers.timeFrame">
      </el-table-column>
       <el-table-column
           align="center" width="60px"
        label="作者"
        prop="teacherName"
         v-model="papers.teacherName">
      </el-table-column>
      <el-table-column width="94px"
           align="center"
        label="创建时间"
        prop="gmtCreate"
         v-model="papers.gmtCreate">
      </el-table-column>
      <el-table-column width="94px"
           align="center"
        label="更新时间"
        prop="gmtModified"
        v-model="papers.gmtModified">
      </el-table-column>
       <el-table-column width="80px" prop="type"
           align="center"    label="是否为正式试卷" >
            <template slot-scope="scope" >
             <el-switch
              v-model="scope.row.type"
                active-color="#13ce66"
                inactive-color="#909399"
                active-value="true"
                 inactive-value="false" @change="ChangeType($event,scope.row.id)">
          </el-switch>
            </template>
      </el-table-column>
       <el-table-column  width="78px"
            align="center"
        label="试卷状态" >
         <template slot-scope="scope" >
          {{scope.row.status==1 ? '已发布':'未发布'}}
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="操作">
        <template slot-scope="scope">
           <router-link :to="'/paper/publish/'+scope.row.id">
          <el-button
            size="small  "
            type="primary"  style="margin-left:7px;"
           >修改
          </el-button>
          </router-link>
             <el-button 
            type="info" size="small  "  style="margin-top:5px;margin-left:7px;"
            @click="openPaper(scope.row.id)">预览试题 
          </el-button>
          <el-button v-if="scope.row.status==0"
            type="success" size="small  "  style="margin-top:5px;"
            @click="publish(scope.row.id)">发布
          </el-button>
          <el-button v-if="scope.row.status==1"
            type="success" size="small  "  style="margin-top:5px;"
            @click="QXpublish(scope.row.id)">取消发布
          </el-button>
            <el-button 
            type="danger" size="small  "  style="margin-top:5px;margin-left:7px;"
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
  <el-dialog title="预览试题详情" :visible.sync="outerVisible"   width="30%" style="margin-top:75px">
    <el-dialog title="单选题试题详情" :visible.sync="dialogDXTable" width=82% top=100px append-to-body>
     <el-table :data="DXData">
      <el-table-column property="questionName" label="题目" width="250"></el-table-column>
      <el-table-column property="optionA" label="A选项" width="150"></el-table-column>
      <el-table-column property="optionB" label="B选项" width="150"></el-table-column>
      <el-table-column property="optionC" label="C选项" width="150"></el-table-column>
      <el-table-column property="optionD" label="D选项" width="150"></el-table-column>
      <el-table-column property="answer" label="答案" width="70"></el-table-column>
      <el-table-column property="analysis" label="解析" width="175"></el-table-column>
    </el-table>
  </el-dialog>
   <el-dialog title="判断题试题详情" :visible.sync="dialogPDTable"  width=43% append-to-body>
     <el-table :data="PDData">
      <el-table-column property="questionName" label="题目" width="280"></el-table-column>
      <el-table-column property="answer" label="答案" width="100"></el-table-column>
      <el-table-column property="analysis" label="解析" width="180"></el-table-column>
    </el-table>
  </el-dialog>
    <div  class="dialog-footer" >
          <el-button style="margin-left:17px;"  type="primary" @click="dialogDXTable = true" >点击预览单选题题目</el-button>
          <el-button style="margin-left:17px;"  type="primary" @click="dialogPDTable = true" >点击预览多选题题目</el-button>
    </div>
  </el-dialog>
  </div>
</template>

<script>
import paper from '@/api/paper'
import paperQuestion from '@/api/paperQuestion'
  export default {
    name: "Bank",
    data() {
      return {
        DXData: [{
                }],
        PDData: [{
                }],
        dialogDXTable :false,
        dialogPDTable:false,
        outerVisible: false,
        // innerVisible: false,
        papers:[],
        page:1,
        limit:4,
        total:0,
        listLoading:false,
        paperQuery:{paperName:""},
        paper:{},
        teacherId :'',
      }
    },
    methods: {
      ChangeType: function($event,id){
          this.paper.paperId=id
          this.paper.type=$event
          		console.log(this.paper);
                   paper.savePaper(this.paper).then(res=>{
                      this.getListByPage()         
              })
			},
      openPaper(id){
           this.outerVisible=true;
         //  this.paperId=id
            this.listPDQuestion(id);
           this.listDXQuestion(id);
      },
       listPDQuestion(id){
             paperQuestion.listQuestionDX(id,0).then(res=>{
              //  console.log(res);
                this.DXData=res.data.paperQuestionsDX

             })
       },
        listDXQuestion(id){
             paperQuestion.listQuestionPD(id,1).then(res=>{
             //   console.log(res);
                this.PDData=res.data.paperQuestionsPD
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
          console.log(this.paperQuery);
        paper.getListByPage(this.page,this.limit,this.teacherId,this.paperQuery).then(res=>{
            console.log(res.data);
          this.papers=res.data.records
          this.total=res.data.total;
          // console.log(res.data);
        })
      },
      handleDelete(id){
          //  console.log(id);
         this.$confirm('此操作将永久删除该试卷, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
              paper.delete(id).then(response=>{
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
      publish(id){
         this.paper.paperId=id
         this.paper.status=1
                 paper.savePaper(this.paper).then(res=>{
                    this.$message({
                      type: 'success',
                      message: '发布成功!'
                  });
                this.getListByPage()         
              })
      },
       QXpublish(id){
         this.paper.paperId=id
         this.paper.status=0
                 paper.savePaper(this.paper).then(res=>{
                    this.$message({
                      type: 'success',
                      message: '取消发布成功!'
                  });
                this.getListByPage()         
              })
      },
      resetData(){
        this.paperQuery={}
        this.getListByPage()
      }
    },
    created() {
      console.log(localStorage.getItem("id")+"***");
       this.teacherId=localStorage.getItem("id")
          this.getListByPage()          
    }
  }
</script>

<style scoped>

</style>
