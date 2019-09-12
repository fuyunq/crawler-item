package com.ziqun.crawleritem.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ziqun.crawleritem.util.JsoupUtil;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TianMao {

    public TianMaoResut getItemInfo(String id){
        TianMaoResut tianMaoResut = new TianMaoResut();
        String url = "https://detail.m.tmall.com/item.htm?id="+id;
        Map<String,String> map = new HashMap<>();
        //map.put("Referer","https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.15.4fb0995bITzd6g&id=600950739234&skuId=4196862136793&areaId=310100&user_id=513051429&cat_id=2&is_b=1&rn=b5a79237be4aa56433878e7337d73fe0");
        map.put("User-Agent","Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Mobile Safari/537.36");
//        map.put("Sec-Fetch-Mode","no-cors");
//        map.put("","");
        Document document = JsoupUtil.connect(url,map,null,false);
//        System.out.println(document.toString());
        Pattern pattern = Pattern.compile("<script>var _DATA_Mdskip = ([\\s\\S]*?)</script>");
        String test = document.toString();
        System.out.println(test);
        Matcher matcher = pattern.matcher(test);
//        PinduoduoItem pinduoduoItem = pinDuoDuo.getItemInfo("8232877392");
        if(matcher.find()){
            String json = matcher.group(1);
            JSONObject js = JSON.parseObject(json);
            JSONObject item = js.getJSONObject("item");
            String title = item.getString("title");
            JSONObject skuBase = js.getJSONObject("skuBase");
            JSONObject skuCore = js.getJSONObject("skuCore");
            tianMaoResut.setTitle(title);
            tianMaoResut.setSkuBase(JSON.toJSONString(skuBase));
            tianMaoResut.setSkuCore(JSON.toJSONString(skuCore));
        }
        return tianMaoResut;

    }
}
