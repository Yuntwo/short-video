package com.irdc.shortvideo.service;

import com.irdc.shortvideo.VO.AchievementVO;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/15 20:45
 * Description：
 */
public interface AchievementStaticService {

    List<AchievementVO> getAchievement(String userId);

    void updateAchievement(String userId);
}
