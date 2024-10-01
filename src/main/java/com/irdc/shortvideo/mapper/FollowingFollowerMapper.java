package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.entity.FollowingFollower;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowingFollowerMapper extends MyMapper<FollowingFollower> {
    /**
     * 查询是否存在粉丝关系
     * @param followerId
     * @param followingId
     * @return
     */
    List<FollowingFollower> queryFollowByFollowerIdAndFollowingId(String followerId, String followingId);

    /**
     * 解除粉丝关系
     * @param followingId
     * @param followerId
     */
    void deleteFollowRelationship(String followingId, String followerId);
}