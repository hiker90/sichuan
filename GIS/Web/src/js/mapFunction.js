import { Map,View,Feature, Overlay } from 'ol';
import { XYZ , Vector as VectorSource ,Cluster as ClusterSource} from 'ol/source';
import {Tile as TileLayer,Vector as VectorLayer} from 'ol/layer';
import TileGrid from 'ol/tilegrid/TileGrid';
import { get as olGet,Projection } from 'ol/proj';
import * as olControl from 'ol/control';
import {Draw as olDraw,Select,Modify} from 'ol/interaction';
import * as olSphere from 'ol/sphere';
import { Circle as CircleStyle, Fill, Stroke, Style, Icon, Text } from 'ol/style';
import GeoJSON from 'ol/format/GeoJSON';
import {Polygon, MultiLineString,Point,Cricle} from 'ol/geom';
import {register} from 'ol/proj/proj4';import {unByKey} from 'ol/Observable';
import proj4 from 'proj4';
import axios from 'axios';

import { mapConfig } from './mapConfig'
import {featureDictionary} from "./featureDictionary"

//全局变量
let _map = null;
//绘图
let _draw = null;
let _drawLayer = null;
let _drawSource = null;
let _drawSelect = null;
let _drawModify = null;
let _drawDeleteOverlay = null;
//测量
let _measure = null;
let _measureLayer = null;
let _measureSource = null;
//点击要素事件
let _mapClick;
//点击查找桩号事件
let _mapSearchClick;

let _featureOverlay = null;

let _mapLayers = [];
let _ygyxLayers = [];
let _areaLayers = [];
let _featureLayers = [];
let _searchLayers = [];

//默认样式
let defaultFillStyle = new Fill({               //填充样式
    color: 'rgba(51, 102, 153, 0.2)'
});
let defaultStrokeStyle = new Stroke({           //线样式
    color: 'rgba(51, 102, 153, 1)',
    width: 2
});
let defaultCircleStyle = new CircleStyle({
    radius: 5,
    stroke: new Stroke({
        color: 'rgba(51, 102, 153, 1)',
    }),
    fill: new Fill({
        color: 'rgba(51, 102, 153, 0.2)',
    }),
});
let defaultTextStyle = function(text){
    return new Text({
        text: text,
        font: '12px Microsoft YaHei',
        fill: new Fill({
            color: "white"
        })
})};
let defaultIconStyle = function(icon){
    return new Icon({
        scale: 0.5,
        size: [32, 32],
        src: icon,
    })
}


/**
 * 初始化Map
 */
function init() {
    let layerPromises = [];

    mapConfig.layers.forEach((item, index, arr) => {
        layerPromises.push(new Promise((resolve, reject) => {
            let serviceUrl = mapConfig.service + "/proxy/server/" + item.proxyID + "/10913";
            axios.get(serviceUrl, { params: { f: "json" } }).then(res => {
                let layerInfo = res.data;
                let title = item.title;

                // 地图切片服务地址
                let tileUrl = serviceUrl + "/tile/{z}/{y}/{x}";

                let projection = layerInfo.spatialReference.wkid;
                
                if (projection == "4490") {
                    proj4.defs("EPSG:4490","+proj=longlat +ellps=GRS80 +no_defs");
                    register(proj4)
                    projection = olGet("EPSG:" + projection);
                    projection.setExtent([67.23511268198499,-1.8285199552774745,139.62805013358604,58.22014327347277])
                    projection.setWorldExtent([67.23511268198499,-1.8285199552774745,139.62805013358604,58.22014327347277])
                } else{ 
                    // 地图坐标
                    projection = olGet("EPSG:" + projection);
                 }
                //范围
                let extent = [
                    layerInfo.initialExtent.xmin,
                    layerInfo.initialExtent.ymin,
                    layerInfo.initialExtent.xmax,
                    layerInfo.initialExtent.ymax
                ];
                //分辨率
                let resolutions = [];

                layerInfo.tileInfo.lods.forEach((lod, i, arr) => {
                    resolutions.push(lod.resolution)
                })
                // 坐标原点
                let origin = [layerInfo.tileInfo.origin.x, layerInfo.tileInfo.origin.y];
                //切片大小
                let tileSize = [layerInfo.tileInfo.rows, layerInfo.tileInfo.cols];

                if (index == 0) {
                    let view = new View({
                        center: mapConfig.viewCenter,
                        resolutions: resolutions,
                        resolution: mapConfig.viewResolution,
                        projection: mapConfig.viewProjection,
                        extent: extent
                    });

                    //创建地图
                    _map = new Map({
                        target: mapConfig.target,
                        controls: olControl.defaults({ attributionOptions: { collapsed: false } }),
                        view: view,
                    });
                }

                let tileGrid = new TileGrid({
                    tileSize: tileSize,
                    origin: origin,
                    extent: extent,
                    resolutions: resolutions
                });
                let tileArcGISXYZ = new XYZ({
                    tileGrid: tileGrid,
                    projection: projection,
                    url: tileUrl,
                });

                let layer = new TileLayer({
                    title: title,
                    source: tileArcGISXYZ,
                    zIndex: item.zIndex,
                    maxResolution: item.maxResolution,
                    minResolution: item.minResolution,
                });

                resolve(layer)
            }).catch(err => {
                console.log(err)
            })
        }))
    })

    return Promise.all(layerPromises).then(layers => {
        //添加图层
        layers.forEach((item, index, arr) => {
            _map.addLayer(item);
            _mapLayers.push(item);
        })
        //添加控件
        let scaleControl = new olControl.ScaleLine();
        _map.addControl(scaleControl);

        //要素信息覆盖层
        _featureOverlay = new Overlay({
            element:document.getElementById('popup'),
            autoPan:true,
            autoPanAnimation: {
                duration: 250,
            }
        })
        
        _map.addOverlay(_featureOverlay);
        let closer = document.getElementById('popup-closer')
        closer.onclick = function(){
            _featureOverlay.setPosition(undefined);
            closer.blur();
            return false;
        }

        //删除绘制要素覆盖层
        _drawDeleteOverlay = new Overlay({
            element:document.getElementById('popup-delete'),
            autoPan:true,
            autoPanAnimation: {
                duration: 250,
            }
        })
        _map.addOverlay(_drawDeleteOverlay);

        return _map;
    })
}


function bindMapClick(){
    //地图点击事件
    _mapClick = _map.on('singleclick',e=>{ 
        _map.forEachFeatureAtPixel(e.pixel,(feature)=>{
            let coordinate = e.coordinate;
            let properties = feature.getProperties()
            //点要素
            if(properties.features){
                let features = properties.features
                if(features.length==1){
                    properties = features[0].getProperties();
                    let content = "";
                    for(let key in properties){
                        if(featureDictionary[key]){
                            content+= "<p>"+featureDictionary[key]+":"+properties[key]+"</p>";
                        }
                    }
                    if(content!=""){
                        document.getElementById('popup-content').innerHTML=content;
                        _featureOverlay.setPosition(coordinate);
                    }
                }
            //线要素
            }else{
                let content = "";
                for(let key in properties){
                    if(featureDictionary[key]){
                        content+= "<p>"+featureDictionary[key]+":"+properties[key]+"</p>";
                    }
                }
                if(content!=""){
                    document.getElementById('popup-content').innerHTML=content;
                    _featureOverlay.setPosition(coordinate);
                }
            }
        })
    });
}
function removeMapClick(){ 
    //地图点击事件
    unByKey(_mapClick);
    _mapClick = null;
}
/**
 * 绘图
 */
function draw(type) {
    if (!_drawLayer) {
        _drawSource = new VectorSource({ wrapX: false });
        _drawLayer = new VectorLayer({
            source: _drawSource,
            zIndex: 5,
            style: new Style({
                fill: defaultFillStyle,
                stroke: defaultStrokeStyle,
                image: defaultCircleStyle
            }),
        });

        _map.addLayer(_drawLayer);
    }

    if (_draw) {
        _map.removeInteraction(_draw);
    }
    _draw = new olDraw({
        source: _drawSource,
        type: type,
        snapTolerance: 20,
        style: new Style({
            fill: defaultFillStyle,
            stroke: new Stroke({
                color: 'rgba(51, 102, 153, 1)',
                lineDash: [10, 10],
                width: 2,
            }),
            image: defaultCircleStyle
        }), 
    });

    _map.addInteraction(_draw);
}

/**
 * 清除绘图
 */
function clearDraw() {
    if(_drawSource){
        _drawSource.clear();
    }
}

/**
 * 取消绘图
 */
function cancelDraw() {
    if (_draw) {
        _map.removeInteraction(_draw);
    }
}

/**
 * 修改绘图
 */
function drawModify(){
    cancelDraw();
    _drawSelect = new Select({
        layers:[_drawLayer]
    });
    _map.addInteraction(_drawSelect);
    
    _drawModify = new Modify({features:_drawSelect.getFeatures()});
    _map.addInteraction(_drawModify);

    _drawSelect.on("select",function(e){ 
        if(e.selected.length>0){
            let feature = e.selected[0];
             
            let coordinate = [feature.getGeometry().getExtent()[2],feature.getGeometry().getExtent()[3]]
            _drawDeleteOverlay.setPosition(coordinate);
            let closer = document.getElementById('popup-delete-closer')
            closer.onclick = function(){
                _drawSource.removeFeature(feature);
                _drawDeleteOverlay.setPosition(undefined);
                closer.blur();
                return false;
            }
        }else{
            _drawDeleteOverlay.setPosition(undefined);
        } 
         
    })
    _drawModify.on("modifyend",function(e){
        let feature = e.features.item(0);
        let coordinate = [feature.getGeometry().getExtent()[2],feature.getGeometry().getExtent()[3]];
        _drawDeleteOverlay.setPosition(coordinate);
    })
   
    _drawSelect.setActive(true);
    _drawModify.setActive(true);
}

/**
 * 取消修改
 */
function cancelModify(){
    _map.removeInteraction(_drawSelect);
    _map.removeInteraction(_drawModify); 
    _drawDeleteOverlay.setPosition(undefined);
}

/**
 * 测距 测面积
 */
function measure(type) {
        let measureType = (type == "Distance" ? "LineString" : "Polygon");
        if (!_measureLayer) {
            _measureSource = new VectorSource({ wrapX: false });
            _measureLayer = new VectorLayer({
                source: _measureSource,
                zIndex: 5,
                style: new Style({
                    fill: defaultFillStyle,
                    stroke: defaultStrokeStyle,
                    image: defaultCircleStyle
                }),
            });
    
            _map.addLayer(_measureLayer);
        }
    
        if (_measure) {
            _map.removeInteraction(_measure);
        }
        _measure = new olDraw({
            source: _measureSource,
            type: measureType,
            snapTolerance: 20,
            style: new Style({
                fill: defaultFillStyle,
                stroke: new Stroke({
                    color: 'rgba(51, 102, 153, 1)',
                    lineDash: [10, 10],
                    width: 2,
                }),
                image: defaultCircleStyle
            }), 
        });
    
        _map.addInteraction(_measure);

        let listener;
        _measure.on('drawstart', function (evt) {

            let popMeasure = document.createElement("div");
            popMeasure.className = "ol-popup-measure";
            let popMeasureClose = document.createElement("a")
            popMeasureClose.className = "ol-popup-closer";
            let popMeasureContent = document.createElement("div");
            popMeasure.appendChild(popMeasureClose);
            popMeasure.appendChild(popMeasureContent);
            
            //测量信息覆盖层
            let _measureOverlay = new Overlay({
                element:popMeasure,
                offset:[0,-7]
            }) 
            _map.addOverlay(_measureOverlay);

            popMeasureClose.onclick = function(){
                _map.removeOverlay(_measureOverlay);
                _measureSource.removeFeature(evt.feature);
                popMeasureClose.blur();
                return false;
            }
            listener = evt.feature.getGeometry().on('change',function(e){
                let geom = e.target;
                if (type == "Distance") {
                    let distance = olSphere.getLength(evt.feature.getGeometry(), { projection: new Projection(mapConfig.viewProjection) });
                    if(distance>0){
                        popMeasureContent.innerHTML = distance>100?Math.round((distance / 1000) * 100) / 100 + "千米":Math.round(distance * 100) / 100 + "米";
                        _measureOverlay.setPosition(geom.getLastCoordinate());
                    }
                } else {
                    let area = olSphere.getArea(evt.feature.getGeometry(), { projection: new Projection(mapConfig.viewProjection) });
                    if(area>0){
                        popMeasureContent.innerHTML = area > 10000?Math.round((area / 1000000) * 100) / 100 + '平方千米':Math.round(area * 100) / 100 +  '平方米';
                        _measureOverlay.setPosition(geom.getInteriorPoint().getCoordinates());
                    }
                }
            })
        });
        _measure.on('drawend', ()=>{
            unByKey(listener);
        });
}

function cancelMeasure(){
    if (_measure) {
        _map.removeInteraction(_measure);
    }
}
/**
 * 获取所有的layer title
 */
function getMapLayers() {
    let result = [];
    if (_map != null) {
        _mapLayers.forEach((item, index, arr) => {
            result.push(item.get("title"));
        })
    }
    return result;
}

/**
 * 设置显示图层
 */
function showMapLayers(layers) {
    _mapLayers.forEach(item => {
        let show = false;
        layers.forEach(i => {
            if (item.get("title") == i) {
                show = true
            }
        })
        item.set("visible", show);
    })
}

/**
 * 加载遥感影像
 */
function loadRemoteSensingImage(checkedNodes) {
    //删除多余的图层
    _ygyxLayers.forEach(item => {
        let exist = false;
        checkedNodes.forEach(i => {
            if (item.get("title") == i.key) {
                exist = true;
            }
        })
        if (!exist) {
            _map.removeLayer(item);
        }
    })
    return new Promise((resolve, reject) => {
        let loadPromise = []
        checkedNodes.forEach(node => {
            let exist = false;
            _map.getLayers().forEach(item => {
                  if (item.get("title") == node.key) {
                    exist = true;
                }
            })

            if (!exist) {
                loadPromise.push(new Promise((res, rej) => {
                    axios.get(node.url, { params: { f: "json" } }).then(res => {
                        let layerInfo = res.data;
                        let title = node.key; 
                        // 地图切片服务地址
                        let tileUrl = node.url + "/tile/{z}/{y}/{x}";
                         
                        let projection = layerInfo.spatialReference.wkid;
                        if (projection == "4490") {
                            proj4.defs("EPSG:4490","+proj=longlat +ellps=GRS80 +no_defs");
                            register(proj4)
                            projection = new Projection({
                                code:"EPSG:4490",
                                Extent:[67.23511268198499,-1.8285199552774745,139.62805013358604,58.22014327347277],
                            })
                        } 
                        else{
                            // 地图坐标
                            projection = olGet("EPSG:" + projection);
                        }
                        //范围
                        let extent = [
                            layerInfo.initialExtent.xmin,
                            layerInfo.initialExtent.ymin,
                            layerInfo.initialExtent.xmax,
                            layerInfo.initialExtent.ymax
                        ];
                        //分辨率
                        let resolutions = [];

                        layerInfo.tileInfo.lods.forEach((lod, i, arr) => {
                            resolutions.push(lod.resolution)
                        })
                        // 坐标原点
                        let origin = [layerInfo.tileInfo.origin.x, layerInfo.tileInfo.origin.y];
                        //切片大小
                        let tileSize = [layerInfo.tileInfo.rows, layerInfo.tileInfo.cols];

                        let tileGrid = new TileGrid({
                            tileSize: tileSize,
                            origin: origin,
                            extent: extent,
                            resolutions: resolutions
                        });
                        let tileArcGISXYZ = new XYZ({
                            tileGrid: tileGrid,
                            projection: projection,
                            url: tileUrl,
                        });

                        let layer = new TileLayer({
                            title: title,
                            source: tileArcGISXYZ,
                            zIndex: 2
                        });
                        _map.addLayer(layer);
                         _ygyxLayers.push(layer);
                    }).catch(err => {
                        console.log(err)
                    }).finally(() => {
                        res()
                    })
                }))
            }
        })

        Promise.all(loadPromise).then(() => {
            resolve();
        })
    })
}
/**
 * 加载行政区划
 */
function loadArea(checkedNodes) {
    //删除多余的图层
    _areaLayers.forEach(item => {
        let exist = false;
        checkedNodes.forEach(i=> {
            if (item.get("title") == i.key) {
                exist = true;
            }
        })
        if (!exist) {
            _map.removeLayer(item);
        }
    })

    return new Promise((resolve, reject) => {
        let loadPromise = []
        checkedNodes.forEach(node=> {
            let exist = false;
            _map.getLayers().forEach(item=> {
                if (item.get("title") == node.key) {
                    exist = true;
                }
            })

            if (!exist) {
                loadPromise.push(new Promise((res, rej) => {
                    let serviceUrl = mapConfig.service + "/proxy/server/" + mapConfig.areaProxyID + "/10913/find";

                    axios.get(serviceUrl, { params: { searchText: node.xzbm, layers: 0, f: "pjson" } }).then(res => {
                        let polygonFeature = new Feature(new Polygon(res.data.results[0].geometry.rings));
                        let name = res.data.results[0].attributes.XZQHMC;
                        //实例化一个矢量图层Vector作为绘制层
                        let source = new VectorSource({
                            features: [
                                polygonFeature
                            ]
                        });
                        let vectorLayer = new VectorLayer({
                            title: node.key,
                            zIndex: 3,
                            source: source,
                            style: new Style({
                                fill: defaultFillStyle,
                                stroke: defaultStrokeStyle,
                                text: defaultTextStyle(node.title),
                            })
                        });
                        _map.addLayer(vectorLayer)
                        _areaLayers.push(vectorLayer);
                    }).catch(err => {
                        console.log(err)
                    }).finally(() => {
                        res()
                    })
                }))
            }
        })

        Promise.all(loadPromise).then(() => {
            resolve();
        })
    })
}

/**
 * 加载要素
 */
function loadFeature(checkedNodes) {
    //删除多余的图层
    _featureLayers.forEach(item=> {
        let exist = false;
        checkedNodes.forEach(i => {
            if (item.get("title") == i.key) {
                exist = true;
            }
        })
        if (!exist) {
            _map.removeLayer(item);
        }
    })

    return new Promise((resolve, reject) => {
        let loadPromise = []
        checkedNodes.forEach(node=> {
            let exist = false;
            _map.getLayers().forEach(item => {
                if (item.get("title") == node.key) {
                    exist = true;
                }
            })

            if (!exist) {
                loadPromise.push(new Promise((res, rej) => {
                    let serviceUrl = mapConfig.service + "/proxy/server/" + node.proxyID + "/10913/0/query";
                    let where = "";
                    if (node.where != null) {
                        for (let key in node.where) {
                            where += key + "=" + node.where[key] + "&"
                        }
                        where = where.substr(0, where.length - 1)
                    } else {
                        where = "1=1"
                    }

                    let outFields = ""
                    if (node.outFields != null) {
                        node.outFields.forEach((item, index) => {
                            outFields += item + ","
                        })
                        outFields = outFields.substr(0, outFields.length - 1)
                    }

                    axios.get(serviceUrl, { params: { where: where, outFields: outFields, f: "geojson" } }).then(res => {
                        let features = new GeoJSON().readFeatures(res.data);

                        let vectorSource = new VectorSource({
                            features: features
                        });

                        let style = null;

                        if (node.type == "Polyline") {
                            style = new Style({
                                stroke: new Stroke({
                                    color: node.color,
                                    width: 2,
                                }),
                            });
                        } else {
                            if (node.icon) {
                                style = new Style({
                                    image: defaultIconStyle(node.icon)
                                })
                            } else {
                                style = new Style({
                                    image: defaultCircleStyle,
                                })
                            }
                            var clusterSource = new ClusterSource({
                                distance:15,
                                source : vectorSource
                            });
                            vectorSource = clusterSource;
                        }
                        let featureLayer = new VectorLayer({
                            title: node.key,
                            zIndex: 5,
                            source: vectorSource,
                            style: style
                        });
                        _map.addLayer(featureLayer);
                        _featureLayers.push(featureLayer);
                    }).catch(err => {
                        console.log(err)
                    }).finally(() => {
                        res();
                    })
                }))
            }
        })

        Promise.all(loadPromise).then(() => {
            resolve();
        })
    })
}

/**
 * 桩号查找
 */
function searchZh(lxbm,zh){ 
    return new Promise((resolve,reject)=>{
       let serviceUrl = process.env.NODE_ENV=="development"?"http://localhost/proxy/proxy/server/DAA94562F1D84F328FA85C470B147724/10913?LXBM="+lxbm+"&ZH="+zh:"http://172.16.22.196/proxy/server/DAA94562F1D84F328FA85C470B147724/10913?LXBM="+lxbm+"&ZH="+zh;
       axios.get(serviceUrl, {}).then(res => {
        let content = eval('(' + res.data.content + ')');
            let linestring =content.lxzb.match(/\d+\.\d+ \d+\.\d+/g);

            let coordinates = [];
            linestring.forEach(item=>{
                coordinates.push(item.split(" "));
            })  

            if(coordinates.length>0){
                //设置中心点 
                let center = coordinates[parseInt(coordinates.length/2)]
                _map.getView().setCenter(center);
            }
            
            let multiLineString = [];
            for(let i=0;i<coordinates.length-1;i++){
                multiLineString.push([coordinates[i],coordinates[i+1]]);
            }
            let vectorSource = new VectorSource({
                features:[
                    new Feature({ 
                        geometry:new MultiLineString(multiLineString),
                       // ZH:item.zh,
                       // LXBM:item.lxbm,
                       // LXMC:item.lxmc
                    })
                ]
            }
            );
            let featureLayer = new VectorLayer({ 
                zIndex: 5,
                source: vectorSource, 
                style : new Style({
                    stroke: defaultStrokeStyle,
                })
            }); 
            _map.addLayer(featureLayer);
            _searchLayers.push(featureLayer)
            resolve();
        });
    })
}

/**
 * 点击查找桩号
 */
function bindMapSearchClick(){
    _mapSearchClick = _map.on('singleclick',e=>{
        return new Promise((resolve,reject)=>{
            var coordinate = e.coordinate;
            let serviceUrl = process.env.NODE_ENV=="development"?"http://localhost/proxy/proxy/server/2EB4075481284E79A5518D52B3FAD0AC/10913?X="+coordinate[0]+"&Y="+coordinate[1]:"http://172.16.22.196/proxy/server/2EB4075481284E79A5518D52B3FAD0AC/10913?X="+coordinate[0]+"&Y="+coordinate[1];
            axios.get(serviceUrl, {}).then(res => {
                if(res.data.content){
                    let content = eval('(' + res.data.content + ')');
                    
                    content.forEach(item=>{
                        let linestring =item.lxzb.match(/\d+\.\d+ \d+\.\d+/g);
        
                        let coordinates = [];
                        linestring.forEach(i=>{
                            coordinates.push(i.split(" "));
                        })  
                        
                        let multiLineString = [];
                        for(let i=0;i<coordinates.length-1;i++){
                            multiLineString.push([coordinates[i],coordinates[i+1]]);
                        }
                        let feature = new Feature({
                            geometry:new MultiLineString(multiLineString),
                            ZH:item.zh,
                            LXBM:item.lxbm,
                            LXMC:item.lxmc
                        })
                        let vectorSource = new VectorSource({
                            features:[
                                feature
                            ]
                        });
                        let featureLayer = new VectorLayer({ 
                            zIndex: 5,
                            source: vectorSource, 
                            style : new Style({
                                stroke: defaultStrokeStyle,
                            })
                        }); 
                        _map.addLayer(featureLayer);
                        _searchLayers.push(featureLayer)
                        resolve();
                    })  
                }
             });
         })
    })
}
/**
 * 移除点击查找桩号事件
 */
function removeMapSearchClick(){
    unByKey(_mapSearchClick)
}

function removeSearchLayers(){
    _searchLayers.forEach(item=>{
        _map.removeLayer(item)
    })
}

 export{
    init,

    getMapLayers,
    showMapLayers,

    draw,
    clearDraw,
    cancelDraw,
    drawModify,
    cancelModify,

    measure,
    cancelMeasure,
    
    loadRemoteSensingImage,
    loadArea,
    loadFeature,

    bindMapClick,
    removeMapClick,

    searchZh,
    bindMapSearchClick,
    removeMapSearchClick,
    removeSearchLayers
}