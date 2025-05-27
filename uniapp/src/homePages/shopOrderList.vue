<template>
  <view class="template-order">
    <!-- 顶部自定义导航 -->
    <tn-nav-bar fixed alpha customBack>
      <view slot="back" class='tn-custom-nav-bar__back'
            @click="goBack">
        <text class='icon tn-icon-left-arrow'></text>
      </view>
      <view class="tn-flex tn-flex-col-center tn-flex-row-center ">
          <text class="tn-text-bold tn-text-xl tn-color-black">商家的订单</text>
      </view>
    </tn-nav-bar>

    <view class="order--wrap" :style="{top: vuex_custom_bar_height + 'px'}" >
      <!-- 顶部标签 -->
      <view class="tn-bg-white">
        <tn-tabs-swiper class="order__tabs" ref="tabs" activeColor="#3165CC" inactiveColor="#080808" :list="list" :current="tabsIndex" :isScroll="false" @change="tabsChange"></tn-tabs-swiper>
      </view>

      <!-- 标签内容 -->
      <swiper class="order__swiper" :style="{top: `${swiperTop}px`, height: `${swiperHeight}px`}" :current="swiperIndex" @transition="swiperTransition" @animationfinish="swiperAnimationFinish">
        <swiper-item class="order__swiper__item" v-for="(tab,tabIndex) in list" :key="tabIndex">
          <scroll-view :style="{height: `${swiperHeight}px`}" scroll-y>
            <view v-for="(item,index) in orderList" :key="index" class="order__item">
              <view class="order__item__head tn-flex tn-flex-direction-row tn-flex-col-center tn-flex-row-between">
                <view class="order__item__head__title" >{{item.room.hotelName}}</view>
                <view class="order__item__head__title" v-if="item.status === 0">待确认</view>
                <view class="order__item__head__title" v-if="item.status === 1">待入住</view>
                <view class="order__item__head__title" v-if="item.status === 2">已完成</view>
             </view>

              <view class="order__item__content tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-between">
                <view class="tn-flex tn-flex-nowrap tn-flex-direction-row tn-flex-col-center tn-flex-row-left">
                  <view class="order__item__content__image">
                    <image :src="imageUrl+item.room.mainImg " mode="aspectFill"></image>
                  </view>
                </view>
                <view>
                  入住：{{item.checkInTime}}<br>离店：{{item.checkOutTime}}
                </view>
                <view>
                  房间名：{{item.room.roomName}}<br>房间号：{{item.room.roomNumber}}
                </view>
                <view class="order__item__content__info tn-flex tn-flex-direction-column tn-flex-col-center tn-flex-row-center">
                  <view class="order__item__content__info__price">
                    <text class="order__item__content__info__price--unit">￥</text>
                    <text class="order__item__content__info__price__value--integer">{{item.amount}}</text>
                    <text class="order__item__content__info__price__value--decimal">.00</text>
                  </view>
                  <view class="order__item__content__info__count">
                    <text>共{{item.days}}天</text>
                  </view>
                </view>
              </view>
              <view class="order__item__operation tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-between">
                <view class="order__item__operaation__left">
                  <text class="order__item__operaation__left--text">订单编号：{{item.sn}}</text>
                </view>
                <view class="order__item__operation__right tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-right">
                  <view class="order__item__operation__right__button">
                    <tn-button v-if="item.status ===0 " shadow shape="round" fontColor="tn-color-white" backgroundColor="tn-bg-blue" :fontSize="24" height="auto" padding="10rpx 18rpx" @click="updateOrder(item)">确认</tn-button>
                    <tn-button v-if="item.status ===1 " shadow shape="round" fontColor="tn-color-white" backgroundColor="tn-bg-purplered" :fontSize="20" height="auto" padding="10rpx 18rpx" @click="updateOrder(item)">登记入住</tn-button>
                  </view>
                </view>
              </view>
            </view>
            <view class="tn-padding-bottom"></view>
          </scroll-view>
        </swiper-item>
      </swiper>
    </view>

    <!-- 模态框 -->
    <tn-modal
        v-model="show"
        :backgroundColor="backgroundColor"
        :width="width"
        :padding="padding"
        :radius="radius"
        :fontColor="fontColor"
        :fontSize="fontSize"
        :title="title"
        :content="content"
        :button="button"
        :showCloseBtn="closeBtn || !maskCloseable"
        :maskCloseable="maskCloseable"
        :zoom="zoom"
        :custom="custom"
        @click="clickBtn"
    >
    </tn-modal>


  </view>
</template>

<script>
import templatePageMixin from '@/libs/mixin/template_page_mixin.js'
import {userOrderUpdate} from "../api/modules/user";

export default {
  name: 'TemplateOrder',
  mixins: [templatePageMixin],
  data() {
    return {
      // 当前选中的模式
      list: [
        {name: '全部'},
        {name: '待确认'},
        {name: '待入住'},
        {name: '已完成'}
      ],
      tabsIndex: 0,
      swiperIndex: 0,
      swiperTop: 0,
      swiperHeight: 0,
      userInfo:uni.getStorageSync('userInfo') ,
      orderList:[],
      imageUrl:this.websiteUrl,
      show:false,
      title: '使用提醒',
      content: '确定删除该笔订单吗？',
      backgroundColor: '',
      width: '84%',
      padding: '',
      radius: 12,
      fontColor: '',
      fontSize: 0,
      button:[
        {
          text: '取消',
          backgroundColor: '#A4E82F',
          fontColor: '#FFFFFF'
        },
        {
          text: '确定',
          backgroundColor: 'tn-bg-indigo',
          fontColor: '#FFFFFF'
        }
      ],
      closeBtn: true,
      maskCloseable: true,
      zoom: true,
      custom: false,
    }
  },
  onLoad() {
    if(typeof this.userInfo=="string"){
      this.userInfo=JSON.parse(this.userInfo);
    }
    this.getOrderList();
  },
  onReady() {
    this.$nextTick(() => {
      this.updateSwiperInfo()
    })
  },

  methods: {
    updateOrder(item) {
      console.log('订单信息：：',item);
      let orderStatus=0;
      if(item.status ===0 ){
        orderStatus=1;
      }else if(item.status ===1){
        orderStatus=2;
      }
      item.status=orderStatus;
      let that=this;
      that.$api.userOrderUpdate(item).then(res =>{
            console.log("返回的信息：：",res)
            if(res.code ===200){
              uni.showToast({
                title: '操作成功',
                icon: 'success',
                image: '',
                duration: 1000
              })
              that.getOrderList();
            }
      })
    },
    // 点击按钮
    clickBtn(event) {
      this.show = false
      // this.$tn.message.toast('点击了第'+(event.index + 1)+'个按钮')
      console.log(event)
    },
    getOrderList(status){
      // console.log("状态：：",status)
      console.log("userInfo：：",this.userInfo)
      this.orderList=[];
      let hotelId=this.userInfo.hotel.id;
      let params={hotelId:hotelId};
      if(status>=0){
        params.status=status;
      }
      this.$api.getOrderList(params).then(async res => {
        //console.log(res)
        if(res.code === 200){
          this.orderList=res.rows;
        }
      })
    },
    // 跳转
    tn(e,data) {
      console.log("点击了评价按钮")
      uni.navigateTo({
        url: e + "?id="+data,
      });
    },



    // 计算可滑动区域的高度信息
    updateSwiperInfo() {
      // 获取可滑动菜单的信息
      this._tGetRect('.order__tabs').then(res => {
        if (!res) {
          setTimeout(() => {
            this.updateSwiperInfo()
          }, 10)
          return
        }
        const systemInfo = uni.getSystemInfoSync()
        this.swiperTop = res.bottom - this.vuex_custom_bar_height
        this.swiperHeight = systemInfo.safeArea.height - res.bottom + systemInfo.statusBarHeight
      })
    },
    // 标签栏值发生改变
    tabsChange(index) {
      this.swiperIndex = index
      console.log("index::",index)
      let status=null;
      if(index>=1){
        status=index-1;
      }
      this.getOrderList(status);
    },
    // swiper-item位置发生改变
    swiperTransition(event) {
      this.$refs.tabs.setDx(event.detail.dx)
    },
    // swiper动画结束
    swiperAnimationFinish(event) {
      const current = event.detail.current
      this.$refs.tabs.setFinishCurrent(current)
      this.swiperIndex = current
      this.tabsIndex = current
    },

  }
}
</script>

<style lang="scss" scoped>

.template-order {
  background-color: #FFFFFF;
}

/* 胶囊*/
.tn-custom-nav-bar__back {
  width: 60%;
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

}

.login {
  position: relative;
  height: 100%;
  z-index: 1;

  /* 内容 start */
  &__wrapper {
    width: 100%;
  }

  /* 切换 start */
  &__mode {
    position: relative;
    margin: 0 auto;
    width: 326rpx;
    height: 60rpx;
    margin-top: 16rpx;
    border: 1rpx solid #E6E6E6;
    border-radius: 39rpx;

    &__item {
      height: 77rpx;
      width: 100%;
      line-height: 77rpx;
      text-align: center;
      font-size: 31rpx;
      color: #AAAAAA;
      z-index: 2;
      transition: all 0.4s;

      &--active {
        font-weight: bold;
        color: #000000;
      }
    }
  }
  /* 切换 end */

}


.order {
  &--wrap {
    position: fixed;
    left: 0;
    right: 0;
    width: 100%;
    background-color: inherit;
  }

  /* 导航栏 start */
  &__tabs {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    background-color: inherit;
  }
  /* 导航栏 end */

  /* swiper start */
  &__swiper {
    position: absolute;
    left: 0;
    right: 0;
    width: 100%;
    background-color: inherit;
    // padding: 0 16rpx;
  }
  /* swiper end */

  /* 订单内容 start */
  &__item {
    margin: 20rpx;
    padding: 36rpx 26rpx;
    background-color: #FFFFFF;
    border-radius: 15rpx;
    box-shadow: 0rpx 0rpx 50rpx 0rpx rgba(0, 0, 0, 0.07);

    &:first-child {
      margin-top: 40rpx;
    }

    &:last-child {
      margin-bottom: 0;
    }

    /* 头部 start */
    &__head {

      &__title {
        font-weight: bold;
        line-height: normal;

        &--right-icon {
          font-size: 24rpx;
          margin-left: 8rpx;
        }
      }

      &__status {
        font-size: 22rpx;
        color: #AAAAAA;
      }
    }
    /* 头部 end */

    /* 内容 start */
    &__content {

      margin-top: 20rpx;

      &__image {
        margin-right: 20rpx;

        image {
          width: 140rpx;
          height: 140rpx;
          border-radius: 10rpx;
        }
      }

      &__title {
        padding-right: 40rpx;
        display: -webkit-box;
        overflow: hidden;
        white-space: normal !important;
        text-overflow: ellipsis;
        word-wrap: break-word;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
      }

      &__info {

        &__price {
          &--unit {
            font-size: 20rpx;
          }
          &__value--integer, &__value--decimal {
            font-weight: bold;
          }
          &__value--decimal {
            font-size: 20rpx;
          }
        }

        &__count {
          color: #AAAAAA;
          font-size: 24rpx;
        }
      }
    }
    /* 内容 end */

    /* 操作按钮 start */
    &__operation {
      margin-top: 30rpx;

      &__right {
        &__button {
          margin-left: 10rpx;
        }
      }
    }
    /* 操作按钮 end */
  }
  /* 订单内容 end */
}


</style>
