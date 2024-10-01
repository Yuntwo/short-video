package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.entity.AdvertisementVideos;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdvertisementVideosMapper extends MyMapper<AdvertisementVideos> {

    List<AdvertisementVideos> findAll();

}