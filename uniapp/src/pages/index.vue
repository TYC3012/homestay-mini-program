<template>
  <view class="start-index">
    <view v-if="tabberPageLoadFlag[0]" :style="{display: currentIndex === 0 ? '' : 'none'}">
      <scroll-view class="custom-tabbar-page" scroll-y enable-back-to-top @scrolltolower="tabbarPageScrollLower">
       <home ref="home"></home>
      </scroll-view>
    </view>
    <view v-if="tabberPageLoadFlag[1]" :style="{display: currentIndex === 1 ? '' : 'none'}">
      <scroll-view class="custom-tabbar-page" scroll-y enable-back-to-top @scrolltolower="tabbarPageScrollLower">
<!--        <circle-page ref="circleRef"></circle-page>-->
        <search ref="search"></search>
      </scroll-view>
    </view>
    <view v-if="tabberPageLoadFlag[2]" :style="{display: currentIndex === 2 ? '' : 'none'}">
      <scroll-view class="custom-tabbar-page" scroll-y enable-back-to-top @scrolltolower="tabbarPageScrollLower">
        <my ref="my"></my>
      </scroll-view>
    </view>
    
    <tn-tabbar
        v-model="currentIndex"
        :list="tabbarList"
        activeColor="#3668fc"
        inactiveColor="#AAAAAA"
        activeIconColor="#3668fc"
        :animation="true"
        :safeAreaInsetBottom="true"
        @change="switchTabbar"
    ></tn-tabbar>
  </view>
</template>
<script>
import Home from './home/home.vue'
import Search from './home/search.vue'
import My from './my/my.vue'
export default {
  components:{
    Home,
    Search,
    My
  },
  data() {
    return {
      // 底部tabbar菜单数据
      tabbarList: [
        {
          title: '首页',
          activeIcon: 'https://blog-1253935418.cos.ap-guangzhou.myqcloud.com/cloud/tabbar/home_tnnew.png',
          inactiveIcon: 'https://blog-1253935418.cos.ap-guangzhou.myqcloud.com/cloud/tabbar/home_tn.png'
        },

        {
          title: '搜索',
          activeIcon: 'https://blog-1253935418.cos.ap-guangzhou.myqcloud.com/cloud/tabbar/pay_tnnew.png',
          inactiveIcon: 'https://blog-1253935418.cos.ap-guangzhou.myqcloud.com/cloud/tabbar/pay_tn.png',
        },
        {
          title: '我的',
          activeIcon: 'https://blog-1253935418.cos.ap-guangzhou.myqcloud.com/cloud/tabbar/my_tnnew.png',
          inactiveIcon: 'https://blog-1253935418.cos.ap-guangzhou.myqcloud.com/cloud/tabbar/my_tn.png'
        }
      ],
      // tabbar当前被选中的序号
      currentIndex: 0,
      // 自定义底栏对应页面的加载情况
      tabberPageLoadFlag: []
    }
  },
  onLoad(options) {
    this.wxHandleLogin();
    const index = Number(options.index || 0)
    // 根据底部tabbar菜单列表设置对应页面的加载情况
    this.tabberPageLoadFlag = this.tabbarList.map((item, tabbar_index) => {
      return index === tabbar_index
    })
    this.switchTabbar(index)
  },
  methods: {
    wxHandleLogin(){
      let _this = this;
      uni.login({
        provider: 'weixin', //使用微信登录
        success: function (loginRes) {
          //console.log("loginRes::",loginRes)
          _this.$api.login({code: loginRes.code,jscode:loginRes.code,phoneCode: loginRes.code}).then(res =>{

            console.log("登录之后返回的信息：：",res)
            // 设置缓存数据
            uni.setStorageSync('access_token', res.data.token)
            uni.setStorageSync('userInfo', res.data.userInfo)
            _this.userInfo = res.data.userInfo;
            _this.isLogin = true;
            /*_this.$refs.toast.show({
              title: '登录成功',
              icon: 'success',
              image: '',
              duration: 1500
            })*/
          })
        }
      });
    },
    // 切换导航
    switchTabbar(index) {
      this._switchTabbarPage(index)
      if (index !== 1) {
        this.$refs?.circleRef?.stopAllVideo()
      }
    },


    // 导航页面滚动到底部
    tabbarPageScrollLower(e) {
      if (this.currentIndex === 3) {
        this.$refs.preferredRef.getRandomData && this.$refs.preferredRef.getRandomData()
      }
    },

    // 切换导航页面
    _switchTabbarPage(index) {
      const selectPageFlag = this.tabberPageLoadFlag[index]
      if (selectPageFlag === undefined) {
        return
      }
      if (selectPageFlag === false) {
        this.tabberPageLoadFlag[index] = true
      }
      this.currentIndex = index
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
