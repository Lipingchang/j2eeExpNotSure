// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {Message} from 'element-ui'

Vue.config.productionTip = false
Vue.use(ElementUI);


import Vuex from 'vuex'
import axios from 'axios'
var instance = axios.create({
  baseURL:"http://127.0.0.1:8088",
  timeout:1000,
  withCredentials: true 
});

//请求返回拦截，把数据返回到页面之前做些什么...
instance.interceptors.response.use((response) => {
  // console.log('interceptors',response)
  return response;
//   //特殊错误处理，状态为10时为登录超时
//   if (response.data.code === 10) {
//     // iView.Message.error("登录已超时，请重新登录");
//     // router.push("/login")
//   //其余错误状态处理    
//   } else if (response.data.code != 0) {
//     // iView.Message.error(response.data.msg)
//   //请求成功
//   } else if(response.data.code === 0){
//     //将我们请求到的信息返回页面中请求的逻辑
//     return response;
//   }
//  //......

}, function (error) {
  if ( error.response.status == 401 ){
    Message.error('权限不足，不能访问');
  }
  return Promise.reject(error);
});


Vue.prototype.$axios= instance
Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    count: 0,
    userinfo:{

    }
  },
  mutations: {
    userlogin (state,userinfo) {
      state.userinfo = userinfo
    }
  }
})
Vue.prototype.$store = store

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
