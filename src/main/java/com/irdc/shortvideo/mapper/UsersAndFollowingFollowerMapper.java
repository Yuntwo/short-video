package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.VO.FollowingFollowerVO;
import com.irdc.shortvideo.entity.FollowingFollower;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/10/5 21:40
 * Description：
 */
@Mapper
public interface UsersAndFollowingFollowerMapper extends MyMapper<FollowingFollower> {


    /**
     * 通过userId查询粉丝的信息
     * @param userId
     * @return
     */
    List<FollowingFollowerVO> queryFollowerInfoByUserId(String userId);


    /**
     * 通过userId查询关注的人信息
     * @param userId
     * @return
     */
    List<FollowingFollowerVO> queryFollowingInfoByUserId(String userId);
}
