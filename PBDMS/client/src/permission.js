import router from './router'
import store from './store'
// import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getCookie, removeCookie, hasPermission } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/404'] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()
  // set page title
  document.title = getPageTitle(to.meta.title)
  // next()
  const hasCookieToken = getCookie('token')
  const { token } = to.query
  if (token) {
    // url中存在拼接token，即页面由入口跳转而来
    try {
      // 调用子系统login接口，token设置cookie，获取权限资源列表
      const accessMenu = await store.dispatch('user/setToken', token)
      const accessedRoutes = await store.dispatch('user/generateRoutes', accessMenu)
      router.options.routes = accessedRoutes
      router.addRoutes(accessedRoutes)
      if (hasPermission(to.path, accessedRoutes)) {
        next({ path: to.path, replace: true })
      } else {
        throw new Error('当前用户没有访问此资源的权限！')
      }
    } catch (error) {
      removeCookie('token')
      next({ path: '/404' })
      NProgress.done()
    }
  } else {
    // 白名单页面直接跳转
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      if (hasCookieToken) {
        // 无query有cookie
        if (!store.getters.name) {
          // 无用户名，即未调用login
          try {
            // 调用子系统login接口，token设置cookie，获取权限资源列表
            const accessMenu = await store.dispatch('user/setToken', hasCookieToken)
            const accessedRoutes = await store.dispatch('user/generateRoutes', accessMenu)
            router.options.routes = accessedRoutes
            router.addRoutes(accessedRoutes)
            if (hasPermission(to.path, accessedRoutes)) {
              next({ path: to.path, replace: true })
            } else {
              throw new Error('当前用户没有访问此资源的权限！')
            }
          } catch (error) {
            next({ path: '/404' })
            NProgress.done()
          }
        } else {
          if (hasPermission(to.path, store.getters.accessedRoutes)) {
            next()
          } else {
            throw new Error('当前用户没有访问此资源的权限！')
          }
        }
      } else {
        next({ path: '/404' })
        NProgress.done()
      }
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
