package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.VO.PackageInfoVO;
import com.irdc.shortvideo.entity.AdvertisementVideos;
import com.irdc.shortvideo.entity.PackageInfo;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PackageInfoMapper extends MyMapper<PackageInfo> {

    PackageInfoVO findOne();

}