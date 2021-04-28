<template>
  <section class="index">
    <div class="hello">
      <i class="iconfont icon-xihuan"></i><span>{{hello}}  很高兴遇见你,{{name}}{{roles}}</span>
    </div>
    <div class="msg">
      <p class="title">教务公告：</p>
      <ul>
        <li @click="openMsg(item)"   v-for="item in notices"
      :key="item.id"
      :label="item.noticeTitle"
      :value="item.noticeTitle"><i class="iconfont icon-flag"></i>{{item.noticeTitle}}</li>
      </ul>
    </div>
  </section>
</template>

<script>
import { mapGetters } from 'vuex'
 import notice  from '@/api/notice'
export default {

  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  },
  data(){
    return{
       hello:"",
       notices:[],
    }
  },
  methods:{
     openMsg(item) {
       console.log(item);
      this.$alert(item.notice,{
        confirmButtonText: '确定'
      })
    },
    tishiyu(){
      const date = new Date();
      var hour = date.getHours()
        if(hour < 6){this.hello="凌晨好!"}
        else if (hour < 9){this.hello="早上好!"}
        else if (hour < 12){this.hello="上午好!"}
        else if (hour < 14){this.hello="中午好!"}
        else if (hour < 17){this.hello="下午好!"}
        else if (hour < 19){this.hello="傍晚好!"}
        else if (hour < 22){this.hello="晚上好!"}
        else {this.hello="深夜了!"}
    },
    listAll(){
       notice.listAll().then(res=>{
         this.notices=res.data.notices
        //  console.log(this.notices);
       })
    }
  },
  created(){
    this.tishiyu()
    this.listAll();
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.index {
  margin-left: 70px;
  .hello {
    font-size: 20px;
    color: #726f70;
    .icon-xihuan {
      font-size: 30px;
      color: #dd6572;
    }
  }
  .msg {
    .title {
      font-size: 16px;
      color: #000;
      margin-top: 20px;
      margin-left: 10px;
    }
    ul {
      display: flex;
      flex-direction: column;
      width: 200px;
      overflow: hidden;
    }
    li {
      margin-top: 10px;
      font-size: 14px;
      color: lightcoral;
      cursor: pointer;
      display: inline-block;
    }
  }
}
</style>
