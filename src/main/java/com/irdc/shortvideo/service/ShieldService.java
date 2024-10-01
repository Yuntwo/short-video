package com.irdc.shortvideo.service;

import com.irdc.shortvideo.VO.shield.ShieldVO;
import com.irdc.shortvideo.VO.shield.ShieldedUserVO;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/10 19:33
 * Description：
 */
public interface ShieldService {

    /**
     * 创建屏蔽记录
     * @param shieldVO
     */
    boolean createShieldRecord(ShieldVO shieldVO);

    /**
     * 查询某一用户所有被屏蔽的用户
     * @param fromUserId
     * @return
     */
    List<ShieldedUserVO> queryShieldedUserId(String fromUserId);

    /**
     * 删除屏蔽记录
     * @param fromUserId
     * @param toUserId
     */
    void deleteShieldRecord(String fromUserId, String toUserId);
}
