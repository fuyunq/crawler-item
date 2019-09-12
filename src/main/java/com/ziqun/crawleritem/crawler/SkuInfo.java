package com.ziqun.crawleritem.crawler;

import java.util.HashMap;
import java.util.Map;

public class SkuInfo {
    private Map<String, String> specMap = new HashMap<>();

    private String groupPrice;

    private String normalPrice;

    private String thumbUrl;

    public void setKeyValue(String key, String value) {
        specMap.put(key, value);
    }

    public Map<String, String> getSpecMap() {
        return specMap;
    }

    public void setSpecMap(Map<String, String> specMap) {
        this.specMap = specMap;
    }

    public String getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(String groupPrice) {
        this.groupPrice = groupPrice;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}