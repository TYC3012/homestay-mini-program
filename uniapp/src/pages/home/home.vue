<template>
  <view>
    <!-- 顶部自定义导航 -->
    <tn-sticky>
      <tn-nav-bar :isBack="false" :bottomShadow="false" :height="60" class="tn-flex-direction-column tn-flex">
        <view class="tn-search-fixed tn-bg-white tn-text-left" style="width: 100%">
          <tn-picker mode="region" @confirm="onConfirm" v-model="show" :params="params"
                     :areaCode='["44", "4401", "440106"]'></tn-picker>
          <text class="tn-text-sm tn-flex-1 tn-margin-left-lg" @click="show=true">{{ city }}</text>
          <text class="tn-icon-down tn-color-gray tn-text-xs tn-margin-left-xs"></text>
          <text class="tn-text-xl tn-text-bold" style="color: #109797;margin-left: 160rpx">安心民宿</text>
        </view>
      </tn-nav-bar>
    </tn-sticky>

    <!--  轮播图  -->
    <view class="tn-margin-top-lg" :style="{marginTop: vuex_custom_bar_height +'px'}">
      <tn-swiper :list="swiperList" mode="none" :height="350"></tn-swiper>
    </view>
    <!--   功能介绍   -->
    <view class="tn-padding tn-flex">
      <!--   top的功能介绍     -->
      <view v-for="(item,index) in topListIcon" :key="index"
            class="tn-flex-direction-column tn-col-10 tn-margin-left-sm tn-text-center">
        <view>
          <image :src="item.icon" style="width: 70rpx;height: 70rpx"/>
        </view>
        <view class="tn-margin-top-xs">
          <text class="tn-text-xs">{{ item.title }}</text>
        </view>
      </view>
    </view>

    <view style="margin-top: 10rpx">
      <text class="tn-padding tn-text-bold tn-text-xl">特惠民宿</text>
      <tn-scroll-list :indicator="false" @right="onChangeScroll">
        <view class="tn-padding tn-flex" style="margin-left: -30rpx;height: 300rpx;">
          <view style="height: 470rpx;width: 200rpx;border-radius: 15rpx;"
                class="tn-flex tn-flex-direction-column tn-margin-left-sm" v-for="(item,index) in hotelList" :key="index"
                @click="tn('/homePages/roomList?id='+item.id)">
            <image :src="imageUrl+item.homeFile " style="width: 150rpx;height: 100rpx;"/>
            <view class="tn-padding-left-xs tn-padding-top-sm tn-flex tn-flex-row-between">
              <text class="tn-text-lg">{{ item.homeName }}</text>
            </view>
            <view class="tn-margin-top-xs">
              <tn-tag size="sm" backgroundColor="shadow tn-bg-gray--light" fontColor="tn-color-gray--dark"
                      :fontSize="20">独立卫生间
              </tn-tag>
            </view>
          </view>
        </view>
      </tn-scroll-list>


      <view class="tn-padding tn-no-padding-bottom" style="position: relative">
        <text class="tn-text-xl tn-text-bold">为你推荐</text>
      </view>
    </view>
    <!-- 房源信息   -->
    <view>
      <room-list :list="roomList"></room-list>
    </view>
  </view>
</template>

<script>
import HomeList from '@/components/home/list.vue'
import RoomList from '@/components/room/list.vue'
import {getAddress,calculatedDistance} from '@/utils/tool.js'
import {getHouseList, getRecommendList} from "../../api/modules/user";
export default {
  name: 'Index',
  components: {
    HomeList,RoomList
  },
  data() {
    return {
      params: {
        city: true,
        province: true,
        area: false
      },
      list: [],
      cardCur: 0,
      current: 0,
      city: "",
      tabsList: [
        {tabName: "合租"},
        {tabName: "整租"},
        {tabName: "月租"},
      ],
      show: false,
      isAndroid: true,
      topListIcon: [
        {title: '可以做饭', icon: 'https://blog-1253935418.cos.ap-guangzhou.myqcloud.com/cloud/home/hz.png'},
        {title: '温馨家庭', icon: 'https://blog-1253935418.cos.ap-guangzhou.myqcloud.com/cloud/home/zz.png'},
        {title: '月租', icon: 'https://blog-1253935418.cos.ap-guangzhou.myqcloud.com/cloud/home/yz.png'},
        {title: '地图找房', icon: 'https://blog-1253935418.cos.ap-guangzhou.myqcloud.com/cloud/home/zf.png'},
        {title: '房屋委托', icon: 'https://blog-1253935418.cos.ap-guangzhou.myqcloud.com/cloud/home/wt.png'},
      ],
      hotelList: [],
      swiperList: [{
        id: 1,
        type: 'image',
        title: '',
        name: '',
        text: '',
        image: 'https://img1.baidu.com/it/u=259317823,3127910095&fm=253&fmt=auto&app=138&f=JPG?w=1452&h=500',
      }],
      cardCur2: 0,
      filterData: [
        [{text: '智能排序', value: ''}, {text: '直线距离 近 → 远', value: 1}, {
          text: '低价优先',
          value: 2
        }, {text: '高价优先', value: 3}],
        [{text: '房屋类型', value: ''}, {text: '一室一厅', value: 1}, {text: '两室一厅', value: 2}, {
          text: '两室一厅',
          value: 3
        }, {text: '四室一厅', value: 4}],
        [{text: '租房距离', value: ''}, {text: '1公里以内', value: 1}, {
          text: '2公里以内',
          value: 2
        }, {text: '3公里以内', value: 3}, {text: '3公里以外', value: 4}]
      ],
      defaultIndex: [0, 0, 0],
      canIUseGetUserProfile: false,
      roomList:[],
      imageUrl:this.websiteUrl,
      userInfo:uni.getStorageSync('userInfo'),
    }
  },
  created() {
    const systemInfo = uni.getSystemInfoSync()
    if (systemInfo.system.toLocaleLowerCase().includes('ios')) {
      this.isAndroid = false
    } else {
      this.isAndroid = true
    }
    if(typeof this.userInfo =="string"){
      this.userInfo=JSON.parse(this.userInfo);
    }
  },
  async mounted() {
    this.initList();
    this.getHotelList();
    /*getAddress().then(res => {
      console.log(res)
      this.city = res.result.address_component.city;
      this.initList(res.result.address_component.city, res.result.address_component.province);
    })*/
  },
  methods: {
    async getHotelList(){
      this.$api.getHouseList({city:'22'}).then(async res => {
        //console.log(res)
        if(res.code === 200){
          this.hotelList=res.rows;
        }
      })
    },
    search(val){
      this.tn('/pages/search')
    },
    // 切换城市
    onConfirm(e) {
      this.city = e.city.label
      this.initList(e.city.label, e.province.label)
    },
    async initList(city, province){
      console.log("当前登陆者信息：：",this.userInfo)
      this.userInfo.userId=this.userInfo.id;
      this.$api.getRecommendList({id:this.userInfo.id,userId:this.userInfo.id}).then(async res => {
        console.log(res)
        if(res.code === 200){
          this.roomList=res.rows;
        }
      })
    },
    tabChange(index) {
      this.current = index
    },
    onChangeScroll() {
    },
    // cardSwiper
    cardSwiper(e) {
      this.cardCur = e.detail.current
    },
    // resume
    resume(e) {
      this.cardCur2 = e.detail.current
    },
    // 跳转
    tn(e) {
      uni.navigateTo({
        url: e,
      });
    },
  }
}
</script>

<style lang="scss" scoped>
.tn-search-fixed {
  position: fixed;
  top: 50rpx;
  transition: all 0.25s ease-out;
  z-index: 1;
}

.template-index {
  max-height: 100vh;
}

.tn-tabbar-height {
  min-height: 100rpx;
  height: calc(120rpx + env(safe-area-inset-bottom) / 2);
}


/* 轮播视觉差 start */
.card-swiper {
  height: 540rpx !important;
}

.card-swiper swiper-item {
  width: 750rpx !important;
  left: 0rpx;
  box-sizing: border-box;
  // padding: 0rpx 30rpx 90rpx 30rpx;
  overflow: initial;
}

.card-swiper swiper-item .swiper-item {
  width: 100%;
  display: block;
  height: 100%;
  transform: scale(1);
  transition: all 0.2s ease-in 0s;
  overflow: hidden;
}

.card-swiper swiper-item.cur .swiper-item {
  transform: none;
  transition: all 0.2s ease-in 0s;
}

.card-swiper swiper-item .swiper-item-text {
  margin-top: -260rpx;
  text-align: center;
  width: 100%;
  display: block;
  height: 50%;
  border-radius: 10rpx;
  transform: translate(100rpx, -60rpx) scale(0.9, 0.9);
  transition: all 0.6s ease 0s;
  overflow: hidden;
}

.card-swiper swiper-item.cur .swiper-item-text {
  margin-top: -320rpx;
  width: 100%;
  transform: translate(0rpx, 0rpx) scale(0.9, 0.9);
  transition: all 0.6s ease 0s;
}

.image-banner {
  display: flex;
  align-items: center;
  justify-content: center;
}

.image-banner image {
  width: 100%;
  height: 100%;
}

/* 轮播指示点 start*/
.indication {
  z-index: 9999;
  width: 100%;
  height: 36rpx;
  position: absolute;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}

.spot {
  background-color: #FFFFFF;
  opacity: 0.6;
  width: 10rpx;
  height: 10rpx;
  border-radius: 20rpx;
  top: -60rpx;
  margin: 0 8rpx !important;
  position: relative;
}

.spot.active {
  opacity: 1;
  width: 30rpx;
  background-color: #FFFFFF;
}

/* 简历推荐 start */
.card-swiper2 {
  height: 420rpx !important;
  overflow: hidden;
}

.card-swiper2 swiper-item {
  width: 680rpx !important;
  left: 30rpx;
  box-sizing: border-box;
  overflow: initial;
  padding: 0 0rpx 40rpx 0;
}

.card-swiper2 swiper-item .swiper-item1 {
  width: 100%;
  display: block;
  height: 100%;
  transform: scale(0.97);
  transition: all 0.2s ease-in 0s;
  background-color: #E7FAFE;
}

.card-swiper2 swiper-item.cur .swiper-item1 {
  transform: none;
  transition: all 0.2s ease-in 0s;
}

.card-swiper2 swiper-item .swiper-item2 {
  margin-top: -120rpx;
  width: 100%;
  display: block;
  height: 50%;
  border-radius: 50%;
  transform: translate(480rpx, -150rpx) scale(0.9, 0.9);
  transition: all 0.3s ease 0s;
}

.card-swiper2 swiper-item.cur .swiper-item2 {
  margin-top: -180rpx;
  width: 100%;
  transform: translate(510rpx, -150rpx) scale(0.9, 0.9);
  transition: all 0.3s ease 0s;
}

.card-swiper2 swiper-item .swiper-item-text {
  margin-top: -210rpx;
  width: 100%;
  display: block;
  height: 100%;
  border-radius: 10rpx;
  transform: translate(30rpx, -80rpx) scale(0.8, 0.8);
  transition: all 0.6s ease 0s;
  overflow: hidden;
}

.card-swiper2 swiper-item.cur .swiper-item-text {
  margin-top: -270rpx;
  width: 100%;
  transform: translate(20rpx, 0rpx) scale(0.9, 0.9);
  transition: all 0.6s ease 0s;
}


/* 底部tabbar假阴影 start*/
.bg-tabbar-shadow {
  background-image: repeating-linear-gradient(to top, rgba(0, 0, 0, 0.1) 10rpx, #FFFFFF, #FFFFFF);
  position: fixed;
  bottom: 0;
  height: 450rpx;
  width: 100vw;
  z-index: -1;
}

/* 图标容器12 start */
.tn-three {
  position: absolute;
  top: 50%;
  right: 50%;
  bottom: 50%;
  left: 50%;
  transform: translate(-38rpx, -20rpx) rotateX(20deg) rotateY(10deg) rotateZ(-20deg);
  text-shadow: -1rpx 2rpx 0 #f0f0f0, -2rpx 4rpx 0 #f0f0f0, -10rpx 20rpx 30rpx rgba(0, 0, 0, 0.2);
}

.icon12 {
  &__item {
    width: 30%;
    background-color: #FFFFFF;
    border-radius: 10rpx;
    padding: 30rpx;
    margin: 20rpx 10rpx;
    transform: scale(1);
    transition: transform 0.3s linear;
    transform-origin: center center;

    &--icon {
      width: 100rpx;
      height: 100rpx;
      font-size: 60rpx;
      border-radius: 50%;
      margin-bottom: 18rpx;
      position: relative;
      z-index: 1;

      &::after {
        content: " ";
        position: absolute;
        z-index: -1;
        width: 100%;
        height: 100%;
        left: 0;
        bottom: 0;
        border-radius: inherit;
        opacity: 1;
        transform: scale(1, 1);
        background-size: 100% 100%;
        background-image: url(https://tnuiimage.tnkjapp.com/cool_bg_image/icon_bg6.png);


      }
    }
  }
}

/* 自定义导航栏内容 start */
.custom-nav {
  height: 100%;

  &__back {
    margin: auto 5rpx;
    font-size: 40rpx;
    margin-right: 10rpx;
    margin-left: 30rpx;
    flex-basis: 5%;
  }

  &__search {
    flex-basis: 60%;
    width: 100%;
    height: 100%;

    &__box {
      width: 100%;
      height: 70%;
      padding: 10rpx 0;
      margin: 0 30rpx;
      border-radius: 60rpx 60rpx 0 60rpx;
      font-size: 24rpx;
    }

    &__icon {
      padding-right: 10rpx;
      margin-left: 20rpx;
      font-size: 30rpx;
    }

    &__text {
      // color: #AAAAAA;
    }
  }
}

.logo-image {
  width: 65rpx;
  height: 65rpx;
  position: relative;
}

.logo-pic {
  background-size: cover;
  background-repeat: no-repeat;
  // background-attachment:fixed;
  background-position: top;
  border-radius: 50%;
}

/* 自定义导航栏内容 end */


/* 热门图片 start*/
.image-tuniao1 {
  padding: 164rpx 0rpx;
  font-size: 40rpx;
  font-weight: 300;
  position: relative;
}

.image-tuniao2 {
  padding: 75rpx 0rpx;
  font-size: 40rpx;
  font-weight: 300;
  position: relative;
}

.image-tuniao3 {
  padding: 90rpx 0rpx;
  font-size: 40rpx;
  font-weight: 300;
  position: relative;
}

.image-pic {
  background-size: cover;
  background-repeat: no-repeat;
  // background-attachment:fixed;
  background-position: top;
  border-radius: 10rpx;
}

/* 胶囊banner*/
.image-capsule {
  padding: 100rpx 0rpx;
  font-size: 40rpx;
  font-weight: 300;
  position: relative;
}

.image-piccapsule {
  background-size: cover;
  background-repeat: no-repeat;
  // background-attachment:fixed;
  background-position: top;
  border-radius: 20rpx 20rpx 0 0;
}

/* 标题 start */
.nav_title {
  -webkit-background-clip: text;
  color: transparent;

  &--wrap {
    position: relative;
    display: flex;
    height: 120rpx;
    font-size: 46rpx;
    align-items: center;
    justify-content: center;
    font-weight: bold;
    background-image: url(https://tnuiimage.tnkjapp.com/title_bg/title00.png);
    background-size: cover;
  }
}

/* 标题 end */

/* 业务展示 start */
.tn-info {

  &__container {
    margin-top: 10rpx;
    margin-bottom: 50rpx;
  }

  &__item {
    width: 47.7%;
    margin: 15rpx 0rpx 30rpx 0rpx;
    padding: 40rpx 30rpx;
    border-radius: 10rpx;


    position: relative;
    z-index: 1;

    &::after {
      content: " ";
      position: absolute;
      z-index: -1;
      width: 100%;
      height: 100%;
      left: 0;
      bottom: 0;
      border-radius: inherit;
      opacity: 1;
      transform: scale(1, 1);
      background-size: 100% 100%;
      background-image: url(https://tnuiimage.tnkjapp.com/cool_bg_image/2.png);
    }

    &__left {

      &--icon {
        width: 80rpx;
        height: 80rpx;
        border-radius: 50%;
        font-size: 40rpx;
        margin-right: 20rpx;
        position: relative;
        z-index: 1;

        &::after {
          content: " ";
          position: absolute;
          z-index: -1;
          width: 100%;
          height: 100%;
          left: 0;
          bottom: 0;
          border-radius: inherit;
          opacity: 1;
          transform: scale(1, 1);
          background-size: 100% 100%;
          background-image: url(https://tnuiimage.tnkjapp.com/cool_bg_image/icon_bg5.png);
        }
      }

      &__content {
        font-size: 25rpx;

        &--data {
          color: rgba(255, 255, 255, 0.5);
          margin-top: 5rpx;
          // font-weight: bold;
        }
      }
    }

    &__right {
      &--icon {
        position: absolute;
        right: 0rpx;
        top: 50rpx;
        font-size: 100rpx;
        width: 108rpx;
        height: 108rpx;
        text-align: center;
        line-height: 60rpx;
        opacity: 0.15;
      }
    }

    &__bottom {
      box-shadow: 0rpx 0rpx 30rpx 0rpx rgba(0, 0, 0, 0.12);
      border-radius: 0 0 10rpx 10rpx;
      position: absolute;
      width: 85%;
      line-height: 15rpx;
      left: 50%;
      bottom: -15rpx;
      transform: translateX(-50%);
      z-index: -1;
      text-align: center;
    }
  }
}

/* 业务展示 end */

/* 底部tabbar start*/
.footerfixed {
  position: fixed;
  width: 100%;
  bottom: 0;
  z-index: 999;
  background-color: #FFFFFF;
  box-shadow: 0rpx 0rpx 30rpx 0rpx rgba(0, 0, 0, 0.07);
}

.tabbar {
  display: flex;
  align-items: center;
  min-height: 110rpx;
  justify-content: space-between;
  padding: 0;
  height: calc(110rpx + env(safe-area-inset-bottom) / 2);
  padding-bottom: calc(env(safe-area-inset-bottom) / 2);
}

.tabbar .action {
  font-size: 22rpx;
  position: relative;
  flex: 1;
  text-align: center;
  padding: 0;
  display: block;
  height: auto;
  line-height: 1;
  margin: 0;
  overflow: initial;
}

.bar-center {
  animation: suspension 3s ease-in-out infinite;
}

@keyframes suspension {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-0.8rem);
  }
}

.tabbar .action .bar-icon {
  width: 100rpx;
  position: relative;
  display: block;
  height: auto;
  margin: 0 auto 10rpx;
  text-align: center;
  font-size: 42rpx;
  // line-height: 50rpx;
}

.tabbar .action .bar-icon image {
  width: 50rpx;
  height: 50rpx;
  display: inline-block;
}

.tabbar .action .bar-circle {
  position: relative;
  display: block;
  margin: 0rpx auto 0rpx;
  text-align: center;
  font-size: 52rpx;
  line-height: 90rpx;
  // background-color: #01BEFF;
  width: 100rpx !important;
  height: 100rpx !important;
  overflow: hidden;
  // border-radius: 50%;
  // box-shadow: 0px 10px 30px rgba(70,23,129, 0.12),
  //   0px -8px 40px rgba(255, 255, 255, 1),
  //   inset 0px -10px 10px rgba(70,23,129, 0.05),
  //   inset 0px 10px 20px rgba(255, 255, 255, 1);
  // box-shadow: 0rpx 0rpx 20rpx 0rpx rgba(1, 190, 255, 0.5);
}

.tabbar .action .bar-circle image {
  width: 100rpx;
  height: 100rpx;
  display: inline-block;
  margin: 0rpx auto 0rpx;
}

/* 流星+悬浮 */
.nav-index-button {
  animation: suspension 3s ease-in-out infinite;
  z-index: 999999;


  &__content {
    position: absolute;
    width: 100rpx;
    height: 100rpx;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);

    &--icon {
      width: 100rpx;
      height: 100rpx;
      font-size: 60rpx;
      border-radius: 50%;
      margin-bottom: 18rpx;
      position: relative;
      z-index: 1;
      transform: scale(0.85);

      &::after {
        content: " ";
        position: absolute;
        z-index: -1;
        width: 100%;
        height: 100%;
        left: 0;
        bottom: 0;
        border-radius: inherit;
        opacity: 1;
        transform: scale(1, 1);
        background-size: 100% 100%;
        // background-image: url(https://tnuiimage.tnkjapp.com/cool_bg_image/icon_bg6.png);
      }
    }
  }

  &__meteor {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 100rpx;
    height: 100rpx;
    transform-style: preserve-3d;
    transform: translate(-50%, -50%) rotateY(75deg) rotateZ(10deg);

    &__wrapper {
      width: 100rpx;
      height: 100rpx;
      transform-style: preserve-3d;
      animation: spin 20s linear infinite;
    }

    &__item {
      position: absolute;
      width: 100rpx;
      height: 100rpx;
      border-radius: 1000rpx;
      left: 0;
      top: 0;

      &--pic {
        display: block;
        width: 100%;
        height: 100%;
        background: url(https://tnuiimage.tnkjapp.com/cool_bg_image/arc1.png) no-repeat center center;
        background-size: 100% 100%;
        animation: arc 4s linear infinite;
      }
    }
  }
}


@keyframes suspension {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-0.6rem);
  }
}

@keyframes spin {
  0% {
    transform: rotateX(0deg);
  }

  100% {
    transform: rotateX(-360deg);
  }
}

@keyframes arc {
  to {
    transform: rotate(360deg);
  }
}

</style>