package com.zzsj.analysis.manage.constants;

import com.zzsj.analysis.manage.entity.FiveDistrictsInvestmentCompletionIndex;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zbya
 * @date ：Created in 2020/12/31 16:38
 * @description：五大经济区
 */
public class EconomicRegion {
    public static Map<String, String> map = new HashMap();

    static {
        map.put("成都市", "成都平原经济区");
        map.put("德阳市", "成都平原经济区");
        map.put("绵阳市", "成都平原经济区");
        map.put("遂宁市", "成都平原经济区");
        map.put("乐山市", "成都平原经济区");
        map.put("雅安市", "成都平原经济区");
        map.put("眉山市", "成都平原经济区");
        map.put("资阳市", "成都平原经济区");

        map.put("自贡市", "川南经济区");
        map.put("泸州市", "川南经济区");
        map.put("内江市", "川南经济区");
        map.put("宜宾市", "川南经济区");

        map.put("达州市", "川东北经济区");
        map.put("广安市", "川东北经济区");
        map.put("巴中市", "川东北经济区");
        map.put("南充市", "川东北经济区");
        map.put("广元市", "川东北经济区");

        map.put("阿坝州", "川西北生态示范区");
        map.put("甘孜州", "川西北生态示范区");

        map.put("攀枝花市", "攀西经济区");
        map.put("凉山州", "攀西经济区");

    }
}
