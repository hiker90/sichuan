import request from '@/utils/request'

// 子系统登录
export function sysLogin(token) {
  return request({
    url: 'login',
    method: 'post',
    params: { token }
  })
}

