const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  accessedRoutes: state => state.user.accessedRoutes,
  accessApi: state => state.user.accessApi
}
export default getters
