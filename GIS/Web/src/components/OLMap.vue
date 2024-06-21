<template>
  <div class="map-wrap" ref="mapwrap">
    <div id="map" ref="map"></div>
    <div id="popup" class="ol-popup">
      <a href="#" id="popup-closer" class="ol-popup-closer" ></a>
      <div id="popup-content"></div>
    </div>
    <div id="popup-delete" class="ol-popup-delete">
      <a href="#" id="popup-delete-closer" class="ol-popup-delete-closer ol-popup-closer" ></a>
    </div>
    <div id="popup-measure" class="ol-popup-measure">
      <a href="#" id="popup-mesaure-closer" class="ol-popup-closer" ></a>
      <div id="popup-measure-content"></div>
    </div>
    <div id="toolbar" :class="toolbarShow ? 'toolbar-show' : 'toolbar-hide'">
      <div>
        <button
          @click="onToolbar"
          :class="toolbarShow ? 'btn-toolbar-close' : 'btn-toolbar-open'"
          :title="toolbarShow ? '隐藏工具栏' : '打开工具栏'"
        ></button>
      </div>
      <div>
        <button
          @click="onDraw('Point')"
          title="画点"
          :class="this.curOperate == 'Point' ? 'btn-clicked' : ''"
        ></button>
        <button
          @click="onDraw('LineString')"
          title="画线"
          :class="this.curOperate == 'LineString' ? 'btn-clicked' : ''"
        ></button>
        <button
          @click="onDraw('Circle')"
          title="画圆"
          :class="this.curOperate == 'Circle' ? 'btn-clicked' : ''"
        ></button>
        <button
          @click="onDraw('Polygon')"
          title="画多边形"
          :class="this.curOperate == 'Polygon' ? 'btn-clicked' : ''"
        ></button>
        <button @click="onModify" title="修改" :class="this.curOperate == 'Modify' ? 'btn-clicked' : ''"></button>
        <button @click="onClear" title="清除绘制"></button>
        <button
          @click="onMeasure('Distance')"
          title="测距"
          :class="this.curOperate == 'Distance' ? 'btn-clicked' : ''"
        ></button>
        <button
          @click="onMeasure('Area')"
          title="测面积"
          :class="this.curOperate == 'Area' ? 'btn-clicked' : ''"
        ></button>
        <button
          @click="onClick"
          title="点选元素"
          :class="this.curOperate == 'Click' ? 'btn-clicked' : ''"
        ></button>
        <button
          @click="onMove"
          title="移动"
          :class="this.curOperate == 'Move' ? 'btn-clicked' : ''"
        ></button>
        <button
          @click="onSearch"
          title="查询"
          :class="this.curOperate == 'Search' ? 'btn-clicked' : ''"
        >
        </button>
        <button
          @click="onClickSearch"
          title="点击查询"
          :class="this.curOperate == 'ClickSearch' ? 'btn-clicked' : ''"
        >
        </button>
        <button
          @click="onLayers"
          title="图层"
          :class="this.layersDialogShow ? 'btn-clicked' : ''"
        ></button>
          <button
          @click="onFullScreen"
          :title="this.fullScreen ? '取消全屏' : '全屏显示'"
          :class="this.fullScreen ? 'cancel-fullscreen' : 'fullscreen'"
        ></button>
      </div>
    </div>
    <el-dialog title="图层" width="30%" :visible.sync="layersDialogShow">
      <el-form>
        <el-form-item label="图层">
          <el-checkbox-group v-model="checkedLayers">
            <el-checkbox
              v-for="layer in layers"
              :label="layer"
              :key="layer"
              :disabled="true"
              @change="onLayerChange()"
              >{{ layer }}
              </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="查找" width="30%" :visible.sync="searchDialogShow">
      <el-form ref="form" :model="search" label-width="80px">
        <el-form-item label="路线编码">
          <el-input v-model="search.lxbm"></el-input>
        </el-form-item>
        <el-form-item label="桩号">
          <el-input v-model="search.zh"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="searchDialogShow = false">取 消</el-button>
        <el-button type="primary" @click="onSearchConfirm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import * as mapFunction from "../js/mapFunction";
export default {
  data() {
    return {
      olMap: null,
      toolbarShow: true,
      layersDialogShow: false,
      layers: [],
      checkedLayers: [],
      curOperate: "Move",
      fullScreen: false,
      searchDialogShow:false,
      search:{
        lxbm:"",
        zh:"",
      },
       
    }
  },
  watch:{
    curOperate(val,oldval){
      if(val!="Point"&&val!="LineString"&&val!="Circle"&&val!="Polygon"){
          mapFunction.cancelDraw();
      }
      
      if((oldval=="Distance"||oldval=="Area")&&(val!="Distance"&&val!="Area")){
          mapFunction.cancelMeasure();  
      }

      if(oldval=="Click"){
        mapFunction.removeMapClick();
      }
      if(oldval=="Modify"){
         mapFunction.cancelModify();
      }
      if(oldval=="ClickSearch"){
        mapFunction.removeMapSearchClick();
      }
  
      if((oldval=="Search"||oldval=="ClickSearch"||oldval=="Click")&&(val!="Search"&&val!="ClickSearch"&&val!="Click")){
        mapFunction.removeSearchLayers();
      }
    }, 
  },
  methods: {
    onToolbar: function () {
      this.toolbarShow = !this.toolbarShow;
    },
    onDraw: function (type) {
      this.$refs.map.style.cursor = "";
      this.curOperate = type;
      
      mapFunction.draw(type);
    },
    onModify:function(){
      this.$refs.map.style.cursor = "";
       this.curOperate = "Modify";
       mapFunction.drawModify();
    },
    onClear: function () {
      this.measureResult = "";
      mapFunction.clearDraw();
    },
    onMeasure: function (type) {
      this.$refs.map.style.cursor = "";
      this.curOperate = type;
      mapFunction.measure(type);
    },
    onClick:function(){
      this.$refs.map.style.cursor = "";
      this.curOperate = "Click";
      mapFunction.bindMapClick();
    },
    onMove: function () {
      this.$refs.map.style.cursor = "move";
      this.curOperate = "Move";
    },
    onLayers: function () {
      this.layersDialogShow = true;
      if (this.layers.length == 0) {
        this.layers = mapFunction.getMapLayers();
        this.layers.forEach((item, index, arr) => {
          this.checkedLayers.push(item);
        });
      }
    },
    onFullScreen: function () {
      this.fullScreen = !this.fullScreen;
      if (this.fullScreen) {
        let mapwrap = this.$refs.mapwrap;
        if (mapwrap.requestFullscreen) {
          mapwrap.requestFullscreen();
        } else if (mapwrap.msRequestFullscreen) {
          mapwrap.msRequestFullscreen();
        } else if (mapwrap.mozRequestFullScreen) {
          mapwrap.mozRequestFullScreen();
        } else if (mapwrap.webkitRequestFullscreen) {
          mapwrap.webkitRequestFullscreen();
        }
      } else {
        if (document.exitFullscreen) {
          document.exitFullscreen();
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen();
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen();
        } else if (document.webkitExitFullscreen) {
          document.webkitExitFullscreen();
        }
      }
    },
    onLayerChange: function () {
      mapFunction.showMapLayers(this.checkedLayers);
    },
    onSearch:function(){
      this.searchDialogShow = true;
      this.curOperate = "Search";
    },
    onSearchConfirm:function(){
      this.$emit('update:loading',true)
      mapFunction.searchZh(this.search.lxbm,this.search.zh).then(()=>{
        this.searchDialogShow = false;
        this.$emit('update:loading',false)
      })
    },
    onClickSearch:function(){
      this.curOperate = "ClickSearch";
      this.$refs.map.style.cursor = "pointer";
      mapFunction.bindMapSearchClick();
    }
  },
  mounted: function () {
    mapFunction.init().then((olMap) => {
      this.olMap = olMap;
      this.$refs.map.style.cursor = "move";
    });
  },
};
</script>

<style scoped>
.map-wrap,
#map {
  width: 100%;
  height: 100%;
}

.map-wrap {
  position: relative;
  top: 0;
  left: 0;
}

.ol-popup {
  position: absolute;
  background-color: white;
  box-shadow: 0 1px 4px rgba(0,0,0,0.2);
  padding: 15px;
  border-radius: 10px;
  border: 1px solid #cccccc;
  bottom: 12px;
  left: -50px;
  min-width: 280px;
}
.ol-popup:after, .ol-popup:before {
  top: 100%;
  border: solid transparent;
  content: " ";
  height: 0;
  width: 0;
  position: absolute;
  pointer-events: none;
}
.ol-popup:after {
  border-top-color: white;
  border-width: 10px;
  left: 48px;
  margin-left: -10px;
}
.ol-popup:before {
  border-top-color: #cccccc;
  border-width: 11px;
  left: 48px;
  margin-left: -11px;
}
.ol-popup-closer{
  text-decoration: none;
  position: absolute;
  top: 2px;
  right: 8px;
}
.ol-popup-delete-closer{
  position:absolute;
  top:-25px;
  left:10px;
}
.ol-popup-closer{
  color:red;
  text-decoration:none;
}
.ol-popup-closer:after{
  content: "✖";
}
#toolbar {
  width: 82px;
  height: 224px;
  position: absolute;
  top: 150px;
  background-color: white;
  box-shadow: 0px 0px 3px 1px #888888;
}
.toolbar-show {
  right: 0px;
}
.toolbar-hide {
  right: -64px;
}
#toolbar > div {
  cursor: default;
  float: left;
}
#toolbar > div:nth-child(1) {
  width: 16px;
  height: 100%;
}
#toolbar > div:nth-child(1) button {
  width: 16px;
  height: 100%;
}
#toolbar > div:nth-child(2) {
  width: 64px;
  height: 100%;
}
#toolbar button {
  border: 0;
  background-color: white;
}
#toolbar button:hover,
.btn-clicked {
  background-color: #d3d3d3 !important;
  box-shadow: 0px 0px 0px 0px #888888;
}
#toolbar button:focus {
  margin: 0;
  padding: 0;
  border: none;
}
.btn-toolbar-close {
  background-image: url("../assets/images/toolbar/close.png");
  background-repeat: no-repeat;
  background-size: 80% 10%;
  background-position: 50% 50%;
}
.btn-toolbar-open {
  background-image: url("../assets/images/toolbar/open.png");
  background-repeat: no-repeat;
  background-size: 80% 10%;
  background-position: 50% 50%;
}
#toolbar > div:nth-child(2) button {
  box-shadow: 0px 0px 1px 0px #888888;
  width: 32px;
  height: 32px;
  float: left;
}
#toolbar > div:nth-child(2) button {
  background-size: 50% 50%;
  background-position: 50% 50%;
  background-repeat: no-repeat;
}
#toolbar > div:nth-child(2) button:nth-child(1) {
  background-image: url("../assets/images/toolbar/point.png");
}
#toolbar > div:nth-child(2) button:nth-child(2) {
  background-image: url("../assets/images/toolbar/line.png");
}
#toolbar > div:nth-child(2) button:nth-child(3) {
  background-image: url("../assets/images/toolbar/circle.png");
}
#toolbar > div:nth-child(2) button:nth-child(4) {
  background-image: url("../assets/images/toolbar/polygon.png");
}
#toolbar > div:nth-child(2) button:nth-child(5) {
  background-image: url("../assets/images/toolbar/edit.png");
}
#toolbar > div:nth-child(2) button:nth-child(6) {
  background-image: url("../assets/images/toolbar/clear.png");
}
#toolbar > div:nth-child(2) button:nth-child(7) {
  background-image: url("../assets/images/toolbar/distance.png");
}
#toolbar > div:nth-child(2) button:nth-child(8) {
  background-image: url("../assets/images/toolbar/area.png");
}
#toolbar > div:nth-child(2) button:nth-child(9) {
  background-image: url("../assets/images/toolbar/arrow.png");
}
#toolbar > div:nth-child(2) button:nth-child(10) {
  background-image: url("../assets/images/toolbar/move.png");
}
#toolbar > div:nth-child(2) button:nth-child(11) {
  background-image: url("../assets/images/toolbar/search.png");
}
#toolbar > div:nth-child(2) button:nth-child(12) {
  background-image: url("../assets/images/toolbar/click.png");
}
#toolbar > div:nth-child(2) button:nth-child(13) {
  background-image: url("../assets/images/toolbar/layers.png");
}
.fullscreen {
  background-image: url("../assets/images/toolbar/fullscreen.png");
}
.cancel-fullscreen {
  background-image: url("../assets/images/toolbar/c-fullscreen.png");
}
</style>
<style >
.ol-popup-closer{
  text-decoration: none;
  position: absolute;
  top: 2px;
  right: 8px;
}
.ol-popup-closer{
  color:red;
  text-decoration:none;
}
.ol-popup-closer:after{
  content: "✖";
}
.ol-popup-measure{
  padding:5px 25px 5px 20px;
  position: absolute;
  opacity: 1;
  top:-50px;
  left:-30px;
  box-shadow: 0 1px 4px rgba(0,0,0,0.2);
  background-color: white;
  border-radius: 10px;
  border: 1px solid #cccccc;
  font-size:12px;
}
.ol-popup-measure:after, .ol-popup-measure:before {
  top: 100%;
  border: solid transparent;
  content: " ";
  height: 0;
  width: 0;
  position: absolute;
  pointer-events: none;
}
.ol-popup-measure:after {
  border-top-color: white;
  border-width: 10px;
  left: 30px;
  margin-left: -10px;
}
.ol-popup-measure:before {
  border-top-color: #cccccc;
  border-width: 11px;
  left: 30px;
  margin-left: -11px;
}
</style>
