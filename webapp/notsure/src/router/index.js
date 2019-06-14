import Vue from 'vue'
import Router from 'vue-router'
import admin from '@/components/admin/admin'
import login from '@/components/login/login'
import channels from '@/components/lanmuAdmin/lanmu'
import articles from '@/components/articles/articles'
import persons from '@/components/persons/persons'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'admin',
      component: admin,
      children:[
        {path:'/channels',component:channels},
        {path:'/articles',component:articles},
        {path:'/persons',component:persons}
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: login
    }
  ]
})
