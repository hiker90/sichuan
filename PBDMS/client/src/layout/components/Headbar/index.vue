<template>
  <div class="headbar">
    <div class="headbar-logo-container">
      <img src="@/assets/logo.png" class="headbar-logo">
      <h1 v-if="device!=='mobile'" class="headbar-title">{{ title }} </h1>
    </div>
    <!-- <head-nav /> -->
    <div class="right-menu">
      <!-- <div class="right-menu-item hover-effect">
        <app-link :to="resolvePath('http://localhost:9527/IPBP/home')" :target="'_self'">
          <el-tooltip effect="dark" content="返回首页" placement="bottom">
            <svg-icon icon-class="goback" />
          </el-tooltip>
        </app-link>
      </div> -->
      <!-- <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">
          <span class="user-avatar">{{ name || '某' }}</span>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <el-dropdown-item @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown> -->
      <span class="user-name">欢迎您，{{ name || '某' }}</span>
    </div>
  </div>
</template>

<script>
import { isExternal } from '@/utils/validate'
import { mapGetters } from 'vuex'
// import AppLink from '@/layout/components/Sidebar/Link'
import { title } from '@/settings'
// import HeadNav from './Headnav'
export default {
  // components: { AppLink },
  data() {
    return {
      title: title
    }
  },
  computed: {
    ...mapGetters([
      'avatar',
      'name',
      'device'
    ])
  },
  methods: {
    async logout() {
      await this.$store.dispatch('user/logout')
      window.location.replace(`http://localhost:9527/IPBP/login?redirect=${this.$router.options.base}${this.$route.fullPath}`)
    },
    resolvePath(routePath) {
      if (isExternal(routePath)) {
        return routePath
      }
      // if (isExternal(this.basePath)) {
      //   return this.basePath
      // }
      // return path.resolve(this.basePath, routePath)
    }
  }
}
</script>

<style lang="scss" scoped>
.headbar {
  height: 50px;
  width: 100%;
  position: fixed;
  z-index: 1001;
  background: #304156;
  border-bottom: 2px solid #63C9C7;

  .headbar-logo-container {
    padding-left: 12px;
    display: inline-block;

    & .headbar-logo {
      width: 32px;
      height: 32px;
      margin-right: 12px;
      position: relative;
      top: 10px;
    }

    & .headbar-title {
      display: inline-block;
      margin: 0;
      color: #fff;
      font-weight: 600;
      line-height: 50px;
      font-size: 20px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
    }
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 10px;
      height: 100%;
      font-size: 18px;
      color: #fff;
      vertical-align: top;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }
    .user-name{
      color: #fff;
      margin-right: 20px;
    }
    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        position: relative;
        top: 2px;
        span.user-avatar{
          display: inline-block;
          cursor: pointer;
          padding: 7px;
          border-radius: 50%;
          line-height: 18px;
          font-size: 18px;
          text-align: center;
          color: #fff;
          background: #E6A23C;
        }
        img.user-avatar {
          cursor: pointer;
          margin-top: 6px;
          width: 32px;
          height: 32px;
          border-radius: 50%;
        }

        .el-icon-caret-bottom {
          display: inline-block;
          vertical-align: top;
          padding: 16px 0 0 10px;
          cursor: pointer;
          font-size: 12px;
          color: #fff;
        }
      }
    }
  }
}
</style>
