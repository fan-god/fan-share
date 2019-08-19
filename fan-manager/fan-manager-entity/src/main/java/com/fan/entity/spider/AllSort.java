//package com.fan.entity.spider;
//
//import com.fan.util.ConstantFan;
//import com.geccocrawler.gecco.annotation.*;
//import com.geccocrawler.gecco.request.HttpRequest;
//import com.geccocrawler.gecco.spider.SpiderBean;
//
//import java.util.List;
//
///**
// * @author fan
// * @title: BlogService
// * @projectName fan-share
// * @description: TODO
// * @date 2019/8/16/001616:45
// */
//@Gecco(matchUrl= ConstantFan.SPIDER_TARGET_URL,pipelines="spiderDataPipelines")
//public class AllSort implements SpiderBean {
//    /**
//     * 向指定URL发送GET方法的请求
//     */
//    @Request
//    private HttpRequest request;
//
//    public HttpRequest getRequest() {
//        return request;
//    }
//
//    public void setRequest(HttpRequest request) {
//        this.request = request;
//    }
//
//    /**
//     * 抓去这个路径下所有的内容
//     */
//    // 移动互联网
//    @HtmlField(cssPath="#tab-list > div:nth-child(1)")
//    private List<Category> mobileInternet;
//
//    // 电子商务
//    @HtmlField(cssPath="#tab-list > div:nth-child(2)")
//    private List<Category> electric;
//
//    // 互联网
//    @HtmlField(cssPath="#tab-list > div:nth-child(3)")
//    private List<Category> internet;
//
//    // 网络营销
//    @HtmlField(cssPath="#tab-list > div:nth-child(4)")
//    private List<Category> netMarket;
//
//    // 网络游戏
//    @HtmlField(cssPath="#tab-list > div:nth-child(5)")
//    private List<Category> netGame;
//
//    public List<Category> getMobileInternet() {
//        return mobileInternet;
//    }
//
//    public void setMobileInternet(List<Category> mobileInternet) {
//        this.mobileInternet = mobileInternet;
//    }
//
//    public List<Category> getElectric() {
//        return electric;
//    }
//
//    public void setElectric(List<Category> electric) {
//        this.electric = electric;
//    }
//
//    public List<Category> getInternet() {
//        return internet;
//    }
//
//    public void setInternet(List<Category> internet) {
//        this.internet = internet;
//    }
//
//    public List<Category> getNetMarket() {
//        return netMarket;
//    }
//
//    public void setNetMarket(List<Category> netMarket) {
//        this.netMarket = netMarket;
//    }
//
//    public List<Category> getNetGame() {
//        return netGame;
//    }
//
//    public void setNetGame(List<Category> netGame) {
//        this.netGame = netGame;
//    }
//
//}
