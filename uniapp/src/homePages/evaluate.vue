<template>
  <view class="components-form">

    <!-- 顶部自定义导航 -->
    <tn-nav-bar fixed alpha customBack>
      <view slot="back" class='tn-custom-nav-bar__back'
            @click="goBack">
        <text class='icon tn-icon-left-arrow'></text>
      </view>
      <view class="tn-flex tn-flex-col-center tn-flex-row-center ">
        <text class="tn-text-bold tn-text-xl tn-color-black">订单评价</text>
      </view>
    </tn-nav-bar>

    <!-- 页面内容 -->
    <view :style="{paddingTop: '30px'}">

      <dynamic-demo-template ref="demoTemplate" :sectionList="sectionList" :full="true" :fullWindowsScroll="true" @click="click">
        <tn-form-item >
          <image v-if="orderInfo.room" :src="imageUrl+orderInfo.room.mainImg " mode="aspectFill"></image>
        </tn-form-item>
        <tn-form :model="model" ref="form" :errorType="errorType" >
          <tn-form-item label="评分" prop="phone" >
            <tn-rate v-model="model.star" :count="count"></tn-rate>
          </tn-form-item>
          <tn-form-item  prop="evaluateDesc" >
            <tn-input :height="400" v-model="model.evaluateDesc" type="textarea" aria-rowspan="8" placeholder="请输入评价信息,和大家分享一下" :border="border"></tn-input>
          </tn-form-item>


<!--          <tn-form-item label="上传图片" prop="photo" :labelPosition="labelPosition" :labelAlign="labelAlign">-->
<!--            <tn-image-upload :fileList="model.photo" @on-list-change="imageUploadChange"></tn-image-upload>-->
<!--          </tn-form-item>-->
        </tn-form>


      </dynamic-demo-template>
      <tn-button backgroundColor="#01BEFF" fontColor="#FFFFFF" width="90%" style="margin-left: 35rpx;" @click="submit">提交</tn-button>



    </view>

  </view>
</template>

<script>
import dynamicDemoTemplate from '@/libs/components/dynamic-demo-template.vue'
import {getOrderById, getUserEvaluate, userEvaluateSave} from "../api/modules/user";
export default {
  name: 'componentsForm',
  components: {dynamicDemoTemplate},
  data() {
    return {
      errorType: ['message', 'border-bottom', 'toast'],
      border: false,
      count: 5,
      value: 0,
      model: {
        evaluateDesc:'',
        star:0,
      },
      rules: {
        evaluateDesc: [
          {
            min: 5,
            message: '评价描述不能少于5个字',
            trigger: 'change'
          }
        ],
      },
      sectionList: [],
      imageUrl:this.websiteUrl,
      userInfo:uni.getStorageSync('userInfo') ,
      orderInfo:{},
      orderId:null,
    }
  },
  onLoad(data) {
    //console.log("data::",data)
    if(typeof this.userInfo=="string"){
      this.userInfo=JSON.parse(this.userInfo);
    }
    //console.log("userInfo::",this.userInfo)
    this.orderId=data.id;
    let orderId=data.id;
    this.getOrderById(orderId);
    this.getUserEvaluate(orderId);
  },
  onReady() {
    //this.$refs.form.setRules(this.rules)
  },
  methods: {
    async getOrderById(orderId){
      this.$api.getOrderById({id:orderId}).then(res =>{
        console.log("res::",res)
        if (res.code==200){
          this.orderInfo=res.data;
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
    async getUserEvaluate(orderId){
      this.$api.getUserEvaluate({orderId:orderId,userId:this.userInfo.id}).then(res =>{
        console.log("res::",res)
        if (res.code==200){
          res.data.star=parseInt(res.data.star);
          this.model=res.data;
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
    click(event) {
      this[event.methods] && this[event.methods](event)
    },
    goBack() {
      // 通过判断当前页面的页面栈信息，是否有上一页进行返回，如果没有则跳转到首页
      uni.reLaunch({
        url: '/homePages/orderList'
      })
    },
    // 表单提交
    submit() {

      //return
      this.$refs.form.validate(valid => {
        if (valid) {
          let params=this.model;
          params.userId=this.userInfo.id;
          params.userName=this.userInfo.nickName;
          params.orderId=this.orderId;
          params.roomName=this.orderInfo.room.roomName;
          params.roomId=this.orderInfo.room.id;
          console.log("model::",params)
          // return
          // 验证通过
          let _this = this;
          _this.$api.userEvaluateSave(params).then(res =>{
            console.log("返回的信息：：",_this)
            if(res.code ===200){
              // _this.$refs.toast.show({
              //   title: '评价成功',
              //   content: '即将返回到订单页面',
              //   icon: 'success',
              //   image: '',
              //   duration: 3000
              // })
              uni.navigateTo({url: '/homePages/orderList'});
            }
          })

        } else {
          // 验证失败
        }
      })
    },

  }
}
</script>

<style lang="scss" scoped>

.agreement {
  display: flex;
  align-items: center;
  margin: 40rpx 0;

  &-text {
    padding-left: 8rpx;
    color: $tn-font-sub-color;
  }
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>
