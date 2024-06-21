import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/',
    redirect: '/dataQuality',
    hidden: true
  }
]

// 根据权限配置的动态路由
export const asyncRoutes = [
  {
    path: '/dataQuality',
    component: Layout,
    children: [{
      path: '',
      name: 'DataQuality',
      component: () => import('@/views/dataQuality/index'),
      meta: { title: '数据质量', icon: 'example' }
    }]
  },

  {
    path: '/dataCheckConfig',
    component: Layout,
    children: [
      {
        path: '',
        name: 'DataCheckConfig',
        component: () => import('@/views/dataCheckConfig/index'),
        meta: { title: '预警规则', icon: 'el-icon-setting' }
      }
    ]
  },

  {
    path: '/dataImport',
    component: Layout,
    children: [
      {
        path: '',
        name: 'DataImport',
        component: () => import('@/views/dataImport/index'),
        meta: { title: '数据导入', icon: 'excel' }
      }
    ]
  },

  {
    path: '/dataCatalog',
    component: Layout,
    children: [
      {
        path: '',
        name: 'DataCatalog',
        component: () => import('@/views/dataCatalog/index'),
        meta: { title: '数据目录', icon: 'tree-table' }
      }
    ]
  },

  {
    path: '/dataQuery',
    component: Layout,
    children: [
      {
        path: '',
        name: 'DataQuery',
        component: () => import('@/views/dataQuery/index'),
        meta: { title: '数据查询', icon: 'search' }
      }
    ]
  },

  {
    path: '/dataStatistics',
    component: Layout,
    children: [
      {
        path: '',
        name: 'DataStatistics',
        component: () => import('@/views/dataStatistics/index'),
        meta: { title: '数据统计', icon: 'chart' }
      }
    ]
  },

  {
    path: '/dataExchange',
    component: Layout,
    children: [
      {
        path: '',
        name: 'DataExchange',
        component: () => import('@/views/dataExchange/index'),
        meta: { title: '交换共享', icon: 'el-icon-share' }
      }
    ]
  },

  {
    path: '/operateLogs',
    component: Layout,
    children: [
      {
        path: '',
        name: 'OperateLogs',
        component: () => import('@/views/operateLogs/index'),
        meta: { title: '操作日志', icon: 'bug' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  mode: 'history', // require service support
  base: '/PBDMS/',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
