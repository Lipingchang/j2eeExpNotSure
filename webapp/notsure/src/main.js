// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false
Vue.use(ElementUI);


import Vuex from 'vuex'
import axios from 'axios'
var instance = axios.create({
  baseURL:"http://127.0.0.1:8088",
  timeout:1000,
  withCredentials: true 
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
