package com.irdc.shortvideo.controller.advertisementVideo;


import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.entity.AdvertisementVideos;
import com.irdc.shortvideo.service.AdvertisementVideosService;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangshu
 */

@Api(value = "获得广告视频播放列表接口", tags = {"获得广告视频播放列表"})
@RestController
@RequestMapping("/advertisementVideo")
@Slf4j
public class AdvertisementVideoListController {

    @Autowired
    private AdvertisementVideosService advertisementVideosService;


    @ApiOperation(value = "获得推荐视频列表", notes = "/video/getRecommendVideo")
    @GetMapping("/getRecommendVideo")
    public ResultVO getRecommendVideo() {
        List<AdvertisementVideos> videoList = advertisementVideosService.findAllVideos();

        int randomNum = (int)(Math.random() * videoList.size());

        AdvertisementVideos video = videoList.get(randomNum);

        return ResultVOUtil.success(video);
    }

}
