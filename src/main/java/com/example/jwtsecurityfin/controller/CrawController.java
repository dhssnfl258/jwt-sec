//package com.example.jwtsecurityfin.controller;
//
//import com.example.jwtsecurityfin.service.currency.CrawlService;
//import lombok.extern.slf4j.Slf4j;
//import org.json.simple.JSONObject;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@Slf4j
//@RestController
//public class CrawController {
//
//    private  final CrawlService crawlService;
//
//    public CrawController(CrawlService crawlService) {
//        this.crawlService = crawlService;
//    }
//
//
//    // 환율정보 스크래핑
//    @GetMapping("sendExchange")
//    @ResponseBody
//    @CrossOrigin
//    public JSONObject sendExchange() throws Exception {
//
//        return crawlService.exchange();
//
//    }
//
//}
