package com.irdc.shortvideo.controller.promotion;

import com.irdc.shortvideo.VO.PromotionVO;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.entity.PromotionPicture;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.service.PromotionPictureService;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/20 21:19
 * Description：
 */

@Api(value = "首页推送接口",tags = {"首页推送"})
@RestController
@RequestMapping("/promotion")
@Slf4j
public class promotionController {
    @Autowired
    private PromotionPictureService promotionPictureService;

    @ApiOperation(value = "获得推送图片",notes = "/promotion/picture")
    @GetMapping("/picture")
    public ResultVO getPromotionPicture() {
        List<PromotionVO> result = promotionPictureService.promotePicture();
        if(CollectionUtils.isEmpty(result)) {
            return ResultVOUtil.error(ResultEnum.PROMOTION_PICTURE_IS_EMPTY.getCode(),ResultEnum.PROMOTION_PICTURE_IS_EMPTY.getMessage());
        }
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "获得所有推送图片",notes = "/promotion/allPicture")
    @GetMapping("/allPicture")
    public ResultVO getAllPromotionPicture() {
        List<PromotionVO> result = promotionPictureService.getAllPromotionPicture();
        if(CollectionUtils.isEmpty(result)) {
            return ResultVOUtil.error(ResultEnum.PROMOTION_PICTURE_IS_EMPTY.getCode(),ResultEnum.PROMOTION_PICTURE_IS_EMPTY.getMessage());
        }
        return ResultVOUtil.success(result);
    }
}
