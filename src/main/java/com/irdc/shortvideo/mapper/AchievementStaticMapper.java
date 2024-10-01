package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.dto.AchievementIdAndScoreDto;
import com.irdc.shortvideo.entity.AchievementStatic;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/15 19:54
 * Description：
 */
@Mapper
public interface AchievementStaticMapper extends MyMapper<AchievementStatic> {

    AchievementStatic queryAchievementStaticByAchievementId(String achievementId);

    List<Integer> queryAchievementStaticType();

    List<AchievementStatic> queryAchievementStaticByType(Integer type);
}
