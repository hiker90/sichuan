<template>
  <div class="head-nav-container">
    <el-dropdown v-if="device==='mobile'" class="head-nav-collapse">
      <span class="el-dropdown-link">
        <i class="el-icon-menu" />
      </span>
      <el-dropdown-menu slot="dropdown">
        <router-link to="/">
          <el-dropdown-item>
            Home
          </el-dropdown-item>
        </router-link>
      </el-dropdown-menu>
    </el-dropdown>
    <el-menu v-else class="head-nav" mode="horizontal" background-color="#304156" text-color="#fff" active-text-color="#63C9C7">
      <el-menu-item index="1">
        <router-link to="/">处理中心</router-link>
      </el-menu-item>
      <el-menu-item index="2">消息中心</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'HeadNav',
  props: {
  },
  data() {
    return {
    }
  },
  computed: {
    ...mapGetters([
      'device'
    ])
  }
}
</script>

<style lang="scss" scoped>
.head-nav-container {
  display: inline-block;
  vertical-align: top;
  margin: 0 10px;

  & > .el-menu.el-menu--horizontal {
    border-bottom: none;
    & > .el-menu-item {
      height: 50px;
      line-height: 50px;
      border-bottom: 2px solid #63C9C7 !important;
    }
    & > .el-menu-item.is-active {
      border: none;
      background: #192d3d !important;
      border-bottom: 2px solid #304156 !important;
    }
  }

  & > .head-nav-collapse {
    color: #fff;
    line-height: 50px;
    & > .el-dropdown-link {
      font-size: 20px;
      vertical-align: middle;
    }
  }
}
</style>
