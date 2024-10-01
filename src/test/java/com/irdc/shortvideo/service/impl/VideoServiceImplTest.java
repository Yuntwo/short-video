package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.entity.Videos;
import com.irdc.shortvideo.service.VideoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/7 14:45
 * Description：
 */

@SpringBootTest
@RunWith(SpringRunner.class)
class VideoServiceImplTest {

    @Autowired
    private VideoService videoService;

    @Test
    void findVideoInfoByVideoId() {
        Videos res = videoService.findVideoInfoByVideoId("f6870897bddc4d2793308a4e2bb930d1");
        System.out.println(res);

    }
}