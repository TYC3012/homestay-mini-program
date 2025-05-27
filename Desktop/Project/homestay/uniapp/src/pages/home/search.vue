<template>
  <view class="tn-safe-area-inset-bottom">


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

    <view class="" style="margin-top: 160rpx;" :style="{paddingTop: vuex_custom_bar_height + 'px'}">
      <view class="tn-flex tn-flex-row-between tn-margin" >
        <view class="justify-content-item tn-text-bold">
          <text class="tn-text-df tn-color-black">最近搜索</text>
        </view>
        <view class="justify-content-item tn-text-df tn-color-grey">
          <text class="tn-padding-xs">删除</text>
          <text class="tn-icon-delete"></text>
        </view>
      </view>
    </view>

    <view class="">
      <view class="tn-tag-search tn-margin tn-text-justify">
        <view v-for="(item, index) in tagList" :key="index" class="tn-tag-search__item tn-margin-right tn-round tn-text-sm tn-bg-gray--light tn-color-gray">
          <text class="tn-tag-search__item--prefix">#</text> {{ item.title }}
        </view>
      </view>
    </view>

    <view class="tn-flex tn-flex-row-between tn-padding-top-xl tn-margin tn-padding-bottom">
      <view class="justify-content-item tn-text-bold">
        <text class="tn-text-df tn-color-black">搜索结果</text>
      </view>
      <view class="justify-content-item tn-text-df tn-color-grey">
        <text class="tn-padding-xs">筛选</text>
        <text class="tn-icon-filter"></text>
      </view>
    </view>


    <!-- 不建议写时间，因为写了时间，你就要经常更新文章了鸭-->
    <view class="">
      <view class="tn-margin-bottom-lg">
        <block v-for="(item, index) in roomList" :key="index">
          <view class="article-shadow tn-margin"  @click="tn('/homePages/listDetail',item.id)">
            <view class="tn-flex">

              <view class="tn-margin-sm tn-padding-top-xs" style="width: 100%;">
                <view class="tn-text-lg tn-text-bold clamp-text-1 tn-text-justify">
                  <text class="">{{ item.hotelName }}</text>
                </view>
                <view class="tn-padding-top-xs" style="min-height: 90rpx;">
                  <text class="tn-text-df tn-color-gray clamp-text-2 tn-text-justify">
                    {{ item.roomName }}
                  </text>
                </view>
                <view class="tn-flex tn-flex-row-between tn-flex-col-between tn-margin-top-sm">
                  <view v-for="(label_item,label_index) in item.label" :key="label_index"
                        class="justify-content-item tn-tag-content__item tn-margin-right tn-text-sm tn-text-bold">
                    <text class="tn-tag-content__item--prefix">#</text> {{ label_item }}
                  </view>
                  <view class="justify-content-item tn-color-gray tn-text-center" style="padding-top: 5rpx;">
                    <text class="tn-icon-fire tn-text-lg" style="padding-right: 5rpx;"></text>
                    <text class="tn-padding-right tn-text-df">{{ item.collectionCount }}</text>
                    <text class="tn-icon-like-lack tn-text-lg" style="padding-right: 5rpx;"></text>
                    <text class="tn-text-df">{{ item.likeCount }}</text>
                  </view>
                </view>
              </view>
              <view class="image-pic tn-margin-sm" :style="'background-image:url(' + imageUrl+item.mainImg + ')'">
                <view class="image-article">
                </view>
              </view>
            </view>
          </view>
        </block>

      </view>

    </view>


  </view>
</template>

<script>
import template_page_mixin from '@/libs/mixin/template_page_mixin.js'
import {userSearchAdd} from "../../api/modules/user";
export default {
  name: 'TemplateSearch',
  mixins: [template_page_mixin],
  data(){
    return {
      inputValue: '',
      userInfo:uni.getStorageSync('userInfo'),
      tagList: [
        {
          color: 'red',
          title: "简约",
        },
        {
          color: 'cyan',
          title: "UI设计",
        },
        {
          color: 'blue',
          title: "vue3",
        },
        {
          color: 'green',
          title: "神器",
        },
        {
          color: 'orange',
          title: "酷炫例子",
        },
        {
          color: 'purple',
          title: "开发工具",
        },
        {
          color: 'brown',
          title: "不许凶我",
        }
      ],
      roomList: [],
      imageUrl:this.websiteUrl,
    }
  },
  created(){
    console.log("api::",this.websiteUrl)
    if(typeof this.userInfo =="string"){
      this.userInfo=JSON.parse(this.userInfo);
    }
    //获取房间信息
    this.getRoomList();
    //获取用户搜索记录
    this.getUserSearchList();
  },
  methods: {
    // 跳转
    // 跳转
    tn(e,data) {
      uni.navigateTo({
        url: e + "?id="+data,
      });
    },
    getRoomList(searchText){
      this.$api.getRoomList({searchText:searchText}).then(async res => {
        console.log("房间列表信息：：",res)
        if(res.code === 200){
          this.roomList=res.rows;
        }
      })
    },
    getUserSearchList(){
      console.log("当前登陆者信息：：",this.userInfo)
      console.log("搜索的内容：：",this.inputValue)
      let _this = this;
      this.$api.getUserSearchList({userId: this.userInfo.id}).then(res =>{
        console.log("res::",res)
        let tagList=[];
        res.rows.forEach(ele=>{
          ele.title=ele.searchText;
          tagList.push(ele);
        });
        _this.tagList=tagList;
        _this.getRoomList(this.inputValue);
      })
    },
    userSearch(e){
      console.log("搜索的内容：：",this.inputValue)
      console.log("当前登陆者信息：：",this.userInfo)
      let params={};
      params.userId=this.userInfo.id;
      params.userName=this.userInfo.nickName;
      params.searchText=this.inputValue;
      let _this = this;
      _this.$api.userSearchAdd(params).then(res =>{
        console.log("返回的信息：：",res)

        _this.getUserSearchList();


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
