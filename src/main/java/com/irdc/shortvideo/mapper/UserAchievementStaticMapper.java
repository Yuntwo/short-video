package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.entity.UserAchievementStatic;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/15 19:55
 * Description：
 */
@Mapper
public interface UserAchievementStaticMapper extends MyMapper<UserAchievementStatic> {

    List<String> queryAchievementStaticIdByUserId(String userId);


    List<UserAchievementStatic> queryAchievementStaticByUserId(String userId);

    UserAchievementStatic queryIsExist(String userId, String achievementId);
}
