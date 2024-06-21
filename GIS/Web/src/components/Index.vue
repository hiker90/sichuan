<template>
  <el-container>
    <el-header height="50px"><img class="logo" src="../assets/images/logo.png"/>全省公路网规划一张图<label v-if="username">欢迎您，<a href="javascript:;">{{username}}</a></label></el-header>
    <el-container style="height: calc(100% - 100px)">
      <el-aside width="200px">
          <el-tree
            :data="tree"
            show-checkbox
            node-key="key"
            :default-expanded-keys="expandedKeys"
            :default-checked-keys="checkedKeys"
            :props="treeProps"
            @check="onChecked"
            ref="tree"
          >
          </el-tree>
      </el-aside>
      <el-main 
        v-loading.sync="loading"  
        element-loading-text="加载中"
        element-loading-background="rgba(0, 0, 0, 0.5)"
        element-loading-customClass="loading"
      >
        <ol-map ref="olMap"/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import "ol/ol.css";
import * as mapFunction from "../js/mapFunction";
import treeConfig from "../js/treeConfig";
import {Loading} from 'element-ui'
import axios from 'axios';
export default {
  data() {
    return {
      text: "",
      loading:false,
      tree: treeConfig.treeNodes,
      checkedKeys:treeConfig.checkedKeys,
      expandedKeys:treeConfig.expandedKeys,
      treeProps: treeConfig.treeProps,

      username:"",
    };
  },
  methods: {
    onChecked:function(node,curChecked){
      if(this.loading){
        let isChecked = false;
        curChecked.checkedNodes.forEach(item=>{
          if(item.key == node.key){
            isChecked = true;
          }
        })

        let setChecked = n => {
          if(!n.children){
            this.$refs.tree.setChecked(n.key,!isChecked)
          }
          else{
            n.children.forEach( item =>{
              setChecked(item)
            })
          }
        };

        setChecked(node);

        this.$message({message:'请等待当前加载完成',offset:100,showClose: true,});
        return;
      }
      
      let loadingPromise = [];
      this.loading = true;

      let ygyxNodes = [];
      let areaNodes=[];
      let featureNodes = [];

      for(let i=0;i<curChecked.checkedNodes.length;i++){
         if(curChecked.checkedNodes[i].children){
           continue;
         }
          
         if(curChecked.checkedNodes[i].type=="ygyx"){
            ygyxNodes.push(curChecked.checkedNodes[i])
         } else if(curChecked.checkedNodes[i].type=="xzqh"){
            areaNodes.push(curChecked.checkedNodes[i])
         } else{
            featureNodes.push(curChecked.checkedNodes[i])
         } 
      }
      loadingPromise.push(mapFunction.loadRemoteSensingImage(ygyxNodes));
      loadingPromise.push(mapFunction.loadArea(areaNodes));
      loadingPromise.push(mapFunction.loadFeature(featureNodes));

      Promise.all(loadingPromise).finally(()=>{
          this.loading = false;
      })
    }
  },
  mounted:function(){
      let loading = Loading.service({ 
        fullscreen: true,
        text:"加载中",
        background:"rgba(0, 0, 0, 0.5)"
      });

      let token = this.$route.query.token;
      let url = "http://172.16.22.196/getuser/"+token;
      
      axios.get(url, {}).then(res => {
        if(res.data.code==0){
          let perms = res.data.perms.split(",");
          if(perms.indexOf("2")>-1){ //登录成功且有权限
            this.username=res.data.username
          }else{ //无权限
            this.$router.push({name:'Error',query: {errCode:'0'}})
          }
        }else{ //未登录
         // this.$router.push({name:'Error',query: {errCode:'-1'}})
        }
      }).catch(err=>{
         //this.$router.push({name:'Error',query: {errCode:'-2'}})
      }).finally(()=>{
        loading.close();
      })
      
  },
};
</script>

<style scoped>
 *{
   margin:0;
   padding:0;
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
.el-header label{
  position:absolute;
  right:20px;
  color:#fff;
  font-size:14px;
  font-weight: 600;
}
.el-header label a{
  color:#fff;
  text-decoration: none;;
}
.el-aside {
  background-color:  #304156;
  text-align: center;
}
.el-tree{
  background-color:  #304156;
  color: #a7b1c2;font-weight: 600
}
.el-tree-node{
  padding:14px 20px 14px 25px
}
.el-tree-node:focus>.el-tree-node__content{
  background-color:  #304156 !important;
}
.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  padding: 0;
  overflow: hidden;
}
</style>
<style>
.el-loading-spinner .el-loading-text,.el-loading-spinner .path{
  color:#304156;
}
.el-loading-spinner .el-loading-text{
  color:#fff !important;
}
.el-loading-spinner .circular .path{
  stroke:#fff;
}
.el-tree-node:focus>.el-tree-node__content,.el-tree-node:hover>.el-tree-node__content{
  background-color:  #1890ff !important;
  color:#fff;
}
.ol-control button{
  background-color: rgba(48,65,86,0.5)
}
.ol-control button:focus,.ol-control button:hover{
   background-color: rgba(48,65,86,0.7)
}
.ol-scale-line{
  background-color: rgba(48,65,86,0.3)
}
</style>
