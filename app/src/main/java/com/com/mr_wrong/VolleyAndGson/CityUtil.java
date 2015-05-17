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
        map.put("香港", "101320101");
        map.put("澳门", "101330101");
        map.put("哈尔滨", "101050101");
        map.put("齐齐哈尔", "101050101");
        map.put("牡丹江", "101050101");
        map.put("大庆", "101050101");
        map.put("伊春", "101050101");
        map.put("双鸭山", "101050101");
        map.put("鹤岗", "101050101");
        map.put("鸡西", "101050101");
        map.put("佳木斯", "101050101");
        map.put("枣庄", "101121401");
        map.put("徐州", "101190801");

    }

    public String GetCityId(String city_name) {
        return map.get(city_name);
    }
}
