package com.ziqun.crawleritem.controller;

import com.ziqun.crawleritem.bean.Hello;
import com.ziqun.crawleritem.crawler.PinDuoDuo;
import com.ziqun.crawleritem.crawler.PinduoduoItem;
import com.ziqun.crawleritem.crawler.TianMao;
import com.ziqun.crawleritem.crawler.TianMaoResut;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired
    PinDuoDuo pinDuoDuo;

    @Autowired
    TianMao tianMao;

    @RequestMapping("/pinduoduo")
    public PinduoduoItem hello(String id){
        System.out.println(id);
        System.out.println("test");
        return pinDuoDuo.getItemInfo(id);
    }

    @RequestMapping("/tianmao")
    public TianMaoResut get(String id){
        return tianMao.getItemInfo(id);
    }
}
