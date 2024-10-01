package com.irdc.shortvideo.service;

import com.irdc.shortvideo.VO.PromotionVO;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/20 9:37
 * Description：
 */
public interface PromotionPictureService {

    List<PromotionVO> promotePicture();

    List<PromotionVO> getAllPromotionPicture();
}
