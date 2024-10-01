package com.irdc.shortvideo.service;

import com.irdc.shortvideo.VO.PackageInfoVO;
import com.irdc.shortvideo.entity.PackageInfo;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/17 18:51
 * Description：
 */
public interface PackageInfoService {

    /**
     * 查询最新的一条记录
     * @return
     */
    PackageInfoVO findOne();
}
