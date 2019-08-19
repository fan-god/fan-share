//package com.fan.service.spider;
//
//import com.fan.entity.spider.AllSort;
//import com.fan.entity.spider.Category;
//import com.geccocrawler.gecco.annotation.PipelineName;
//import com.geccocrawler.gecco.pipeline.Pipeline;
//import com.geccocrawler.gecco.request.HttpRequest;
//import com.geccocrawler.gecco.scheduler.SchedulerContext;
//import com.geccocrawler.gecco.spider.HrefBean;
//import com.google.common.collect.Lists;
//
//import java.util.List;
//
///**
// * @author fan
// * @title: BlogPipelines
// * @projectName fan-share
// * @description: TODO
// * @date 2019/8/16/001616:48
// */
//@PipelineName("spiderDataPipelines")
//public class SpiderDataPipelines implements Pipeline<AllSort> {
//    /**
//     * 将抓取到的内容进行处理  这里是打印在控制台
//     */
//    @Override
//    public void process(AllSort allSort) {
//        System.out.println("-==============================================================-");
//        List<Category> categorys = Lists.newArrayList();
//        categorys.addAll(allSort.getInternet());
//        categorys.addAll(allSort.getElectric());
//        categorys.addAll(allSort.getMobileInternet());
//        categorys.addAll(allSort.getNetGame());
//        categorys.addAll(allSort.getNetMarket());
//        for(Category category : categorys) {
//            List<HrefBean> hrefs = category.getCategorys();
//            for(HrefBean href : hrefs) {
//                String url = href.getUrl();
//                System.out.printf("title:%s\nurl:%s\n",href.getTitle(), url);
//                HttpRequest currRequest = allSort.getRequest();
//                SchedulerContext.into(currRequest.subRequest(url));
//            }
//        }
//    }
//}
