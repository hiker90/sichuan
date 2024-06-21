package com.jh.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.jh.domain.*;
import com.jh.service.*;
import com.jh.shiro.ShiroUtils;
import com.jh.utils.CommonUtils;
import com.jh.utils.ajax.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@Slf4j
public class OdController
{
    @Autowired
    IInterurbanCoachOdService interurbanCoachOdService;

    String[] citys = {
            "乐山市",
            "内江市",
            "南充市",
            "宜宾市",
            "巴中市",
            "广元市",
            "广安市",
            "德阳市",
            "成都市",
            "攀枝花市",
            "泸州市",
            "甘孜藏族自治州",
            "眉山市",
            "绵阳市",
            "自贡市",
            "资阳市",
            "达州市",
            "遂宁市",
            "阿坝藏族羌族自治州",
            "雅安市",
            "凉山彝族自治州"
    };

    @PostMapping("/od/getlist")
    @RequiresPermissions("gis:map:view")
    public JSONObject getUserList(@RequestBody JSONObject jsonParam) {
        Integer start = jsonParam.getInteger("start");
        Integer end = jsonParam.getInteger("end");

        JSONObject jo = new JSONObject();
        try
        {
            HashMap<String, List<ArrayList<JSONObject>>> datas = new HashMap<>();
            for (String city : citys) {
                List<ArrayList<JSONObject>> datalist = new LinkedList<>();
                JSONObject cityjo = new JSONObject();
                cityjo.put("name", city);

                List<InterurbanCoachOd> list = interurbanCoachOdService.selectInterurbanCoachOdStatisticsList(city, start, end);

                for (InterurbanCoachOd od: list) {
                    ArrayList<JSONObject> arrayList = new ArrayList<>();
                    arrayList.add(cityjo);

                    JSONObject item = new JSONObject();
                    item.put("name", od.getdCity());
                    item.put("value", od.getNum());
                    arrayList.add(item);

                    datalist.add(arrayList);
                }

                datas.put(city, datalist);
            }
            jo.put("retcode", 0);
            jo.put("msg", "");
            jo.put("data", datas);

            return jo;
        } catch (Exception e) {
            jo.put("retcode", 1);
            jo.put("msg", e.getMessage());
            return jo;
        }
    }
}
