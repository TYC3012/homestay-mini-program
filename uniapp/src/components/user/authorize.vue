<template>
  <view
    v-if="openModal"
    class="wx-authorization-modal"
  >
    <view
      class="wam__mask"
      @touchmove.prevent=""
      @tap.stop="closeModal"
    ></view>
    
    <!-- 内容区域 -->
    <view class="wam__wrapper">
      <!-- 关闭按钮 -->
      <view class="wam__close-btn" @tap.stop="closeModal">
        <text class="tn-icon-close"></text>
      </view>
      <!-- 标题 -->
      <view class="wam__title">获取您的昵称、头像</view>
      
      <!-- 头像选择 -->
      <view class="wam__avatar">
        <view class="button-shadow">
          <button
            class="button"
            open-type="chooseAvatar"
            @chooseavatar="chooseAvatarEvent"
          >
            <view v-if="userInfo.avatar" class="avatar__image">
              <image class="image" :src="websiteUrl+userInfo.avatar" mode="aspectFill"></image>
            </view>
            <view v-else class="avatar__empty">
              <image class="image" src="https://cdn.nlark.com/yuque/0/2022/jpeg/280373/1668928062708-assets/web-upload/764843cf-055a-4cb6-b5d3-dca528b33fd4.jpeg" mode="aspectFill"></image>
            </view>
            <view class="avatar--icon">
              <view class="tn-icon-camera-fill"></view>
            </view>
          </button>
        </view>
      </view>
      
      <!-- 昵称输入 -->
      <view class="wam__nickname">
        <view class="nickname__data">
          <input class="input" type="nickname" v-model="userInfo.nickName" placeholder="请输入昵称" placeholder-style="color: #AAAAAA;" @change="updateUserName">
        </view>
      </view>
      
      
      <!-- 保存按钮 -->
      <view
        class="wam__submit-btn"
        hover-class="tn-btn-hover-class"
        :hover-stay-time="150"
        @tap.stop="submitUserInfo"
      >
        保 存
      </view>
    </view>
  </view>
</template>

<script>
  
  export default {
    options: {
      // 在微信小程序中将组件节点渲染为虚拟节点，更加接近Vue组件的表现(不会出现shadow节点下再去创建元素)
      virtualHost: true
    },
    props: {
      value: {
        type: Boolean,
        default: false
      },
      userInfo: {
        type: Object,
        default: {}
      }
    },
    data() {
      return {
        openModal: false,
        websiteUrl:this.websiteUrl,
      }
    },
    watch: {
      value: {
        handler(val) {
          this.openModal = val
        },
        immediate: true
      }
    },
    methods: {
      // 头像选择
      chooseAvatarEvent(e) {
        var access_token = uni.getStorageSync('access_token');
        // console.log("websiteUrl::",this.websiteUrl)
        // console.log("loginUser::",this.loginUser)
        // console.log("access_token::",access_token)
        // return
        this.userInfo.avatar = e.detail.avatarUrl;
        uni.uploadFile({
          url: this.websiteUrl+'/wechat/user/avatarUpload',
          filePath:  e.detail.avatarUrl,
          name: 'file',
          formData: {
            userId: this.userInfo.id,
          },
          header: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${access_token}`,
          },
          success: res => {
            // 注意：这里返回的res.data类型是string  需要自己去转换为JSON
            let data = JSON.parse(res.data);
            console.log("修改后的data;;;",data)
            if (data.code === 200) {
              this.userInfo.avatar = data.url;
              uni.showToast({
                title:"保存成功！",
              })
            }
          },
          fail: (error) => {
            uni.showToast({
              title: error,
              duration: 2000
            });
          },
          complete: () => {
            uni.hideLoading();
          }
        });
      },
      updateUserName(e){
        console.log("修改用户昵称时的参数信息：：",e)
        this.userInfo.nickName = e.detail.value
      },
      // 更新用户信息
      submitUserInfo() {
        console.log("更新用户信息::",this.userInfo)

        // 判断是否已经选择了用户头像和输入了用户昵称
        if (!this.userInfo.avatar || !this.userInfo.nickName) {
          return uni.showToast({
            icon: 'none',
            title: '请选择头像和输入用户信息'
          })
        }
        this.$api.updateUser(this.userInfo).then(res =>{
          console.log("返回的信息：：",res)
          if (res.code === 200) {
            this.userInfo = res.data;
            uni.setStorageSync('userInfo', null)
            uni.setStorageSync('userInfo', res.data)
            uni.showToast({
              title:"保存成功！",
            })
            this.closeModal();
          }

        })
        // 更新完成事件
        this.$emit('updated', this.userInfo)
        
      },
      
      // 关闭弹框
      closeModal() {
        this.$emit('input', false)
      },
      
      
    }
  }
</script>

<style lang="scss" scoped>
  
  @import '../../static/css/iconfont.css';
  
  .wx-authorization-modal {
    position: fixed;
    left: 0;
    top: 0;
    width: 100vw;
    height: 100vh;
    z-index: 99998;
    
    view {
      box-sizing: border-box;
    }
    
    .image {
      width: 100%;
      height: 100%;
      border-radius: inherit;
    }
    
    .wam {
      /* mask */
      &__mask {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        opacity: 0;
        animation: showMask 0.25s ease 0.1s forwards;
      }
      
      /* close-btn */
      &__close-btn {
        position: absolute;
        top: 30rpx;
        right: 30rpx;
        z-index: 99999;
      }
      
      /* wrapper */
      &__wrapper {
        position: absolute;
        left: 0;
        bottom: 0;
        width: 100%;
        background-color: #FFFFFF;
        border-radius: 20rpx 20rpx 0rpx 0rpx;
        padding: 40rpx;
        padding-top: 60rpx;
        padding-bottom: 40rpx;
        padding-bottom: calc(constant(safe-area-inset-bottom) + 40rpx);
        padding-bottom: calc(env(safe-area-inset-bottom) + 40rpx);
        transform-origin: center bottom;
        transform: scaleY(0);
        animation: showWrapper 0.25s ease 0.1s forwards;
        z-index: 99999;
      }
      
      /* title */
      &__title {
        font-size: 34rpx;
      }
      /* sub-title */
      &__sub-title {
        font-size: 26rpx;
        color: #AAAAAA;
        margin-top: 16rpx;
        padding-bottom: 30rpx;
      }
      
      /* 头像选择 */
      &__avatar {
        width: 100%;
        margin-top: 30rpx;
        display: flex;
        align-items: center;
        justify-content: center;
        
        .button-shadow {
          border: 8rpx solid rgba(255,255,255,0.05);
          box-shadow: 0rpx 0rpx 80rpx 0rpx rgba(0, 0, 0, 0.15);
          border-radius: 50%;
        }
        
        .button {
          position: relative;
          width: 160rpx;
          height: 160rpx;
          border-radius: 50%;
          overflow: visible;
          background-image: repeating-linear-gradient(45deg, #E4E9EC, #F8F7F8);
          color: #FFFFFF;
          background-color: transparent;
          padding: 0;
          margin: 0;
          font-size: inherit;
          line-height: inherit;
          border: none;
          &::after {
            border: none;
          }
        }
        
        .avatar {
          &__empty, &__image {
            width: 100%;
            height: 100%;
            border-radius: inherit;
          }
          
          &--icon {
            position: absolute;
            right: -10rpx;
            bottom: -6rpx;
            width: 60rpx;
            height: 60rpx;
            // transform: translate(50%, 50%);
            background-color: #1D2541;
            color: #FFFFFF;
            border-radius: 50%;
            border: 6rpx solid #FFFFFF;
            line-height: 1;
            font-size: 36rpx;
            display: flex;
            align-items: center;
            justify-content: center;
          }
        }
      }
      
      /* 昵称 */
      &__nickname {
        margin-top: 40rpx;
        
        .nickname {
          
          &__data {
            margin-top: 16rpx;
            width: 100%;
            padding: 26rpx 20rpx;
            border-radius: 10rpx;
            background-color: #F8F7F8;
            
            .input {
              color: #080808;
            }
          }
        }
      }
      
      /* 保存按钮 */
      &__submit-btn {
        width: 100%;
        background-color: #05C160;
        color: #FFFFFF;
        margin-top: 60rpx;
        border-radius: 10rpx;
        padding: 25rpx;
        font-size: 32rpx;
        display: flex;
        align-items: center;
        justify-content: center;
        
        &.disabled {
          background-color: #E6E6E6;
        }
      }
      
      
    }
  }
  
  .tn-btn-hover-class {
    box-shadow: inset 10rpx 2rpx 40rpx 0rpx rgba(0, 0, 0, 0.05);
  }
  
  @keyframes showMask {
    0% {
      opacity: 0;
    }
    100% {
      opacity: 1;
    }
  }
  @keyframes showWrapper {
    0% {
      transform: scaleY(0);
    }
    100% {
      transform: scaleY(1);
    }
  }
</style>