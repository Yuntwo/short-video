package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.VO.PromotionVO;
import com.irdc.shortvideo.entity.PromotionPicture;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/20 9:34
 * Description：
 */
@Mapper
public interface PromotionPictureMapper extends MyMapper<PromotionPicture> {

    Integer getCount(Integer status);

    List<PromotionVO> getPromotionPic(Integer status);

}
