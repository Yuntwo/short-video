package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.VO.shield.ShieldedUserVO;
import com.irdc.shortvideo.entity.Shield;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/10 19:19
 * Description：
 */
@Mapper
public interface ShieldMapper extends MyMapper<Shield> {

//    /**
//     * 创建屏蔽记录
//     */
//    void createShield(Shield shield);


    /**
     * 获取被屏蔽用户列表
     * @param fromUserId
     * @return
     */
    List<ShieldedUserVO> querytoUserByFromUserId(String fromUserId);

    /**
     * 取消屏蔽
     * @param fromUserId
     * @param toUserId
     */
    void deleteShield(String fromUserId, String toUserId);

}
