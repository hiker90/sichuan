<template>
  <div class="back-header">
    <h4 class="title">{{ title }}</h4>
    <div class="operate-btn">
      <slot />
      <el-button v-if="showBack" size="small" type="info" icon="el-icon-back" @click="handleBack">
        返回
      </el-button>
    </div>
  </div>
</template>

<script>

export default {
  props: {
    title: {
      required: true,
      type: String
    },
    showBack: {
      required: false,
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
    }
  },
  watch: {
  },
  created() {
  },
  methods: {
    handleBack() {
      this.$emit('back')
    }
  }
}
</script>

<style lang="scss" scoped>
.back-header{
  position: relative;
  height: 40px;
  .operate-btn{
    position: absolute;
    top: -6px;
    right: 0;
  }
}
</style>
