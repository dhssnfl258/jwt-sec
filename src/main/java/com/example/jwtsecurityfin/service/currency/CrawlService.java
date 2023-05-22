//package com.example.jwtsecurityfin.service.currency;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//public class CrawlService {
//    public JSONObject exchange() {
//        JSONObject result = new JSONObject();
//        JSONArray arr = new JSONArray();
//
//        // 네이버 환율정보 페이지
//        String url = "https://finance.naver.com/marketindex/exchangeList.nhn";
//        Document doc = null;
//
//        try {
//            // 환율정보 스크래핑
//            doc = Jsoup.connect(url).get();
//            // 국가명, 환율
//            Elements country = doc.select(".tit");
//            Elements sale = doc.select(".sale");
//
//            for (int i = 0; i < country.size(); i++) {
//
//                Element country_el = country.get(i);
//                Element sale_el = sale.get(i);
//                JSONObject obj = new JSONObject();
//
//                obj.put("country", country_el.text());
//                obj.put("sale", sale_el.text());
//                arr.add(obj);
//
//            }
//            result.put("result", "success");
//            result.put("exchange", arr);
//
//        } catch (IOException e) {
//
//            result.put("result", "fail");
//            e.printStackTrace();
//
//        }
//
//        return result;
//    }
//
//}
