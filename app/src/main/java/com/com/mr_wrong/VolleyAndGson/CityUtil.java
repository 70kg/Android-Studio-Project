package com.com.mr_wrong.VolleyAndGson;

import java.util.HashMap;

/**
 * Created by Mr_Wrong on 2015/5/16.
 */
public class CityUtil {
    private HashMap<String, String> map = new HashMap<String, String>();

    public CityUtil() {
        map.put("北京", "101010100");
        map.put("上海", "101020100");
        map.put("天津", "101030100");
        map.put("重庆", "101040100");
    }

    public String GetCityId(String city_name) {
        return map.get(city_name);
    }
}
