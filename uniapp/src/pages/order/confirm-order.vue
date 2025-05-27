<template>
  <view class="confirm-order bg-active-color">
<!--    <Lines></Lines>-->

    <!-- 地址 -->
    <view class="order-map">
      <view class="map-title">
        <view class="map-name">
          收件人:曹国舅
        </view>
        <view class="">
          13213213211
        </view>
      </view>
      <view class="map-add">
        收货地址:北京市朝阳区建国路9009号
      </view>
    </view>

    <!-- 商品 -->
    <view class="goods-list" v-for="(item,index) in 2">

      <view class="goods-content bg-active-color">
        <image class="goods-img" src="../../static/logo.png" mode=""></image>
        <view class="goods-text">
          <view class="goods-name">
            一大串文字介绍一大串文字介绍一大串文字介绍一大串文字介绍一大串文字介绍
          </view>
          <view class="goods-size f-color">
            颜色分类:黑色
          </view>
          <view class="f-active-color">
            7天无理由
          </view>
        </view>
        <view class="">
          <view class="">
            ￥299.00
          </view>
          <view class="goods-size">
            x 1
          </view>
        </view>
      </view>

    </view>

    <!-- 底部:提交订单 -->
    <view class="order-foot">
      <view class="total-price">
        合计: <text class="f-active-color">$3999.00</text>
      </view>
      <view class="confirm">
        提交订单
      </view>
    </view>
  </view>
</template>

<script>

export default {
  data() {
    return {

    };
  },
  components:{

  },
  methods:{

  }
}
</script>

<style lang="less">
.confirm-order{
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 100%;
}
.order-map{
  margin-bottom: 10rpx;
  padding: 20rpx;
  background-color: #fff;
  line-height: 50rpx;
}
.map-title{
  display: flex;
  justify-content: space-between;
}
.map-name{
  font-weight: bold;
}
.goods-list{
  background-color: #fff;
  padding: 10rpx 0;
}

.goods-content{
  padding: 10rpx 20rpx;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.goods-text{
  width: 360rpx;
  padding: 0 10rpx;
  font-size: 26rpx;
}
.goods-img{
  width: 160rpx;
  height: 160rpx;
}
.goods-size{
  font-size: 24rpx;
}

.order-foot{
  width: 100%;
  height: 80rpx;
  position: fixed;
  bottom: 0;
  left: 0;
  background-color: #fff;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.confirm{
  color: #fff;
  background-color: #49bdfb;
  padding: 10rpx 30rpx;
}
.total-price{
  padding: 0 20rpx;
}


</style>

