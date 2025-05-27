<template>
  <view class="mine tn-safe-area-inset-bottom">

    <!-- 顶部自定义导航 -->
    <tn-nav-bar :isBack="false" :bottomShadow="false" backgroundColor="none">
      <view class="custom-nav tn-flex tn-flex-col-center tn-flex-row-left" @click="tn('/homePages/set')">
        <view class="custom-nav__back">
          <view class="tn-icon-set tn-color-cat" style="font-size: 50rpx;">
          </view>
        </view>
      </view>
    </tn-nav-bar>

    <view class="top-backgroup">
      <image src='https://resource.tuniaokj.com/images/my/my-bg4.png' mode='widthFix' class='backgroud-image'></image>
    </view>

    <view class="about__wrap" :style="{paddingTop: vuex_custom_bar_height + 'px'}">
      <!-- 图标logo/头像 -->
      <view class="tn-flex tn-flex-row-between tn-flex-col-center tn-margin-bottom" style="margin-top: -450rpx;">
        <view class="justify-content-item">
          <view class="tn-flex tn-flex-col-center tn-flex-row-left">
            <view class="logo-pic tn-shadow">
              <view class="logo-image" @click="authorizeClick">
                <view class="tn-shadow-blur" :style="'background-image:url(' + imageUrl+userInfo.avatar + ');width: 110rpx;height: 110rpx;background-size: cover;'">
                </view>

              </view>
            </view>
            <view class="tn-padding-right">
              <view class="tn-padding-right tn-padding-left-sm" @click="authorizeClick">
                <text class="tn-color-cat tn-text-xl tn-text-bold">{{userInfo.nickName}}</text>
              </view>
              <view class="tn-padding-right tn-padding-top-xs tn-padding-left-sm tn-text-ellipsis">
                <text class="tn-color-gray" v-if="userInfo.isBoss===1 && userInfo.hotel">店主：{{userInfo.hotel.homeName}}</text>
                <text class="tn-color-gray tn-padding-left-sm tn-text-sm">{{userInfo.phone}}</text>
              </view>
            </view>

          </view>
        </view>
        <view class="justify-content-item"  @click="tn('/homePages/set')">
          <view class="tn-icon-right tn-color-gray">
          </view>
        </view>
      </view>

      <!-- 没有授权，则显示这个授权按钮-->
       <view class="tn-flex tn-flex-row-between" @click="authorizeClick" v-if="!userInfo.avatar || userInfo.avatar==='' ">
        <view class="tn-flex-1 justify-content-item tn-margin-xs tn-text-center">
          <tn-button shape="round" backgroundColor="#1D2541" fontColor="#ffffff" padding="20rpx 0" width="40%" shadow>
            <text class="tn-icon-wechat tn-padding-right-xs tn-text-xl"></text>
            <text class="">授权登录</text>
          </tn-button>
        </view>
      </view>

      <view class="tn-flex tn-flex-row-between tn-bg-white about-shadow tn-margin-top-xl">
        <view class="tn-padding-sm tn-margin-xs" @click="tn('/homePages/orderList')">
          <view class="tn-flex tn-flex-direction-column tn-flex-row-center tn-flex-col-center tn-margin-left">
            <view class="icon15__item--icon tn-flex tn-flex-row-center tn-flex-col-center tn-shadow-blur" style="background-color: #F3F2F7;color: #7C8191;">
              <view class="tn-icon-order tn-color-cat"></view>
            </view>
            <view class="tn-text-center">
              <text class="tn-text-ellipsis">我的订单</text>
            </view>
          </view>
        </view>
        <view class="tn-padding-sm tn-margin-xs" @click="tn('/homePages/shopOrderList')" v-if="userInfo.isBoss">
          <view class="tn-flex tn-flex-direction-column tn-flex-row-center tn-flex-col-center">
            <view class="icon15__item--icon tn-flex tn-flex-row-center tn-flex-col-center tn-shadow-blur" style="background-color: #F3F2F7;color: #7C8191;">
              <view class="tn-icon-shop"></view>
            </view>
            <view class="tn-text-center">
              <text class="tn-text-ellipsis">商家订单</text>
            </view>
          </view>
        </view>
        <view class="tn-padding-sm tn-margin-xs" @click="tn('/homePages/storeOwner')">
          <view class="tn-flex tn-flex-direction-column tn-flex-row-center tn-flex-col-center tn-margin-right">
            <view class="icon15__item--icon tn-flex tn-flex-row-center tn-flex-col-center tn-shadow-blur" style="background-color: #F3F2F7;color: #7C8191;">
              <view class="tn-icon-my-add tn-color-cat"></view>
            </view>
            <view class="tn-text-center">
              <text class="tn-text-ellipsis">店主注册</text>
            </view>
          </view>
        </view>
      </view>
    </view>

    <view class='tn-tabbar-height'></view>

    <!--授权登录-->
    <view>
      <Authorize v-model="showAuthorizationModal" :userInfo="userInfo"></Authorize>
    </view>

  </view>
</template>

<script>
import Authorize from '@/components/user/authorize.vue'
export default {
  name: 'Mine',
  components: {
    Authorize
  },
  data() {
    return {
      userInfo:uni.getStorageSync('userInfo'),
      showAuthorizationModal:false,
      imageUrl:this.websiteUrl,
    }
  },
  created() {
    if(typeof this.userInfo =="string"){
      this.userInfo=JSON.parse(this.userInfo);
    }
  },
  methods: {
    // 跳转
    tn(e) {
      uni.navigateTo({
        url: e,
      });
    },
    // 收货地址
    authorizeClick() {
     this.showAuthorizationModal=true;
    },

  }
}
</script>

<style lang="scss" scoped>
.mine {
  max-height: 100vh;
}

/* 底部安全边距 start*/
.tn-tabbar-height {
  min-height: 120rpx;
  height: calc(140rpx + env(safe-area-inset-bottom) / 2);
  height: calc(140rpx + constant(safe-area-inset-bottom));
}

.tn-color-cat {
  color: #1D2541;
}

.tn-bg-cat {
  background-color: #1D2541;
}


/* 自定义导航栏内容 start */
.custom-nav {
  height: 100%;

  &__back {
    margin: auto 5rpx;
    font-size: 40rpx;
    margin-right: 10rpx;
    flex-basis: 5%;
    width: 100rpx;
    position: absolute;
  }
}

/* 自定义导航栏内容 end */


/* 顶部背景图 end */


/* 用户头像 start */
.logo-image {
  width: 110rpx;
  height: 110rpx;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}

.logo-pic {
  background-size: cover;
  background-repeat: no-repeat;
  // background-attachment:fixed;
  background-position: top;
  border: 8rpx solid rgba(255, 255, 255, 0.05);
  box-shadow: 0rpx 0rpx 80rpx 0rpx rgba(0, 0, 0, 0.15);
  border-radius: 50%;
  overflow: hidden;
  // background-color: #FFFFFF;
}

/* 页面 start*/
.about-shadow {
  border-radius: 15rpx;
  box-shadow: 0rpx 0rpx 50rpx 0rpx rgba(0, 0, 0, 0.07);
}

.about {

  &__wrap {
    position: relative;
    z-index: 1;
    margin: 20rpx 30rpx;
  }
}

/* 页面 end*/

/* 图标容器15 start */
.icon15 {
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


      }
    }
  }
}

/* 图标容器12 start */
.tn-three {
  position: absolute;
  top: 50%;
  right: 50%;
  bottom: 50%;
  left: 50%;
  transform: translate(-38rpx, -16rpx) rotateX(30deg) rotateY(20deg) rotateZ(-30deg);
  text-shadow: -1rpx 2rpx 0 #f0f0f0, -2rpx 4rpx 0 #f0f0f0, -10rpx 20rpx 30rpx rgba(0, 0, 0, 0.2);
}

.icon20 {
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
        background-image: url(https://resource.tuniaokj.com/images/cool_bg_image/icon_bg.png);
      }
    }
  }
}


.button-vip {
  width: 100%;
  height: 150rpx;
  border-radius: 15rpx;
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
    background-image: url(https://resource.tuniaokj.com/images/cool_bg_image/icon_bg.png);
  }
}


/* 图标容器12 start */
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
      width: 15rpx;
      height: 15rpx;
      font-size: 50rpx;
      border-radius: 50%;
      margin-bottom: 38rpx;
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

      }
    }
  }
}

/* 图标容器1 start */
.icon1 {
  &__item {
    // width: 30%;
    background-color: #FFFFFF;
    border-radius: 10rpx;
    padding: 30rpx;
    margin: 20rpx 10rpx;
    transform: scale(1);
    transition: transform 0.3s linear;
    transform-origin: center center;

    &--icon {
      width: 40rpx;
      height: 40rpx;
      font-size: 40rpx;
      border-radius: 50%;
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
        background-image: url(https://resource.tuniaokj.com/images/cool_bg_image/icon_bg.png);
      }
    }
  }
}

/* 图标容器1 end */


/* 顶部背景图 start */
.top-backgroup {
  height: 450rpx;
  z-index: -1;

  .backgroud-image {
    width: 100%;
    height: 450rpx;
    // z-index: -1;
  }
}

/* 顶部背景图 end */


</style>
