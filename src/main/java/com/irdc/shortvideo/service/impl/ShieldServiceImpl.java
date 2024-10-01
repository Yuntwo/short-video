package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.VO.shield.ShieldVO;
import com.irdc.shortvideo.VO.shield.ShieldedUserVO;
import com.irdc.shortvideo.entity.Shield;
import com.irdc.shortvideo.mapper.ShieldMapper;
import com.irdc.shortvideo.service.ShieldService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/10 19:41
 * Description：
 */
@Service
public class ShieldServiceImpl implements ShieldService {

    @Autowired
    private ShieldMapper shieldMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean createShieldRecord(ShieldVO shieldVO) {
        Shield shield = new Shield();
        BeanUtils.copyProperties(shieldVO, shield);
        shield.setId(Sid.next());
        shield.setCreateTime(new Date());

        int i = shieldMapper.insertSelective(shield);

        return i != 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<ShieldedUserVO> queryShieldedUserId(String fromUserId) {
        return shieldMapper.querytoUserByFromUserId(fromUserId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteShieldRecord(String fromUserId, String toUserId) {
        shieldMapper.deleteShield(fromUserId, toUserId);
    }
}
