package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.entity.UserLikeAcademicVideo;
import com.irdc.shortvideo.entity.UserLikeVideo;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLikeAcademicVideoMapper extends MyMapper<UserLikeAcademicVideo> {

    UserLikeAcademicVideo queryVideoLikeIsExists(String videoId, String userId);
}