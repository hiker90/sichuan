import axios from 'axios'
import { Message } from 'element-ui'
import store from '@/store'
import { removeCookie } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API // url = base url + request url
  // withCredentials: true // send cookies when cross-domain requests
  // timeout: 10000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      // config.headers['X-Token'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response
    // if the custom code is not 20000, it is judged as an error.
    if (res.status !== 200) {
      Message({
        message: res.message && res.message.length < 30 || '未知错误，请联系管理员！',
        type: 'error',
        duration: 5 * 1000
      })
    } else {
      if (res.data.success === false) {
        Message({
          message: res.data.msg && res.data.msg.length < 30 || '未知错误，请联系管理员！',
          type: 'warning',
          duration: 5 * 1000
        })
      } else {
        return res.data
      }
    }
  },
  error => {
    console.log('err' + error) // for debug
    const msg = error.response.data && error.response.data.message || error.message
    if (msg === 'timeout') {
      // 未登录 或 超时
      removeCookie('token')
      window.location.reload()
    }
    Message({
      message: (msg && msg.length < 80) ? msg : '未知错误，请联系管理员！',
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
