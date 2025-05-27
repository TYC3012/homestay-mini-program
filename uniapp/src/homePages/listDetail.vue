<template>
  <view class="tn-bg-gray-light">
    <tn-toast ref="toast"></tn-toast>
    <!-- 顶部自定义导航 -->
    <tn-nav-bar fixed alpha customBack>
      <view slot="back" class='tn-custom-nav-bar__back'
            @click="goBack">
        <text class='icon tn-icon-left'></text>
        <text class='icon tn-icon-home-capsule-fill'></text>
      </view>
    </tn-nav-bar>
    <tn-swiper :list="swiperList" mode="number" :height="400"></tn-swiper>
    <view class="tn-margin">
      <!--   标题     -->
      <view class="tn-flex tn-flex-row-between">
        <view class="justify-content-item tn-text-bold tn-text-xxl">
          {{ roomInfo.roomName }}
        </view>
      </view>
      <view class="tn-flex tn-flex-row-between tn-margin-top">
        <view class="tn-color-purplered">
          <!--     价格     -->
          <view>
            <text class="tn-text-bold tn-text-xxl" style="color: #FF9903">￥{{ roomInfo.price }}</text>
            <text class="tn-text-sm tn-color-gray tn-margin-left-xs">/月</text>
          </view>
          <view class="tn-padding-top-lg">
            <!--   房屋详情页的标签     -->
            <tn-tag v-for="(item,index) in roomInfo.label"  :key="index" marginLeft="10rpx" size="sm" backgroundColor="shadow tn-bg-gray--light tn-margin-left-sm" fontColor="tn-color-gray--dark" :fontSize="20">
              {{item}}
            </tn-tag>
          </view>
        </view>

      </view>
    </view>
    <!-- 边距间隔 -->
    <view class="">
      <!--   房屋规格说明     -->
      <view class="tn-padding tn-flex tn-margin">
        <view class="tn-flex-direction-column tn-col-4 tn-margin-left-sm tn-text-center" style="border-right: 1px solid #f8f7f8">
          <view>
            <text class="tn-text-sm tn-text-bold" style="color: #03D3C1;">床型 - {{ roomInfo.bedType}}</text>
          </view>
          <view class="tn-margin-top-xs">
            <text class="tn-text-xs tn-color-gray">床型</text>
          </view>
        </view>
        <view class="tn-flex-direction-column tn-col-4 tn-margin-left-sm tn-text-center" style="border-right: 1px solid #f8f7f8">
          <view>
            <text class="tn-text-sm tn-text-bold" style="color: #03D3C1;">{{ roomInfo.area }}m²</text>
          </view>
          <view class="tn-margin-top-xs">
            <text class="tn-text-xs tn-color-gray">面积</text>
          </view>
        </view>
        <view class="tn-flex-direction-column tn-col-4 tn-margin-left-sm tn-text-center" style="border-right: 1px solid #f8f7f8">
          <view>
            <text class="tn-text-sm tn-text-bold" style="color: #03D3C1;">{{ roomInfo.floor }}/</text><text class="tn-text-sm tn-color-gray">
            {{ roomInfo.totalFloor }}</text>
          </view>
          <view class="tn-margin-top-xs">
            <text class="tn-text-xs tn-color-gray">楼层</text>
          </view>
        </view>
        <view class="tn-flex-direction-column tn-col-4 tn-margin-left-sm tn-text-center">
          <view>
            <text class="tn-text-sm tn-text-bold" style="color: #03D3C1;">{{roomInfo.occupancy}}</text>
          </view>
          <view class="tn-margin-top-xs">
            <text class="tn-text-xs tn-color-gray">入住</text>
          </view>
        </view>
      </view>
    </view>
    <view>

      <picker @change="bindDateChange('start')" mode="date" :value="date" :start="startDate" :end="endDate">
        <view class="tn-flex tn-flex-row-between tn-strip-bottom-min tn-padding">
          <view class="justify-content-item">
            <view class="tn-text-bold tn-text-lg">
              入住日期
            </view>
            <view class="tn-color-gray tn-padding-top-xs">
              {{orderInfo.checkInTime}}
            </view>
          </view>
          <view class="justify-content-item tn-text-lg tn-color-grey">
            <view class="tn-icon-right tn-padding-top"></view>
          </view>
        </view>
      </picker>

      <picker @change="bindDateChange2" mode="date" :value="date" :start="startDate" :end="endDate">
        <view class="tn-flex tn-flex-row-between tn-strip-bottom-min tn-padding">
          <view class="justify-content-item">
            <view class="tn-text-bold tn-text-lg">
              离开日期
            </view>
            <view class="tn-color-gray tn-padding-top-xs">
              {{orderInfo.checkOutTime}}
            </view>
          </view>
          <view class="justify-content-item tn-text-lg tn-color-grey">
            <view class="tn-icon-right tn-padding-top"></view>
          </view>
        </view>
      </picker>

      <view class="tn-flex tn-flex-row-between tn-strip-bottom-min tn-padding">
        <view class="justify-content-item">
          <view class="tn-text-bold tn-text-lg">
            金额：{{orderInfo.amount}}元
          </view>
        </view>
      </view>

      <view class="tn-flex tn-flex-row-between tn-strip-bottom-min tn-padding">
        <view class="justify-content-item">
          <view class="tn-text-bold tn-text-lg">
            入住天数：{{orderInfo.days}}天
          </view>
        </view>
      </view>





    </view>
    <view class="footerfixed dd-glass tn-padding-left-sm tn-padding-right tn-padding-top-xs tn-padding-bottom-sm">
      <tn-goods-nav :options="countOptions" :buttonGroups="customButtonGroups" buttonType="round" :safeAreaInsetBottom="true" @optionClick="onOptionClick" @buttonClick="onButtonClick"></tn-goods-nav>
    </view>
    <view class='tn-tabbar-height'></view>
    <tn-toast ref="toast"></tn-toast>
  </view>
</template>

<script>
import template_page_mixin from '@/libs/mixin/template_page_mixin.js'
import {getChineseHeadLetter} from '@/utils/str'
import {userCollectSave, userOrderSave, userViewLeave} from "../api/modules/user";
export default {
  name: 'listDetail',
  mixins: [template_page_mixin],
  data(){
    return {
      id: 0,
      markers: [],
      cardCur: 0,
      roomInfo: {},
      swiperList: [],
      current: 0,
      countOptions: [{
        icon: 'star',
        text: '收藏'
      }],
      customButtonGroups: [{
        text: '立即预订',
        backgroundColor: 'tn-cool-bg-color-8--reverse',
        color: '#FFFFFF'
      }],
      userInfo:uni.getStorageSync('userInfo') ,
      roomId:'',
      viewTime:'',
      imageUrl:this.websiteUrl,
      date: '',
      index1: 1,
      orderInfo:{
        checkInTime: '',
        checkOutTime: '',
        amount:'',
        days:'',
      },

    }
  },
  computed: {
    startDate() {
      return this.getDate('start');
    },
    endDate() {
      return this.getDate('end');
    }
  },
  async onLoad(data) {
    let nowDate=this.getDate();
    this.orderInfo.checkInTime=nowDate;
    //console.log("nowDate::",nowDate)
    // return
    if(typeof this.userInfo=="string"){
      this.userInfo=JSON.parse(this.userInfo);
    }

    this.roomId=data.id;
    let params={id: data.id,userId:this.userInfo.id,queryType:"view"};
    params.userName=this.userInfo.nickName;
    this.viewTime=new Date();
    this.$api.getRoomById(params).then(res =>{
      console.log("res::",res)
      if (res.code==200){
        // res.data.label = res.data.label.split(",")
        // res.data.facility = res.data.facility.split(",")
        let temp = [];
        let split = res.data.detailsImg.split(",");
        for (let i = 0; i<split.length; i++){
          temp.push({
            image: this.imageUrl+split[i]
          })
        }
        this.roomInfo = res.data;
        console.log("temp::",temp)
        console.log("this.roomInfo::",this.roomInfo)
        this.swiperList=temp;
        res.data.imageList = temp;




      }else{
        this.$refs.toast.show({
          title: '系统异常',
          content: '系统异常请联系管理员',
          icon: 'error',
          duration: 1900
        })
      }
    })
  },
  mounted() {

  },
  async onUnload() {
    // 在这里编写需要执行的操作
    let endDate = new Date();
    var diff = endDate.getTime() - this.viewTime.getTime()// 时间差的毫秒数
    console.log('时间差的毫秒数',diff);
    let params={roomId: this.roomId,userId:this.userInfo.id,queryType:"view"};
    params.viewDuration=diff;
    console.log('页面已经离开',params);
    let _this = this;
    _this.$api.userViewLeave(params).then(res =>{
      console.log("返回的信息：：",res)
    })
  },
  methods: {
    bindDateChange: function(e) {
      this.orderInfo.checkInTime = e.detail.value
    },
    bindDateChange2: function(e) {
      this.orderInfo.checkOutTime = e.detail.value;
      let diff=this.DateDiff(this.orderInfo.checkInTime,this.orderInfo.checkOutTime);
      this.orderInfo.days=diff;
      this.orderInfo.amount=diff*this.roomInfo.price;
      console.log("this.orderInfo::",this.orderInfo)
    },
    //算相隔天数
    DateDiff(sDate1, sDate2) {
      var aDate, oDate1, oDate2, iDays;
      aDate = sDate1.split("-");
      oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);  //转换为yyyy-MM-dd格式
      aDate = sDate2.split("-");
      oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
      iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
      return iDays + 1;  //返回相差天数
    },
    getDate(type) {
      const date = new Date();
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();

      if (type === 'start') {
        year = year - 60;
      } else if (type === 'end') {
        year = year + 2;
      }
      month = month > 9 ? month : '0' + month;
      day = day > 9 ? day : '0' + day;
      return `${year}-${month}-${day}`;
    },
    goBack() {
      // 通过判断当前页面的页面栈信息，是否有上一页进行返回，如果没有则跳转到首页
      uni.reLaunch({
        url: '/pages/index'
      })
    },
    onOptionClick(res){
      this.$refs.toast.show({
        title: '成功',
        content: '即将跳转到订单页面',
        icon: 'success',
        image: '',
        duration: 1500
      })
      /*uni.navigateTo({
        url: '/pages/order/confirm-order' + "?id="+data,
      });*/
      if(res.index === 0){
        let params={roomId: this.roomId,userId:this.userInfo.id};
        params.userName=this.userInfo.nickName;
        params.isCollect=1;
        console.log('点击了收藏',params);
        let _this = this;
        _this.$api.userCollectSave(params).then(res =>{
          console.log("返回的信息：：",res)
        })
      }

    },
    onButtonClick(res){
      console.log("this.roomInfo::",this.roomInfo)
      let params=this.orderInfo;
      params.userName=this.userInfo.nickName;
      params.roomName=this.roomInfo.roomName;
      params.roomId=this.roomInfo.id;
      params.userId=this.userInfo.id;
      params.isCollect=1;
      params.hotelId=this.roomInfo.hotelId;

      console.log('用户点击了确认订单',params);
      // return
      let _this = this;
      _this.$api.userOrderSave(params).then(res =>{
        console.log("返回的信息：：",res)
        if(res.code ===200){
          _this.$refs.toast.show({
            title: '预定成功',
            content: '即将跳转到订单页面',
            icon: 'success',
            image: '',
            duration: 3000
          })
          uni.navigateTo({url: '/homePages/orderList'});
        }
      })
    },
    // cardSwiper
    cardSwiper(e) {
      this.cardCur = e.detail.current
    },
    // 跳转
    tn(e) {
      uni.navigateTo({
        url: e,
      });
    },
    // tab选项卡切换
    tabChange(index) {
      this.current = index
    },
    getRandomCoolBg() {
      return this.$t.colorUtils.getRandomCoolBgClass()
    }
  }
}
</script>

<style lang="scss" scoped>
.tn-tabbar-height {
  min-height: 120rpx;
  height: calc(140rpx + env(safe-area-inset-bottom) / 2);
}

/* 用户头像 start */
.logo-image {
  width: 110rpx;
  height: 110rpx;
  position: relative;
}

.logo-pic {
  background-size: cover;
  background-repeat: no-repeat;
  // background-attachment:fixed;
  background-position: top;
  box-shadow: 0rpx 0rpx 80rpx 0rpx rgba(0, 0, 0, 0.15);
  border-radius: 10rpx;
  overflow: hidden;
  // background-color: #FFFFFF;
}

/* 胶囊*/
.tn-custom-nav-bar__back {
  width: 100%;
  height: 100%;
  position: relative;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  box-sizing: border-box;
  background-color: rgba(0, 0, 0, 0.15);
  border-radius: 1000rpx;
  border: 1rpx solid rgba(255, 255, 255, 0.5);
  color: #FFFFFF;
  font-size: 18px;

  .icon {
    display: block;
    flex: 1;
    margin: auto;
    text-align: center;
  }

  &:before {
    content: " ";
    width: 1rpx;
    height: 110%;
    position: absolute;
    top: 22.5%;
    left: 0;
    right: 0;
    margin: auto;
    transform: scale(0.5);
    transform-origin: 0 0;
    pointer-events: none;
    box-sizing: border-box;
    opacity: 0.7;
    background-color: #FFFFFF;
  }
}

/* 轮播视觉差 start */
.card-swiper {
  height: 750rpx !important;
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

.image-banner{
  display: flex;
  align-items: center;
  justify-content: center;
}
.image-banner image{
  width: 100%;
  height: 100%;
}

/* 轮播指示点 start*/
.indication{
  z-index: 9999;
  width: 100%;
  height: 36rpx;
  position: absolute;
  display:flex;
  flex-direction:row;
  align-items:center;
  justify-content:center;
}

.spot{
  background-color: #FFFFFF;
  opacity: 0.6;
  width: 10rpx;
  height: 10rpx;
  border-radius: 20rpx;
  top: -60rpx;
  margin: 0 8rpx !important;
  position: relative;
}

.spot.active{
  opacity: 1;
  width: 30rpx;
  background-color: #FFFFFF;
}

/* 间隔线 start*/
.tn-strip-bottom {
  width: 100%;
  border-bottom: 20rpx solid rgba(241, 241, 241, 0.8);
}
/* 间隔线 end*/
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
    background-image: url(https://tnuiimage.tnkjapp.com/title_bg/title44.png);
    background-size: cover;
  }
}
/* 标题 end */

/* 底部tabbar start*/
.footerfixed{
  position: fixed;
  width: 100%;
  bottom: 0;
  z-index: 999;
  background-color: #FFFFFF;
  box-shadow: 0rpx 0rpx 30rpx 0rpx rgba(0, 0, 0, 0.07);
}

/* 标签内容 start*/
.tn-tag-content {
  &__item {
    display: inline-block;
    line-height: 45rpx;
    padding: 10rpx 30rpx;
    margin: 20rpx 20rpx 5rpx 0rpx;

    &--prefix {
      padding-right: 10rpx;
    }
  }
}
/* 标签内容 end*/

/* 内容图 start */
.content-backgroup {
  z-index: -1;

  .backgroud-image {
    border-radius: 15rpx;
    width: 100%;
  }
}
/* 内容图 end */

/* 商家商品 start*/
.tn-blogger-content {
  &__wrap {
    box-shadow: 0rpx 0rpx 50rpx 0rpx rgba(0, 0, 0, 0.07);
    border-radius: 20rpx;
    margin: 15rpx;
  }

  &__info {
    &__btn {
      margin-right: -12rpx;
      opacity: 0.5;
    }
  }

  &__label {
    &__item {
      line-height: 45rpx;
      padding: 0 10rpx;
      margin: 5rpx 18rpx 0 0;

      &--prefix {
        color: #E83A30;
        padding-right: 10rpx;
      }
    }

    &__desc {
      line-height: 35rpx;
    }
  }

  &__main-image {
    border-radius: 16rpx 16rpx 0 0;

    &--1 {
      max-width: 690rpx;
      min-width: 690rpx;
      max-height: 400rpx;
      min-height: 400rpx;
    }

    &--2 {
      max-width: 260rpx;
      max-height: 260rpx;
    }

    &--3 {
      height: 212rpx;
      width: 100%;
    }
  }

  &__count-icon {
    font-size: 24rpx;
    padding-right: 5rpx;
  }
}

.image-book{
  padding: 150rpx 0rpx;
  font-size: 16rpx;
  font-weight: 300;
  position: relative;
}
.image-picbook{
  background-size: cover;
  background-repeat:no-repeat;
  // background-attachment:fixed;
  background-position:top;
  border-radius: 15rpx 15rpx 0 0;
}

/* 毛玻璃*/
.dd-glass {
  width: 100%;
  backdrop-filter: blur(20rpx);
  -webkit-backdrop-filter: blur(20rpx);
}
</style>
