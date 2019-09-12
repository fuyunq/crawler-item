package com.ziqun.crawleritem.controller;

import com.ziqun.crawleritem.bean.Hello;
import com.ziqun.crawleritem.crawler.PinDuoDuo;
import com.ziqun.crawleritem.crawler.PinduoduoItem;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired
    PinDuoDuo pinDuoDuo;

    @RequestMapping("/pinduoduo")
    public PinduoduoItem hello(String id){
        System.out.println(id);
        return pinDuoDuo.getItemInfo(id);
    }

}
