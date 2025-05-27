<template>
  <view class="template-set">
    <!-- 顶部自定义导航 -->
    <tn-nav-bar fixed alpha customBack>
      <view slot="back" class='tn-custom-nav-bar__back'
        @click="goBack">
        <text class='icon tn-icon-left-arrow'></text>
      </view>
    </tn-nav-bar>

    <view class="tn-margin-top" :style="{paddingTop: vuex_custom_bar_height + 'px'}">
      
      
      <view class="tn-flex tn-flex-row-between tn-strip-bottom-min tn-padding" @click="tn('/minePages/avatar')">
        <view class="justify-content-item">
          <view class="tn-text-bold tn-text-lg">
            用户头像
          </view>
          <view class="tn-color-gray tn-padding-top-xs">
            有趣的头像，百里挑一
          </view>
        </view>
        <view class="justify-content-item tn-text-lg tn-color-grey">
          <view class="logo-pic tn-shadow">
            <view class="logo-image">
              <view class="tn-shadow-blur" :style="'background-image:url(' + imageUrl+userInfo.avatar + ');width: 110rpx;height: 110rpx;background-size: cover;'">
              </view>
            </view>
          </view>
        </view>
      </view>
      
      <view class="tn-flex tn-flex-row-between tn-strip-bottom-min tn-padding">
        <view class="justify-content-item">
          <view class="tn-text-bold tn-text-lg">
            用户昵称
          </view>
          <view class="tn-color-gray tn-padding-top-xs">
            {{userInfo.nickName}}
          </view>
        </view>
      </view>
      
      <view class="tn-flex tn-flex-row-between tn-strip-bottom tn-padding" @click="showModal2">
        <view class="justify-content-item">
          <view class="tn-text-bold tn-text-lg">
            绑定手机号
          </view>
          <view class="tn-color-gray tn-padding-top-xs">
            {{userInfo.phone}}
          </view>
        </view>
        <view class="justify-content-item tn-text-lg tn-color-grey">
          <view class="tn-icon-right tn-padding-top"></view>
        </view>
      </view>
      
      <view class="tn-flex tn-flex-row-between tn-strip-bottom-min tn-padding">
        <view class="justify-content-item">
          <view class="tn-text-bold tn-text-lg">
            姓名
          </view>
          <view class="tn-color-gray tn-padding-top-xs">
            <tn-input v-model="userInfo.name" type="text"  placeholder="请输入真实姓名"></tn-input>
          </view>
        </view>
      </view>
      <picker @change="bindPickerChange" :value="index" :range="array">
        <view class="tn-flex tn-flex-row-between tn-strip-bottom-min tn-padding" >
          <view class="justify-content-item">
            <view class="tn-text-bold tn-text-lg">
              性别
            </view>
            <view class="tn-color-gray tn-padding-top-xs">
              <view class="tn-color-gray">{{array[index]}}</view>
            </view>
          </view>
          <view class="justify-content-item tn-text-lg tn-color-grey">
            <view class="tn-icon-right tn-padding-top"></view>
          </view>
        </view>
      </picker>
<!--      <picker @change="bindDateChange" mode="date" :value="date" :start="startDate" :end="endDate">-->
<!--        <view class="tn-flex tn-flex-row-between tn-strip-bottom-min tn-padding">-->
<!--          <view class="justify-content-item">-->
<!--            <view class="tn-text-bold tn-text-lg">-->
<!--              生日-->
<!--            </view>-->
<!--            <view class="tn-color-gray tn-padding-top-xs">-->
<!--              {{date}}-->
<!--            </view>-->
<!--          </view>-->
<!--          <view class="justify-content-item tn-text-lg tn-color-grey">-->
<!--            <view class="tn-icon-right tn-padding-top"></view>-->
<!--          </view>-->
<!--        </view>-->
<!--      </picker>-->

      <view class="tn-flex tn-flex-row-between tn-strip-bottom-min tn-padding" @click="showModal3">
        <view class="justify-content-item">
          <view class="tn-text-bold tn-text-lg">
            民宿
          </view>
          <tn-select v-model="bossShow" mode="single" :list="list" @confirm="confirm"></tn-select>
          <view class="tn-color-gray tn-padding-top-xs">
            {{userInfo.hotelName}}
          </view>
        </view>
        <view class="justify-content-item tn-text-lg tn-color-grey">
          <view class="tn-icon-right tn-padding-top"></view>
        </view>
      </view>

      <tn-button backgroundColor="#01BEFF" fontColor="#FFFFFF" width="90%" style="margin-left: 35rpx;margin-top: 10rpx;" @click="submit">保存</tn-button>

      
      <tn-modal v-model="show2" :custom="true" :showCloseBtn="true">
        <view class="custom-modal-content">
          <view class="">
            <view class="tn-text-lg tn-text-bold tn-color-purplered tn-text-center tn-padding">变更手机号码</view>
            <view class="tn-bg-gray--light tn-color-gray" style="border-radius: 10rpx;padding: 20rpx 30rpx;margin: 50rpx 0 60rpx 0;">
              {{userInfo.phone}}
            </view>
          </view>
          <view class="tn-flex-1 justify-content-item tn-margin-sm tn-text-center">
            <tn-button  shape="round" backgroundColor="#f0f6ff" fontColor="#00000" shadow :openType="'getPhoneNumber'" @getphonenumber="decryptPhoneNumber">
              <text class="tn-text-lg tn-text-bold">点击修改</text>
            </tn-button>
          </view>
        </view>
      </tn-modal>




    </view>
  </view>
</template>

<script>
  import template_page_mixin from '@/libs/mixin/template_page_mixin.js'
  export default {
    name: 'TemplateSet',
    mixins: [template_page_mixin],
    data(){
      return {
        show1: false,
        show2: false,
        show3: false,
        index: 0,
        array: ['女', '男', '保密'],
        date: '2005-01-29',
        index1: 1,
        userInfo:uni.getStorageSync('userInfo'),
        imageUrl:this.websiteUrl,
        hotelList:[],
        bossShow: false,
        show:true,
        list: [],
      }
    },
    created() {
      if(typeof this.userInfo =="string"){
        this.userInfo=JSON.parse(this.userInfo);
      }
      this.userInfo.hotelName=this.userInfo.hotel.homeName;
      console.log("当前登陆者信息：：",this.userInfo)
      this.index=this.userInfo.gender;
      this.getHotelList();
    },
    computed: {
        startDate() {
            return this.getDate('start');
        },
        endDate() {
            return this.getDate('end');
        }
    },
    methods: {
      // 跳转
      tn(e) {
      	uni.navigateTo({
      		url: e,
      	});
      },
      getHotelList(){
        this.$api.getHouseList({}).then(async res => {
          //console.log(res)
          if(res.code === 200){
            this.hotelList=res.rows;
            let list=[];
            this.hotelList.forEach(ele=>{
              let element={};
              element.value=ele.id;
              element.label=ele.homeName;
              list.push(element);
            })
            this.list=list;
            //console.log(this.list)
          }
        })
      },
      // 弹出模态框
      showModal2(event) {
        this.openModal2()
      },
      // 打开模态框
      openModal2() {
        this.show2 = true
      },
     bindPickerChange: function(e) {
        this.index = e.detail.value
      },
      // 弹出模态框
      showModal3(event) {
        this.bossShow=true;
      },
      

      bindDateChange: function(e) {
          this.date = e.detail.value
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
      decryptPhoneNumber(e){
        console.log("授权时的参数信息：：",e)
        this.$api.phoneCode({code:e.code,userId:this.userInfo.id}).then(res =>{
          console.log("返回的信息：：",res)
          this.userInfo = res.data;
          uni.showToast({
            title: '修改成功',
            icon: 'success',
            image: '',
            duration: 1000
          })
        })
      },
      confirm(e) {
        //console.log(e);
        this.userInfo.hotelId=e[0].value;
        this.userInfo.hotelName=e[0].label;
      },
      submit(){
        this.userInfo.gender=this.index;
        this.$api.storeOwner(this.userInfo).then(res =>{
          console.log("返回的信息：：",res)
          if (res.code === 200) {
            this.userInfo = res.data;
            uni.setStorageSync('userInfo', null)
            uni.setStorageSync('userInfo', res.data)
            uni.showToast({
              title:"保存成功！",
            })
            uni.switchTab({
              url: '/pages/my/my'
            });
          }

        })
      }
        
    }
  }
</script>

<style lang="scss" scoped>
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


    /* 间隔线 start*/
    .tn-strip-bottom-min {
      width: 100%;
      border-bottom: 1rpx solid #F8F9FB;
    }
    
    .tn-strip-bottom {
     width: 100%;
     border-bottom: 20rpx solid rgba(241, 241, 241, 0.8);
    }
     /* 间隔线 end*/


  /* 用户头像 start */
  .logo-image {
    width: 80rpx;
    height: 80rpx;
    position: relative;
  }

  .logo-pic {
    background-size: cover;
    background-repeat: no-repeat;
    // background-attachment:fixed;
    background-position: top;
    border: 2rpx solid rgba(255,255,255,0.05);
    box-shadow: 0rpx 0rpx 80rpx 0rpx rgba(0, 0, 0, 0.15);
    border-radius: 50%;
    overflow: hidden;
    // background-color: #FFFFFF;
  }


  /* 底部悬浮按钮 start*/
  .tn-tabbar-height {
  	min-height: 100rpx;
  	height: calc(120rpx + env(safe-area-inset-bottom) / 2);
  }
  .tn-footerfixed {
    position: fixed;
    width: 100%;
    bottom: calc(30rpx + env(safe-area-inset-bottom));
    z-index: 1024;
    box-shadow: 0 1rpx 6rpx rgba(0, 0, 0, 0);
    
  }
  /* 底部悬浮按钮 end*/
  
</style>
