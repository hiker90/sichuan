<template>
  <el-container>
    <el-header height="50px"><img class="logo" src="../assets/images/logo.png"/>全省公路网规划一张图</el-header>
    <el-container style="height: calc(100% - 100px)">
        <div>
        {{errMsg}}
        </div>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
        errMsg:""
    };
  },
  methods: {
  },
  mounted:function(){
      let errCode = this.$route.query.errCode;
      switch(errCode){
          case "0":this.errMsg = "抱歉，您没有查看权限";
            break;
          case "-1":this.errMsg = "抱歉，您当前未登录，请登录后重试";
            break;
          case "-2":this.errMsg = "网络错误，请联系系统管理员";
           break;
          default:
            this.errMsg = "未知错误";
      }
  }
};
</script>

<style scoped>
 *{
   margin:0;
   padding:0;
 }
.el-loading-spinner .el-loading-text,.el-loading-spinner .path{
  color:#304156;
}
#app > .el-container {
  width: 100%;
  height: 100%;
}
.el-header {
  background-color: #304156;
  color: #333;
  color: #ffffff;
  font-size: 20px;
  line-height: 50px;
  font-weight: bold;border-bottom: 1px solid #63c9c7;
}
.el-header .logo{
  position: relative;
  width:32px;height:32px;
  top: 8px;
  margin:0 10px;
}
.el-container div{
    position: absolute;
    top:50%;
    margin-top:-32px;
    width:100%;
    text-align: center;
    line-height: 64px;
    font-size:32px;
    color:#304156;
}
</style>

