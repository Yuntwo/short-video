package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.VO.PackageInfoVO;
import com.irdc.shortvideo.constant.RedisConstant;
import com.irdc.shortvideo.entity.PackageInfo;
import com.irdc.shortvideo.mapper.PackageInfoMapper;
import com.irdc.shortvideo.service.PackageInfoService;
import com.irdc.shortvideo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/17 18:58
 * Description：
 */
@Service
public class PackageInfoServiceImpl implements PackageInfoService {

    @Autowired
    private PackageInfoMapper packageInfoMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 查询最新的一条记录
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PackageInfoVO findOne() {

        boolean keyExist = redisService.keyExist(RedisConstant.PACKAGE_INFO);

        if(!keyExist){
            PackageInfoVO packageInfoVO = packageInfoMapper.findOne();
            if(packageInfoVO == null){
                return null;
            }
            redisService.setHash(RedisConstant.PACKAGE_INFO, RedisConstant.PACKAGE_INFO_NUMBER, packageInfoVO.getNumber());
            redisService.setHash(RedisConstant.PACKAGE_INFO, RedisConstant.PACKAGE_INFO_DESCRIPTION, packageInfoVO.getDescription());
            redisService.setHash(RedisConstant.PACKAGE_INFO, RedisConstant.PACKAGE_INFO_URL, packageInfoVO.getUrl());
        }

        PackageInfoVO packageInfoVOFromRedis = new PackageInfoVO();

        packageInfoVOFromRedis.setNumber(redisService.getHash(RedisConstant.PACKAGE_INFO, RedisConstant.PACKAGE_INFO_NUMBER));
        packageInfoVOFromRedis.setDescription(redisService.getHash(RedisConstant.PACKAGE_INFO, RedisConstant.PACKAGE_INFO_DESCRIPTION));
        packageInfoVOFromRedis.setUrl(redisService.getHash(RedisConstant.PACKAGE_INFO, RedisConstant.PACKAGE_INFO_URL));

        return packageInfoVOFromRedis;
    }
}
