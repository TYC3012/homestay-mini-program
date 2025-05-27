<template>
  <view class="tn-safe-area-inset-bottom">
    <!-- 顶部自定义导航 -->
    <tn-nav-bar fixed alpha customBack>
      <view slot="back" class='tn-custom-nav-bar__back'
            @click="goBack">
        <text class='icon tn-icon-left'></text>
        <text class='icon tn-icon-home-capsule-fill'></text>
      </view>
    </tn-nav-bar>

    <view class="tn-search-fixed" style="background: linear-gradient(180deg, #D8E5FF, #FFFFFF);">
      <view class="tn-flex tn-flex-row-between tn-flex-col-center tn-margin" :style="{paddingTop: vuex_custom_bar_height + 'px'}">

        <view class="justify-content-item align-content-item" style="width: 100vw;">
          <view class="tn-flex tn-flex-col-center" style="border: 1rpx solid #3668fc;border-radius: 100rpx;padding: 10rpx 20rpx 10rpx 20rpx;width: 90%;">
            <text class="tn-icon-search justify-content-item tn-padding-right-xs tn-color-gray tn-text-lg"></text>
            <input @input="onInput" class="justify-content-item" placeholder="想搜点什么咧" name="input" placeholder-style="color:#AAAAAA" style="width: 90%;"></input>
          </view>
        </view>

        <view class="align-content-item">
          <view class="justify-content-item tn-text-center">
            <tn-button backgroundColor="#3668fc" shape="round" padding="20rpx 20rpx" width="150rpx" @tap="userSearch">
              <text class="tn-color-white">搜 索</text>
            </tn-button>

          </view>
        </view>
      </view>
    </view>




    <!-- 不建议写时间，因为写了时间，你就要经常更新文章了鸭-->
    <view style="margin-top: 8rem;">
      <!-- 房源信息   -->
      <view>
        <room-list :list="roomList"></room-list>
      </view>
    </view>


  </view>
</template>

<script>
import template_page_mixin from '@/libs/mixin/template_page_mixin.js'
import {userSearchAdd} from "../api/modules/user";
import RoomList from '@/components/room/list.vue'
export default {
  name: 'TemplateSearch',
  components: {RoomList},
  mixins: [template_page_mixin],
  data(){
    return {
      inputValue: '',
      userInfo:uni.getStorageSync('userInfo') ,
      tagList: [],
      roomList: [],
    }
  },
  onLoad(data) {
    console.log("房间列表界面：：",data)
    this.getRoomList(data.id);
  },
  methods: {
    // 跳转
    tn(e) {
      uni.navigateTo({
        url: e,
      });
    },
    getRoomList(hotelId){
      this.$api.getRoomList({hotelId:hotelId}).then(async res => {
        console.log(res)
        if(res.code === 200){
          this.roomList=res.rows;
        }
      })
    },

    //获取input文本框中的输入值
    onInput(e){
      this.inputValue = e.target.value
    },
  }
}
</script>

<style lang="scss" scoped>
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

.tn-search-fixed{
  position: fixed;
  top: 0rpx;
  width: 100%;
  transition: all 0.25s ease-out;
  z-index: 1;
}


/* 搜索标签 start*/
.tn-tag-search {
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




/* 资讯主图 start*/
.image-article {
  border-radius: 8rpx;
  width: 200rpx;
  height: 200rpx;
  position: relative;
}

.image-pic{
  border: 1rpx solid #F8F7F8;
  background-size: cover;
  background-repeat:no-repeat;
  // background-attachment:fixed;
  background-position:top;
  border-radius: 10rpx;
}

.article-shadow {
  border-radius: 15rpx;
  box-shadow: 0rpx 0rpx 50rpx 0rpx rgba(0, 0, 0, 0.07);
}

/* 文字截取*/
.clamp-text-1 {
  -webkit-line-clamp: 1;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  overflow: hidden;
}

.clamp-text-2 {
  -webkit-line-clamp: 2;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  overflow: hidden;
}

/* 标签内容 start*/
.tn-tag-content {
  &__item {
    display: inline-block;
    line-height: 35rpx;
    color: #1D2541;
    background-color: #F3F2F7;
    border-radius: 10rpx;
    font-size: 22rpx;
    padding: 5rpx 15rpx;

    &--prefix {
      padding-right: 10rpx;
    }
  }
}


/* 大单图 start*/
.tn-blogger-content2 {
  &__wrap {
    padding: 30rpx;
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
      padding: 0 20rpx;
      margin: 5rpx 18rpx 0 0;

      &--prefix {
        color: #00FFC8;
        padding-right: 10rpx;
      }
    }

    &__desc {
      line-height: 55rpx;
    }
  }

  &__main-image {
    box-shadow: 0rpx 5rpx 40rpx 0rpx rgba(43, 158, 246, 0.2);
    border-radius: 16rpx;

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
    font-size: 40rpx;
    padding-right: 5rpx;
  }
}

.image-design{
  padding: 160rpx 0rpx;
  font-size: 40rpx;
  font-weight: 300;
  position: relative;
}
.image-pic{
  background-size: cover;
  background-repeat:no-repeat;
  // background-attachment:fixed;
  background-position:top;
  border-radius: 10rpx;
}
/* 文章内容 end*/
</style>
