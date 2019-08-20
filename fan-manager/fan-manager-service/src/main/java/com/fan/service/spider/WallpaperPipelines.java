package com.fan.service.spider;

import com.fan.entity.spider.Wallpaper;
import com.fan.util.ConstantFan;
import com.fan.util.OperateFileUtil;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author fan
 * @title: wallpaperPipelines
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/19/001911:39
 */
@Slf4j
@PipelineName("wallpaperPipelines")
public class WallpaperPipelines implements Pipeline<Wallpaper> {

    @Override
    public void process(Wallpaper wallpaper) {
        System.out.println("*********************************************start spider***********************************");
        List<String> imageUrls = wallpaper.getImageUrls();
        List<String> imageTiles = wallpaper.getImageTile();
        if (!imageUrls.isEmpty() && !imageTiles.isEmpty()) {
            for (int i = 0; i < imageUrls.size(); i++) {
                String imageUrl = imageUrls.get(i);
                String imageTile = imageTiles.get(i);
                System.out.printf("title:%s    imageUrl:%s\n", imageTile, imageUrl);
//                HttpRequest currRequest = wallpaper.getRequest();
//                SchedulerContext.into(currRequest.subRequest(imageUlr));

                OperateFileUtil.downLoadFileFromLine(imageUrl,"D:/fan-share");
            }
        }
    }

}
