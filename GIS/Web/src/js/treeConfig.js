let featurePath =  process.env.NODE_ENV=="development"? "/static/images/feature/":"./static/images/feature/"
export default {
    treeNodes: [
        {
            title: "遥感影像",
            key: "yaoganditu",
            children: [
                {
                    title: "成都市",
                    key: "ygdt_chengdushi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scchengdu/MapServer"
                },
                {
                    title: "自贡市",
                    key: "ygdt_zigongshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/zigong/MapServer"
                },
                {
                    title: "攀枝花市",
                    key: "ygdt_panzhihuashi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scpanzhihua/MapServer"
                },
                {
                    title: "泸州市",
                    key: "ygdt_luzhoushi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scluzhou/MapServer"
                },
                {
                    title: "德阳市",
                    key: "ygdt_deyangshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scdeyang/MapServer"
                },
                {
                    title: "绵阳市",
                    key: "ygdt_mianyangshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scmianyang/MapServer"
                },
                {
                    title: "广元市",
                    key: "ygdt_guangyuanshi",
                    type: "ygyx"
                },
                {
                    title: "遂宁市",
                    key: "ygdt_suiningshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scsuining/MapServer"
                },
                {
                    title: "内江市",
                    key: "ygdt_neijiangshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scneijiang/MapServer"
                },
                {
                    title: "乐山市",
                    key: "ygdt_leshanshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scleshan/MapServer"
                },
                {
                    title: "南充市",
                    key: "ygdt_nanchongshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scnanchong/MapServer"
                },
                {
                    title: "眉山市",
                    key: "ygdt_meishanshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scmeishan/MapServer"
                },
                {
                    title: "宜宾市",
                    key: "ygdt_yibinshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scyibin/MapServer"
                },
                {
                    title: "广安市",
                    key: "ygdt_guanganshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scguangan/MapServer"
                },
                {
                    title: "达州市",
                    key: "ygdt_dazhoushi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scdazhou/MapServer"
                },
                {
                    title: "雅安市",
                    key: "ygdt_yaanshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scyaan/MapServer"
                },
                {
                    title: "巴中市",
                    key: "ygdt_bazhongshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scbazhong/MapServer"
                },
                {
                    title: "资阳市",
                    key: "ygdt_ziyangshi",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scziyang/MapServer"
                },
                {
                    title: "阿坝藏族羌族自治州",
                    key: "ygdt_abazhou",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scabazhou/MapServer"
                },
                {
                    title: "甘孜藏族自治州",
                    key: "ygdt_ganzizhou",
                    type: "ygyx"
                },
                {
                    title: "凉山彝族自治州",
                    key: "ygdt_liangshanzhou",
                    type: "ygyx",
                    url: "http://172.16.21.202:6080/arcgis/rest/services/scliangshanzhou/MapServer"
                },
            ]
        },
        {
            title: "行政区划",
            key: "xingzhengquhua",
            children: [
                {
                    title: "成都市",
                    key: "xzqh_chengdushi",
                    xzbm: "510100",
                    type: "xzqh"
                },
                {
                    title: "自贡市",
                    key: "xzqh_zigongshi",
                    xzbm: "510300",
                    type: "xzqh"
                },
                {
                    title: "攀枝花市",
                    key: "xzqh_panzhihuashi",
                    xzbm: "510400",
                    type: "xzqh"
                },
                {
                    title: "泸州市",
                    key: "xzqh_luzhoushi",
                    xzbm: "510500",
                    type: "xzqh"
                },
                {
                    title: "德阳市",
                    key: "xzqh_deyangshi",
                    xzbm: "510600",
                    type: "xzqh"
                },
                {
                    title: "绵阳市",
                    key: "xzqh_mianyangshi",
                    xzbm: "510700",
                    type: "xzqh"
                },
                {
                    title: "广元市",
                    key: "xzqh_guangyuanshi",
                    xzbm: "510800",
                    type: "xzqh"
                },
                {
                    title: "遂宁市",
                    key: "xzqh_suiningshi",
                    xzbm: "510900",
                    type: "xzqh"
                },
                {
                    title: "内江市",
                    key: "xzqh_neijiangshi",
                    xzbm: "511000",
                    type: "xzqh"
                },
                {
                    title: "乐山市",
                    key: "xzqh_leshanshi",
                    xzbm: "511100",
                    type: "xzqh"
                },
                {
                    title: "南充市",
                    key: "xzqh_nanchongshi",
                    xzbm: "511300",
                    type: "xzqh"
                },
                {
                    title: "眉山市",
                    key: "xzqh_meishanshi",
                    xzbm: "511400",
                    type: "xzqh"
                },
                {
                    title: "宜宾市",
                    key: "xzqh_yibinshi",
                    xzbm: "511500",
                    type: "xzqh"
                },
                {
                    title: "广安市",
                    key: "xzqh_guanganshi",
                    xzbm: "511600",
                    type: "xzqh"
                },
                {
                    title: "达州市",
                    key: "xzqh_dazhoushi",
                    xzbm: "511700",
                    type: "xzqh"
                },
                {
                    title: "雅安市",
                    key: "xzqh_yaanshi",
                    xzbm: "511800",
                    type: "xzqh"
                },
                {
                    title: "巴中市",
                    key: "xzqh_bazhongshi",
                    xzbm: "511900",
                    type: "xzqh"
                },
                {
                    title: "资阳市",
                    key: "xzqh_ziyangshi",
                    xzbm: "512000",
                    type: "xzqh"
                },
                {
                    title: "阿坝藏族羌族自治州",
                    key: "xzqh_abazhou",
                    xzbm: "513200",
                    type: "xzqh"
                },
                {
                    title: "甘孜藏族自治州",
                    key: "xzqh_ganzizhou",
                    xzbm: "513300",
                    type: "xzqh"
                },
                {
                    title: "凉山彝族自治州",
                    key: "xzqh_liangshanzhou",
                    xzbm: "513400",
                    type: "xzqh"
                },
            ]
        },
        {
            title: "公路",
            key: "gonglu",
            children: [
                {
                    title: "按行政等级",
                    key: "axzdj",
                    children: [
                        {
                            title: "国道",
                            key: "guodao",
                            proxyID: "E86D71B8E2A848E9AA8FAB04C0C7F71D",
                            type: "Polyline",
                            color: "#FF8C24",
                            outFields:["LDBM","LXBM","LXMC"]
                        },
                        {
                            title: "省道",
                            key: "shengdao",
                            proxyID: "C124B31AD6544F91B653DD5318351FBA",
                            type: "Polyline",
                            color: "#FFFF53",
                            outFields:["LDBM","LXBM","LXMC"]
                        },
                        {
                            title: "县道",
                            key: "xiandao",
                            proxyID: "EFA53A8208C240CFB2FCADBFE04B96DD",
                            type: "Polyline",
                            color: "#D1EC68",
                            outFields:["LDBM","LXBM","LXMC"]
                        },
                        {
                            title: "乡道",
                            key: "xiangdao",
                            proxyID: "9419027C39D941D1A08EFA8708491771",
                            type: "Polyline",
                            color: "#E0DCF5",
                            outFields:["LDBM","LXBM","LXMC"]
                        },
                        {
                            title: "村道",
                            key: "cundao",
                            proxyID: "19821E5F9D924C94955A803455D4F875",
                            type: "Polyline",
                            color: "#C8C8C8",
                            outFields:["LDBM","LXBM","LXMC"]
                        },
                    ],
                },
                {
                    title: "按技术等级",
                    key: "glqt",
                    children: [
                        {
                            title: "高速道路",
                            key: "gaosudaolu",
                            proxyID: "BCF18472FC544D45B9C442A9E028AD37",
                            type: "Polyline",
                            color: "#E71818",
                            outFields:["LDBM","LXBM","LXMC"]
                        },
                        {
                            title: "城市道路",
                            key: "chengshidaolu", //500 Error: Service GLJ/Road_D/MapServer not found
                            proxyID: "5E33EE866219419C959D7EB0E15CBB06",
                            type: "Polyline",
                            color: "#0809BC",
                            outFields:["LDBM","LXBM","LXMC"]
                        },
                        {
                            title: "专道",
                            key: "zhuandao",
                            proxyID: "08184A305986402F8DCBDE586F3344A4",
                            type: "Polyline",
                            color: "#9CB478",
                            outFields:["LDBM","LXBM","LXMC"]
                        },
                    ],
                },
                {
                    title: "其他",
                    key: "gl_qita",
                    children: [
                        {
                            title: "拟建道路",
                            key: "nijiangonglu",
                            proxyID: "A2AE50FBA88B4C29A27294C03D27F33B",
                            type: "Polyline",
                            color: "#FF8C24",
                            outFields:["LDBM","LXBM","LXMC"]
                        },
                    ]
                }
            ],
        },
        {
            title: "公路附属配套",
            key: "gonglufushupeitao",
            children: [
                {
                    title: "服务区",
                    key: "fuwuqu",
                    proxyID: "68EF9C378A6A4500968B2F4201BAEC90",
                    type: "Point",
                    icon: featurePath+"fwq.png",
                    outFields:["FWQMC"]
                },
                {
                    title: "收费站",
                    key: "shoufeizhan",
                    proxyID: "E3A975CD30B247C5A5C39168FBB026EC",
                    type: "Point",
                    icon: featurePath+"sfz.png",
                    outFields:["SFZMC"]
                },
                {
                    title: "出入口",
                    key: "churukou",
                    proxyID: "792BA83540DA4E609365F6AC38B6A00C",
                    type: "Point",
                    icon: featurePath+"crk.png",
                    outFields:["CRKMC"]
                },
                {
                    title: "里程桩",
                    key: "lichengzhuang",
                    proxyID: "3B71513D8C08475C9A1CDFD227C17BCB",
                    type: "Point",
                    icon: featurePath+"lcz.png",
                    outFields:["LXMC","LCZBM","LCZBH"]
                },
            ]
        },
        {
            title: "公交站点及配套",
            key: "gongjiaozhandianjipeitao",
            children: [
                {
                    title: "枢纽",
                    key: "shuniu",
                    children: [
                        {
                            title: "火车站",
                            key: "huochezhan",
                            proxyID: "98B46E0529B84F7CA73784C6BAE0A5E9",
                            type: "Point",
                            icon: featurePath+"hcz.png",
                            outFields:["MC"]
                        },
                        {
                            title: "机场",
                            key: "jichang",
                            proxyID: "C88A236EBC07482787EAD8044DAECA78",
                            type: "Point",
                            icon: featurePath+"jc.png",
                            outFields:["MC"]
                        },
                    ]
                },
                {
                    title: "站点",
                    key: "zhandian",
                    children: [
                        {
                            title: "公交站",
                            key: "gongjiaozhan",
                            proxyID: "C033620CC6D1446FBD18AE8748B4DC90",
                            type: "Point",
                            icon: featurePath+"gjz.png",
                            where:{ZDLX:0},
                            outFields:["GJZDMC"]
                        },
                        {
                            title: "地铁、轻轨",
                            key: "ditieyuqinggui",
                            proxyID: "C033620CC6D1446FBD18AE8748B4DC90",
                            type: "Point",
                            icon: featurePath+"gjz.png",
                            where:{ZDLX:1},
                            outFields:["GJZDMC"]
                        },
                        {
                            title: "BRT专用站",
                            key: "BRTzhuanyongzhan",
                            proxyID: "C033620CC6D1446FBD18AE8748B4DC90",
                            type: "Point",
                            icon: featurePath+"gjz.png",
                            where:{ZDLX:2},
                            outFields:["GJZDMC"]
                        },
                        {
                            title: "有轨电车专用站",
                            key: "youguidianchezhuanyongzhan",
                            proxyID: "C033620CC6D1446FBD18AE8748B4DC90",
                            type: "Point",
                            icon: featurePath+"gjz.png",
                            where:{ZDLX:3},
                            outFields:["GJZDMC"]
                        },
                        {
                            title: "其他",
                            key: "zd_qt",
                            proxyID: "C033620CC6D1446FBD18AE8748B4DC90",
                            type: "Point",
                            icon: featurePath+"gjz.png",
                            where:{ZDLX:9},
                            outFields:["GJZDMC"]
                        },
                    ]
                },
                {
                    title: "线路",
                    key: "xianlu",
                    children: [
                        {
                            title: "公交线路",
                            key: "gongjiaoxianlu",
                            children: [
                                {
                                    title: "公交线路",
                                    key: "gjxl_gongjiaoxianlu",
                                    proxyID: "8EF84D911E634FD3B7965947DBB2AECA",
                                    type: "Polyline",
                                    color: "blue",
                                    where: { GJXLLX: 1 },
                                    outFields:["GJXLMC"]
                                },
                                {
                                    title: "地铁轻轨",
                                    key: "ditieqinggui",
                                    proxyID: "8EF84D911E634FD3B7965947DBB2AECA",
                                    type: "Polyline",
                                    color: "66ccff",
                                    where: { GJXLLX: 2 },
                                    outFields:["GJXLMC"]
                                },
                                {
                                    title: "磁悬浮",
                                    key: "cixuanfu",
                                    proxyID: "8EF84D911E634FD3B7965947DBB2AECA",
                                    type: "Polyline",
                                    color: "6666ff",
                                    where: { GJXLLX: 3 },
                                    outFields:["GJXLMC"]
                                },
                                {
                                    title: "航运轮渡",
                                    key: "hangyunlundu",
                                    proxyID: "8EF84D911E634FD3B7965947DBB2AECA",
                                    type: "Polyline",
                                    color: "9999ff",
                                    where: { GJXLLX: 4 },
                                    outFields:["GJXLMC"]
                                },
                                {
                                    title: "索道",
                                    key: "suodao",
                                    proxyID: "8EF84D911E634FD3B7965947DBB2AECA",
                                    type: "Polyline",
                                    color: "99ffcc",
                                    where: { GJXLLX: 5 },
                                    outFields:["GJXLMC"]
                                },
                                {
                                    title: "BRT(快速公交)",
                                    key: "kuaisugongjiao",
                                    proxyID: "8EF84D911E634FD3B7965947DBB2AECA",
                                    type: "Polyline",
                                    color: "green",
                                    where: { GJXLLX: 6 },
                                    outFields:["GJXLMC"]
                                },
                                {
                                    title: "有轨电车",
                                    key: "youguidianche",
                                    proxyID: "8EF84D911E634FD3B7965947DBB2AECA",
                                    type: "Polyline",
                                    color: "3366ff",
                                    where: { GJXLLX: 7 },
                                    outFields:["GJXLMC"]
                                },
                            ]
                        },
                        {
                            title: "客运线路",
                            key: "keyunxianlu",
                            proxyID: "098B7F602774431B80CD4F18D90991C1",
                            type: "Polyline",
                            color: "gray",
                            outFields:["KYXLMC"]
                        },
                    ]
                },
            ]
        },
        {
            title:"港口码头",
            key:"gangkoumatou",
            children:[
                {
                    title: "港口",
                    key: "gangkou",
                    proxyID: "E02343176A3244A6AF99408967D2A41D",
                    type: "Point",
                    icon: featurePath+"gk.png",
                    outFields:["MC"]
                },
                {
                    title: "码头",
                    key: "matou",
                    proxyID: "46D284E89A784B9890166BFD99D82D81",
                    type: "Point",
                    icon: featurePath+"mt.png",
                    outFields:["MC"]
                },
            ]
        },
        {
            title: "桥梁",
            key: "qiaoliang",
            children: [
                {
                    title: "按跨径区分",
                    key: "ql_ankuajingqufen",
                    children: [
                        {
                            title: "特大桥",
                            key: "tedaqiao",
                            proxyID: "658D81CCF674495587328BBE77BDF891",
                            type: "Point",
                            icon: featurePath+"ql.png",
                            where: {
                                AKJF: "1"
                            },
                            outFields:[
                                "QLMC"
                            ]
                        },
                        {
                            title: "大桥",
                            key: "daqiao",
                            proxyID: "658D81CCF674495587328BBE77BDF891",
                            type: "Point",
                            icon: featurePath+"ql.png",
                            where: {
                                AKJF: "2"
                            },
                            outFields:[
                                "QLMC"
                            ]
                        },
                        {
                            title: "中桥",
                            key: "zhongqiao",
                            proxyID: "658D81CCF674495587328BBE77BDF891",
                            type: "Point",
                            icon: featurePath+"ql.png",
                            where: {
                                AKJF: "3"
                            },
                            outFields:[
                                "QLMC"
                            ]
                        },
                        {
                            title: "小桥",
                            key: "xiaoqiao",
                            proxyID: "658D81CCF674495587328BBE77BDF891",
                            type: "Point",
                            icon: featurePath+"ql.png",
                            where: {
                                AKJF: "4"
                            },
                            outFields:[
                                "QLMC"
                            ]
                        },
                    ]
                },
                {
                    title: "按技术状况评定区分",
                    key: "ql_anjishuzhuangkuangpingdingqufen",
                    children: [
                        {
                            title: "一类",
                            key: "ql_yilei",
                            proxyID: "658D81CCF674495587328BBE77BDF891",
                            type: "Point",
                            icon: featurePath+"ql.png",
                            where: {
                                JSZKPD: "1"
                            },
                            outFields:[
                                "QLMC"
                            ]
                        },
                        {
                            title: "二类",
                            key: "ql_erlei",
                            proxyID: "658D81CCF674495587328BBE77BDF891",
                            type: "Point",
                            icon: featurePath+"ql.png",
                            where: {
                                JSZKPD: "2"
                            },
                            outFields:[
                                "QLMC"
                            ]
                        },
                        {
                            title: "三类",
                            key: "ql_sanlei",
                            proxyID: "658D81CCF674495587328BBE77BDF891",
                            type: "Point",
                            icon: featurePath+"ql.png",
                            where: {
                                JSZKPD: "3"
                            },
                            outFields:[
                                "QLMC"
                            ]
                        },
                        {
                            title: "四类",
                            key: "ql_silei",
                            proxyID: "658D81CCF674495587328BBE77BDF891",
                            type: "Point",
                            icon: featurePath+"ql.png",
                            where: {
                                JSZKPD: "4"
                            },
                            outFields:[
                                "QLMC"
                            ]
                        },
                        {
                            title: "五类",
                            key: "ql_wulei",
                            proxyID: "658D81CCF674495587328BBE77BDF891",
                            type: "Point",
                            icon: featurePath+"ql.png",
                            where: {
                                JSZKPD: "5"
                            },
                            outFields:[
                                "QLMC"
                            ]
                        },
                    ]
                },
            ]
        },
        {
            title: "隧道",
            key: "suidao",
            children: [
                {
                    title: "特长隧道",
                    key: "techangsuidao",
                    proxyID: "8F396649EF594094BB57D2FE77969CB8",
                    type: "Point",
                    icon: featurePath+"sd.png",
                    where: {
                        ACDFL: "1"
                    },
                    outFields:[
                        "SDMC",
                        "LXMC",
                    ]
                },
                {
                    title: "长隧道",
                    key: "changsuidao",
                    proxyID: "8F396649EF594094BB57D2FE77969CB8",
                    type: "Point",
                    icon: featurePath+"sd.png",
                    where: {
                        ACDFL: "2"
                    },
                    outFields:[
                        "SDMC",
                        "LXMC",
                    ]
                },
                {
                    title: "中隧道",
                    key: "zhongsuidao",
                    proxyID: "8F396649EF594094BB57D2FE77969CB8",
                    type: "Point",
                    icon: featurePath+"sd.png",
                    where: {
                        ACDFL: "3"
                    },
                    outFields:[
                        "SDMC",
                        "LXMC",
                    ]
                },
                {
                    title: "短隧道",
                    key: "duansuidao",
                    proxyID: "8F396649EF594094BB57D2FE77969CB8",
                    type: "Point",
                    icon: featurePath+"sd.png",
                    where: {
                        ACDFL: "4"
                    },
                    outFields:[
                        "SDMC",
                        "LXMC",
                    ]
                },
            ]

        },
        {
            title: "其他",
            key: "qita",
            children: [
                {
                    title: "乡镇点",
                    key: "xiangzhendian",
                    proxyID: "6DE60BC8170E44CA98B9A0564C2F8E95",
                    type: "Point",
                    icon: featurePath+"xc.png",
                    outFields:["XZMC"]
                },
                {
                    title: "自然村点",
                    key: "zirancundian",
                    proxyID: "00E6CDDE3840454D880884015EF516B7",
                    type: "Point",
                    icon: featurePath+"xc.png",
                    outFields:["JZCMC"]
                },
                {
                    title: "建制村",
                    key: "jianzhicun",
                    proxyID: "40AE004D7A574720BAE05E5FAE777603",
                    type: "Point",
                    icon: featurePath+"xc.png",
                    outFields:["JZCMC"]
                },
                {
                    title: "撤并建制村",
                    key: "chebingjianzhicun",
                    proxyID: "416FB1D464574C6591012C9C0FA06544",
                    type: "Point",
                    icon: featurePath+"xc.png",
                    outFields:["JZCMC"]
                },
                {
                    
                    title: "货运场站",
                    key: "huoyunchangzhan",
                    proxyID: "A469802DC4774C6DAA5B49E27494CE6F",
                    type: "Point",
                    icon: featurePath+"hy.png",
                    outFields:["MC"]
                },
                {
                    title: "维修企业",
                    key: "weixiuqiye",
                    proxyID: "1EAEFFDB47CB49BDA3FEACD242A68135",
                    type: "Point",
                    icon: featurePath+"wx.png",
                    outFields:["MC"]
                },
                {
                    title: "培训驾校",
                    key: "peixunjiaxiao",
                    proxyID: "65C898645F8947369C3BEBEA3A2820A7",
                    type: "Point",
                    icon: featurePath+"jx.png",
                    outFields:["MC"]
                },
              
                {
                    title: "治超监测站",
                    key: "zhichaojiancezhan",
                    proxyID: "21D9907A03DF4F0EB5007899C3E9819C",
                    type: "Point",
                    outFields:["ZCJCZMC"]
                },
                {
                    title: "养护工区",
                    key: "yanghugongqu",
                    proxyID: "630AA09DC94B45F18C657BF418F8826B",
                    type: "Point",
                    outFields:["GQMC"]
                },
                {
                    title: "检查站",
                    key: "jianchazhan",
                    proxyID: "AD478C2182BC456BA25E5CA72D2EDF60",
                    type: "Point",
                    icon: featurePath+"jcz.png",
                    outFields:["JCZMC"]
                },
                {
                    title: "监控设备",
                    key: "jiankongshebei",
                    proxyID: "42700DF47F1C4AB195924F1CA6C22CDF",
                    type: "Point",
                    icon: featurePath+"sxt.png",
                    outFields:["JKSBMC"]
                },
                {
                    title: "渡口",
                    key: "dukou",
                    proxyID: "F2939B936EF44DA185EAF62699D878F7",
                    type: "Point",
                    icon: featurePath+"dk.png",
                    outFields:["DKMC"]
                },
                {
                    title: "涵洞",
                    key: "handong",
                    proxyID: "B72C28ADBC7C4DF89394C31E2FA82F12",
                    type: "Point",
                    icon: featurePath+"hd.png",
                    outFields:["HDMC"]
                },
                {
                    title: "交通量观测站",
                    key: "jiaotongliangguancezhan",
                    proxyID: "FFBF41BBD4A6460F81050BE0AC574A15",
                    type: "Point", 
                    outFields:["JTLGCMC"]
                },
                {
                    title: "气象站点",
                    key: "qixiangzhandian",
                    proxyID: "94A4512351E14132AB0FCCA7EFC89D5D",
                    type: "Point", 
                    outFields:["QXZMC"]
                },
                {
                    title: "物资库",
                    key: "wuziku",
                    proxyID: "F2236CD04A854E4DA3EA5E98EDAD23AA",
                    type: "Point", 
                    outFields:["WZMC"]
                },
            ]
        }

    ], 
    checkedKeys: [], 
    expandedKeys: [], 
    treeProps: {
        children: "children",
        label: "title",
    }
}