import App from './App'
import store from './store'
import api from "@/api/index"
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'
Vue.prototype.websiteUrl = 'http://192.168.0.103:8080';
Vue.prototype.$api = api;
// 引入全局TuniaoUI
import TuniaoUI from 'tuniao-ui'
Vue.use(TuniaoUI)

// 引入TuniaoUI提供的vuex简写方法
let vuexStore = require('@/store/$t.mixin.js')
Vue.mixin(vuexStore)

// 引入TuniaoUI对小程序分享的mixin封装
let mpShare = require('tuniao-ui/libs/mixin/mpShare.js')
Vue.mixin(mpShare)

const app = new Vue({
  store,
  ...App
})

app.$mount()