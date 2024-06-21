import Cookies from 'js-cookie'

export function getCookie(key) {
  return Cookies.get(key)
}

export function setCookie(key, val) {
  return Cookies.set(key, val)
}

export function removeCookie(key) {
  return Cookies.remove(key)
}

export function hasPermission(path, accessedRoutes) {
  if (accessedRoutes && accessedRoutes instanceof Array) {
    return accessedRoutes.some(val => val.path === path)
  } else return false
}
