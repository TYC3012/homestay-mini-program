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
            <input class="justify-content-item" placeholder="想搜点什么咧" name="input" placeholder-style="color:#AAAAAA" style="width: 90%;"></input>
          </view>
        </view>

        <view class="align-content-item">
          <view class="justify-content-item tn-text-center">
            <tn-button backgroundColor="#3668fc" shape="round" padding="20rpx 20rpx" width="150rpx" @tap="">
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
        <block v-for="(item, index) in news" :key="index">
          <view class="article-shadow tn-margin" @click="tn('/pageA/news/news')">
            <view class="tn-flex">

              <view class="tn-margin-sm tn-padding-top-xs" style="width: 100%;">
                <view class="tn-text-lg tn-text-bold clamp-text-1 tn-text-justify">
                  <text class="">{{ item.title }}</text>
                </view>
                <view class="tn-padding-top-xs" style="min-height: 90rpx;">
                  <text class="tn-text-df tn-color-gray clamp-text-2 tn-text-justify">
                    {{ item.desc }}
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
              <view class="image-pic tn-margin-sm" :style="'background-image:url(' + item.userAvatar + ')'">
                <view class="image-article">
                </view>
              </view>
            </view>
          </view>
        </block>

      </view>



      <!-- 单图大图文 -->
      <view class="">

        <block v-for="(item,index) in news2" :key="index">
          <view class="article-shadow tn-margin tn-padding-sm" @click="tn('/pageA/news/news')">
            <view class="image-pic" :style="'background-image:url(' + item.userAvatar + ')'">
              <view class="image-design">
              </view>
            </view>
            <view class="tn-margin-top tn-padding-bottom-sm">
              <view class="tn-text-lg tn-text-bold clamp-text-1 tn-text-justify">
                <text class="">{{ item.title }}</text>
              </view>
              <!-- <view class="tn-padding-top-xs" style="min-height: 90rpx;">
                <text class="tn-text-df tn-color-gray clamp-text-2 tn-text-justify">
                  {{ item.desc }}
                </text>
              </view> -->
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
          </view>

        </block>
      </view>

      <!-- 三图图文 -->
      <view class="">
        <block v-for="(item,index) in news3" :key="index">
          <view class="article-shadow tn-margin" @click="tn('/pageA/news/news')">
            <view class="" style="padding: 15rpx 10rpx 0 10rpx;">
              <tn-grid  hoverClass="none" :col="3">
                <block v-for="(image_item,image_index) in item.mainImage" :key="image_index">
                  <view class="" style="width: 33.3%;">
                    <view class="image-pic tn-margin-xs" :style="'background-image:url(' + image_item + ')'">
                      <view class="image-article">
                      </view>
                    </view>
                    <!-- <image
                      class="blogger__main-image blogger__main-image--3"
                      :src="image_item"
                      mode="aspectFill"
                    ></image> -->
                  </view>
                </block>
              </tn-grid>
            </view>


            <view class="tn-margin-sm tn-padding-bottom-sm">
              <view class="tn-text-lg tn-text-bold clamp-text-1 tn-text-justify">
                <text class="">{{ item.title }}</text>
              </view>
              <!-- <view class="tn-padding-top-xs" style="min-height: 90rpx;">
                <text class="tn-text-df tn-color-gray clamp-text-2 tn-text-justify">
                  {{ item.desc }}
                </text>
              </view> -->
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
          </view>

        </block>
      </view>


      <!-- 不建议写时间，因为写了时间，你就要经常更新文章了鸭-->
      <view class="tn-margin-bottom-lg">
        <block v-for="(item, index) in news4" :key="index">
          <view class="article-shadow tn-margin" @click="tn('/pageA/news/news')">
            <view class="tn-flex">

              <view class="tn-margin-sm tn-padding-top-xs" style="width: 100%;">
                <view class="tn-text-lg tn-text-bold clamp-text-1 tn-text-justify">
                  <text class="">{{ item.title }}</text>
                </view>
                <view class="tn-padding-top-xs" style="min-height: 90rpx;">
                  <text class="tn-text-df tn-color-gray clamp-text-2 tn-text-justify">
                    {{ item.desc }}
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
              <view class="image-pic tn-margin-sm" :style="'background-image:url(' + item.userAvatar + ')'">
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
export default {
  name: 'TemplateSearch',
  mixins: [template_page_mixin],
  data(){
    return {
      inputValue: '',

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
      news4: [{
        userAvatar: 'https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg',
        userName: '可我会像',
        date: '2021年12月20日',
        color: 'blue',
        label: ['UI设计'],
        title: '为什么资讯不显示时间？',
        desc: '你确定你经常更新文章吗？',
        mainImage: 'https://resource.tuniaokj.com/images/shop/computer2.jpg',
        viewUser: {
          latestUserAvatar: [{
            src: 'https://resource.tuniaokj.com/images/blogger/avatar_1.jpeg'
          },
            {
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg'
            },
            {
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_3.jpeg'
            },
            {
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_4.jpeg'
            },
          ],
          viewUserCount: 321
        },
        collectionCount: 654,
        commentCount: 232,
        likeCount: 543
      },
        {
          userAvatar: 'https://resource.tuniaokj.com/images/blogger/avatar_3.jpeg',
          userName: '可我会像',
          date: '2021年12月20日',
          color: 'green',
          label: ['创意'],
          title: '创意一点点',
          desc: '创意灵感从这里开始',
          mainImage: 'https://resource.tuniaokj.com/images/shop/phonecase1.jpg',
          viewUser: {
            latestUserAvatar: [{
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_1.jpeg'
            },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg'
              },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_3.jpeg'
              },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_4.jpeg'
              },
            ],
            viewUserCount: 230
          },
          collectionCount: 987,
          commentCount: 236,
          likeCount: 342
        },
        {
          userAvatar: 'https://resource.tuniaokj.com/images/shop/phonecase2.jpg',
          userName: '可我会像',
          date: '2021年12月20日',
          color: 'orange',
          label: ['UI设计'],
          title: '图鸟UI素材已上传语雀',
          desc: '语雀素材地址资源',
          mainImage: 'https://resource.tuniaokj.com/images/shop/phonecase2.jpg',
          viewUser: {
            latestUserAvatar: [{
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_1.jpeg'
            },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg'
              },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_3.jpeg'
              },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_4.jpeg'
              },
            ],
            viewUserCount: 106
          },
          collectionCount: 765,
          commentCount: 32,
          likeCount: 91
        },
        {
          userAvatar: 'https://resource.tuniaokj.com/images/shop/watch1.jpg',
          userName: '可我会像',
          date: '2021年12月20日',
          color: 'purplered',
          label: ['神器'],
          title: '最强的视频转GIF工具',
          desc: '神器推荐',
          mainImage: 'https://resource.tuniaokj.com/images/shop/watch1.jpg',
          viewUser: {
            latestUserAvatar: [{
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_1.jpeg'
            },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg'
              },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_3.jpeg'
              },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_4.jpeg'
              },
            ],
            viewUserCount: 232
          },
          collectionCount: 776,
          commentCount: 48,
          likeCount: 86
        },
        {
          userAvatar: 'https://resource.tuniaokj.com/images/shop/sticker.jpg',
          userName: '可我会像',
          date: '2021年12月20日',
          color: 'purple',
          label: ['粒子'],
          title: '酷炫的小程序粒子效果一览',
          desc: '小程序粒子效果',
          mainImage: 'https://resource.tuniaokj.com/images/shop/sticker.jpg',
          viewUser: {
            latestUserAvatar: [{
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_1.jpeg'
            },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg'
              },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_3.jpeg'
              },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_4.jpeg'
              },
            ],
            viewUserCount: 456
          },
          collectionCount: 342,
          commentCount: 42,
          likeCount: 76
        },
        {
          userAvatar: 'https://resource.tuniaokj.com/images/blogger/avatar_1.jpeg',
          userName: '可我会像',
          date: '2021年12月20日',
          color: 'brown',
          label: ['工具'],
          title: '小程序任意页面生成二维码',
          desc: '二维码生成器',
          mainImage: 'https://resource.tuniaokj.com/images/shop/card.jpg',
          viewUser: {
            latestUserAvatar: [{
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_1.jpeg'
            },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg'
              },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_3.jpeg'
              },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_4.jpeg'
              },
            ],
            viewUserCount: 129
          },
          collectionCount: 265,
          commentCount: 22,
          likeCount: 62
        }
      ],

      news3: [{
        userAvatar: 'https://resource.tuniaokj.com/images/blogger/avatar_4.jpeg',
        userName: '可我会像',
        date: '2021年12月20日',
        color: 'red',
        label: ['小程序'],
        title: '我不喜欢带伞，因为雨滴从来不滴落我手心上',
        desc: '小程序前端源码，欢迎白嫖嗷嗷，可以的话，插件市场三连支持一下aa',
        mainImage:[
          'https://resource.tuniaokj.com/images/simple/image0.jpg',
          'https://resource.tuniaokj.com/images/simple/logo-flower.jpg',
          'https://resource.tuniaokj.com/images/simple/banner0.jpg',
        ],
        viewUser: {
          latestUserAvatar: [{
            src: 'https://resource.tuniaokj.com/images/blogger/avatar_1.jpeg'
          },
            {
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg'
            },
            {
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_3.jpeg'
            },
            {
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_4.jpeg'
            },
          ],
          viewUserCount: 567
        },
        collectionCount: 543,
        commentCount: 543,
        likeCount: 206
      }
      ],

      news2: [{
        userAvatar: 'https://resource.tuniaokj.com/images/blogger/avatar_4.jpeg',
        userName: '可我会像',
        date: '2021年12月20日',
        color: 'red',
        label: ['小程序'],
        title: '小程序官网源码，已上线',
        desc: '小程序前端源码，欢迎白嫖嗷嗷，可以的话，插件市场三连支持一下aa',
        mainImage: 'https://resource.tuniaokj.com/images/shop/prototype2.jpg',
        viewUser: {
          latestUserAvatar: [{
            src: 'https://resource.tuniaokj.com/images/blogger/avatar_1.jpeg'
          },
            {
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg'
            },
            {
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_3.jpeg'
            },
            {
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_4.jpeg'
            },
          ],
          viewUserCount: 567
        },
        collectionCount: 543,
        commentCount: 543,
        likeCount: 206
      }
      ],

      news: [{
        userAvatar: 'https://resource.tuniaokj.com/images/blogger/blogger_beibei.jpg',
        userName: '可我会像',
        date: '2021年12月20日',
        color: 'cyan',
        label: ['模型'],
        title: '一个拥有大量3D模型的网站',
        desc: '3D模型了解一下？',
        mainImage: 'https://resource.tuniaokj.com/images/shop/prototype1.jpg',
        viewUser: {
          latestUserAvatar: [{
            src: 'https://resource.tuniaokj.com/images/blogger/avatar_1.jpeg'
          },
            {
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg'
            },
            {
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_3.jpeg'
            },
            {
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_4.jpeg'
            },
          ],
          viewUserCount: 987
        },
        collectionCount: 567,
        commentCount: 69,
        likeCount: 65
      },
        {
          userAvatar: 'https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg',
          userName: '可我会像',
          date: '2021年12月20日',
          color: 'blue',
          label: ['UI设计'],
          title: '为什么资讯不显示时间？',
          desc: '你确定你经常更新文章吗？',
          mainImage: 'https://resource.tuniaokj.com/images/shop/computer2.jpg',
          viewUser: {
            latestUserAvatar: [{
              src: 'https://resource.tuniaokj.com/images/blogger/avatar_1.jpeg'
            },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg'
              },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_3.jpeg'
              },
              {
                src: 'https://resource.tuniaokj.com/images/blogger/avatar_4.jpeg'
              },
            ],
            viewUserCount: 321
          },
          collectionCount: 654,
          commentCount: 232,
          likeCount: 543
        }
      ],
    }
  },
  methods: {
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
