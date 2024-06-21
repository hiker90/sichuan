import { sysLogin } from '@/api/user'
import { setCookie } from '@/utils/auth'
import { asyncRoutes, constantRoutes } from '@/router'

const getDefaultState = () => {
  return {
    token: '',
    name: '',
    dept: '',
    avatar: '',
    accessMenu: [],
    accessedRoutes: [],
    accessApi: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_DEPT: (state, dept) => {
    state.dept = dept
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_MENU_PERM: (state, menu) => {
    state.accessMenu = menu
  },
  SET_BUTTON_PERM: (state, api) => {
    state.accessApi = api
  },
  SET_ROUTES: (state, routes) => {
    state.accessedRoutes = routes
  }
}

const actions = {
  // 判断登录状态 设置前端缓存token
  setToken({ commit, state }, token) {
    return new Promise((resolve, reject) => {
      setCookie('token', token)
      sysLogin(token).then(response => {
        commit('SET_TOKEN', token)
        const { userName, dept, catalog, button } = response.data
        commit('SET_NAME', userName)
        commit('SET_DEPT', dept)
        const accessMenu = catalog.map(item => item.url)
        const accessApis = button.map(item => item.perms)
        commit('SET_MENU_PERM', accessMenu)
        commit('SET_BUTTON_PERM', accessApis)
        resolve(accessMenu)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // 根据用户权限配置动态路由
  generateRoutes({ commit }, menu) {
    return new Promise(resolve => {
      const accessedRoutes = asyncRoutes.filter(item => menu.indexOf(item.path) > -1)
      const routes = constantRoutes.concat(accessedRoutes)
      commit('SET_ROUTES', routes)
      resolve(routes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
