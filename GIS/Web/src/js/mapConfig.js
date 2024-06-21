let mapConfig = {
    //服务地址
    service:"http://172.16.21.150:50001",
    layers:[
            {
                title:"中国地图",
                proxyID:"884C39DCA24D4D60B411214552BBE15A",
                zIndex:0,
                maxResolution: 0.17578125000000006,
                minResolution:2.682209014892579E-6 
            },
        ],
    //目标元素ID
    target:"map",
    //View中心点坐标 成都
    viewCenter:[104.07, 30.67],
    //坐标系
    viewProjection:"EPSG:4490",
    //默认缩放级别
    viewResolution:0.010986328125000003,
    
    //行政区划代理ID
    areaProxyID:"384CC385C9F346F2B2D86C21856DEE93"
}

export {mapConfig}