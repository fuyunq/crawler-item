package com.ziqun.crawleritem.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ziqun.crawleritem.util.JsoupUtil;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PinDuoDuo {

    public PinduoduoItem getItemInfo(String goodId){
        String url = "https://mobile.yangkeduo.com/goods.html?goods_id="+goodId;
        Map<String,String> map = new HashMap<>();
        map.put("User-Agent","Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Mobile Safari/537.36");

        Document document = JsoupUtil.connect(url,map,null,false);

        Pattern pattern = Pattern.compile("window.rawData=(.*?);");
        String test = document.toString();
        Matcher matcher = pattern.matcher(test);

        PinduoduoItem pinduoduoItem = new PinduoduoItem();

        if(matcher.find()){
            String ss = matcher.group(1);
            JSONObject jsonObject = JSON.parseObject(ss);
            JSONObject initDataObj = jsonObject.getJSONObject("store").getJSONObject("initDataObj");
            JSONObject goods  = initDataObj.getJSONObject("goods");

            String title = goods.getString("goodsName");
            pinduoduoItem.setName(title);

            String imgUrl = goods.getString("thumbUrl");
            pinduoduoItem.setImgUrl(imgUrl);

            JSONArray skus = goods.getJSONArray("skus");

            List<SkuInfo> skuInfoList = new ArrayList<>();
            for (int i=0;i<skus.size();i++) {
                SkuInfo skuInfo = new SkuInfo();
                JSONObject sku = skus.getJSONObject(i);
                String groupPrice = sku.getString("groupPrice");
                skuInfo.setGroupPrice(groupPrice);

                String normalPrice = sku.getString("normalPrice");
                skuInfo.setNormalPrice(normalPrice);

                String thumbUrl  = sku.getString("thumbUrl");
                skuInfo.setThumbUrl(thumbUrl);

                Map<String,String> keyValueMap =  new HashMap<>();

                JSONArray specs = sku.getJSONArray("specs");
                for(int j=0;j<specs.size();j++){
                    JSONObject spec = specs.getJSONObject(j);
                    String key = spec.getString("spec_key");
                    String value = spec.getString("spec_value");
                    keyValueMap.put(key,value);
                }
                skuInfo.setSpecMap(keyValueMap);
                skuInfoList.add(skuInfo);
            }
            pinduoduoItem.setSkuInfoList(skuInfoList);
        }
        return pinduoduoItem;
    }
}