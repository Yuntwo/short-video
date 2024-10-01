package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.entity.AdvertisementVideos;
import com.irdc.shortvideo.entity.Videos;
import com.irdc.shortvideo.enums.VideoStatusEnum;
import com.irdc.shortvideo.mapper.AdvertisementVideosMapper;
import com.irdc.shortvideo.service.AdvertisementVideosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/17 17:08
 * Description：
 */

@Service
public class AdvertisementVideosServiceImpl implements AdvertisementVideosService {

    @Autowired
    private AdvertisementVideosMapper advertisementVideosMapper;

    /**
     * 创建广告视频
     *
     * @param advertisementVideo
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createAdvertisementVideos(AdvertisementVideos advertisementVideo) {

        advertisementVideosMapper.insertSelective(advertisementVideo);

    }

    /**
     * 查询所有的广告视频
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<AdvertisementVideos> findAllVideos() {
        List<AdvertisementVideos> res = advertisementVideosMapper.findAll();
        return res;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public AdvertisementVideos findByVideoId(String videoId) {
        Example example = new Example(Videos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("videoId",videoId);

        AdvertisementVideos video = advertisementVideosMapper.selectOneByExample(example);
        return video;
    }

    /**
     * 更新广告视频信息
     *
     * @param video
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateVideo(AdvertisementVideos video) {
        Example example = new Example(Videos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("videoId",video.getVideoId());
        advertisementVideosMapper.updateByExampleSelective(video,example);
    }
}
