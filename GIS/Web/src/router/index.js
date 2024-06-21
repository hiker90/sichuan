import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/index'
import Error from '@/components/error'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/Error',
      name: 'Error',
      component: Error
    },
  ]
})
