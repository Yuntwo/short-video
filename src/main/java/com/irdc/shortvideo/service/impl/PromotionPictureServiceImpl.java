package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.VO.PromotionVO;
import com.irdc.shortvideo.enums.PromotionPictureStatusEnum;
import com.irdc.shortvideo.mapper.PromotionPictureMapper;
import com.irdc.shortvideo.service.PromotionPictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/20 9:37
 * Description：
 */

@Service
@Slf4j
public class PromotionPictureServiceImpl implements PromotionPictureService {

    @Autowired
    private PromotionPictureMapper promotionPictureMapper;

//    @Autowired
//    private PromotionVOMapper promotionVOMapper;

    @Override
    public List<PromotionVO> promotePicture() {
        Integer pictureCount = promotionPictureMapper.getCount(PromotionPictureStatusEnum.NORMAL.getCode());
        if (pictureCount < 3) {
            log.error("推送图片不够");
            return null;
        }

        List<PromotionVO> allpictureList = promotionPictureMapper.getPromotionPic(PromotionPictureStatusEnum.NORMAL.getCode());

        ArrayList<Integer> list = new ArrayList<>();
        // todo: 不是随机的进行推送，把大赛的图片推送上去
        list.add(0);

        Random ra = new Random();
        // int i = 0;
        int i = 1;
        while (i < 3) {
            int r = ra.nextInt(pictureCount);
            //利用collection集合下的contains方法来判断集合中是否存在生成的随机数
            if (!list.contains(r)) {
                list.add(r);
                i++;
            }
        }

        List<PromotionVO> pictureList = new ArrayList<>();
        for (Integer j : list) {
            pictureList.add(allpictureList.get(j));
        }

        return pictureList;
    }


    @Override
    public List<PromotionVO> getAllPromotionPicture() {
        return promotionPictureMapper.getPromotionPic(PromotionPictureStatusEnum.NORMAL.getCode());
    }
}
