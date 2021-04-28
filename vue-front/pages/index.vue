<template>
<div>
  <!-- 幻灯片 开始 -->
   <div class="msg">
      <p class="title">教务公告：</p>
      <ul>
        <li @click="openMsg(item)"   v-for="item in notices"
      :key="item.id"
      :label="item.noticeTitle"
      :value="item.noticeTitle"><i class="iconfont icon-flag"></i>{{item.noticeTitle}}</li>
      </ul>
    </div>
     <div v-swiper:mySwiper="swiperOption" v-if="banners.length > 0">
    <div class="swiper-wrapper">
        <div class="swiper-slide" style="background: #040B1B;"  v-for="item in banners"
      :key="item.id"
      :label="item.imageUrl"
      :value="item.imageUrl">
            <a   >
                <el-image :src="item.imageUrl"   style="width:100%;height:500px;"  ></el-image> 
            </a>
        </div>
        <!-- <div class="swiper-slide" style="background: #040B1B;">
            <a target="_blank" href="/">
                <img src="~/assets/photo/banner/1525939573202.jpg" alt="首页banner">
            </a>
        </div>
         <div class="swiper-slide" style="background: #040B1B;">
            <a target="_blank" href="/">
                <img src="~/assets/photo/banner/1525939573202.jpg" alt="首页banner">
            </a>
        </div> -->
    </div>
        <!-- <swiper :options="swiperOptions" class="banner"  v-if="banners.length > 0">
        <swiper-slide v-for="value in banners" :key="value.bannerId" class="item">
          <a :href="value.url">
              <img v-lazy="value.pic" :alt="value.typeTitle">
          </a>
        </swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
      </swiper> -->
    <div class="swiper-pagination swiper-pagination-white"></div>
    <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
    <div class="swiper-button-next swiper-button-white" slot="button-next"></div>
</div>
</div>
<!-- 幻灯片 结束 -->
</template>

<script>
 import cookie from 'js-cookie'
  import notice  from '@/api/notice'
    import banner  from '@/api/banner'
export default {
  name: 'login',
  data(){
    return{
       swiperOption: {
        //配置分页
        pagination: {
          el: '.swiper-pagination'//分页的dom节点
        },
        //配置导航
        navigation: {
          nextEl: '.swiper-button-next',//下一页dom节点
          prevEl: '.swiper-button-prev'//前一页dom节点
        },
        stopOnLastSlide:false,
        circular:true,
        loop:true,
        autoplay: {
    　　delay: 3000,
    　　disableOnInteraction: false,
        loopAdditionalSlides: 50000,
　      　},
      loopAdditionalSlides: 200,
      },
        notices:[],
        banners:[]
    }
  },
  created(){
      if(cookie.get('guli_token')==undefined||cookie.get('guli_token')==""){
         this.$router.push('/login');
      }
      this.listAll()
       this.listAllBanner()
  },
  methods:{
 openMsg(item) {
       console.log(item);
      this.$alert(item.notice,{
        confirmButtonText: '确定'
      })
    },
       listAll(){
       notice.listAll().then(res=>{
         this.notices=res.data.data.notices
         console.log(this.notices);
       })
    },
        listAllBanner(){
       banner.listAll().then(res=>{
         this.banners=res.data.data.banners
         console.log(this.banners);
       })
    }
  },
 
  // middleware({ store, route, redirect, params, query, req, res }) {
  //   redirect('/login') // 默认跳转页面的路由
 
  // },
  }
</script>
<style>
.msg .title {
  font-size: 26px;
  color: #000;
  margin-top: 20px;
  margin-left: 40%;
}
.msg ul {
    margin-top: 20px;
    margin-left: 50%;
     margin-bottom: 15px;
  display: flex;
  flex-direction: column;
  width: 200px;
  overflow: hidden;
}
.msg li {
  margin-top: 15px;
  font-size: 20px;
  color: lightcoral;
  cursor: pointer;
  display: inline-block;
}
</style>