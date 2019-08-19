package com.fan.entity.spider;

import com.fan.util.ConstantFan;
import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.SpiderBean;
import lombok.Data;

import java.util.List;

/**
 * @author fan
 * @title: Wallpaper
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/19/001911:41
 */
@Data
@Gecco(matchUrl= ConstantFan.SPIDER_TARGET_URL,pipelines="wallpaperPipelines")
public class Wallpaper implements SpiderBean {
    @Request
    private HttpRequest request;

//    @HtmlField(cssPath = "ul li img")
    @HtmlField(cssPath = "img")
    @Image
    private List<String> imageUrls;

    @HtmlField(cssPath = "img")
    @Attr("alt")
    private List<String> imageTile;
}
