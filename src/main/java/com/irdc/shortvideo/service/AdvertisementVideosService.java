package com.irdc.shortvideo.service;

import com.irdc.shortvideo.entity.AdvertisementVideos;

import java.util.List;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/17 17:05
 * Description：广告视频
 */
public interface AdvertisementVideosService {

    /**
     * 创建广告视频
     * @param advertisementVideo
     */
    void createAdvertisementVideos(AdvertisementVideos advertisementVideo);

    /**
     * 查询所有的广告视频
     * @return
     */
    List<AdvertisementVideos> findAllVideos();

    /**
     * 通过视频id查询视频信息
     * @param videoId
     * @return
     */
    AdvertisementVideos findByVideoId(String videoId);


    /**
     * 更新广告视频信息
     * @param video
     */
    void updateVideo(AdvertisementVideos video);
}
