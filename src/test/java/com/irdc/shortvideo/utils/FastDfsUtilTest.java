package com.irdc.shortvideo.utils;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/11 11:06
 * Description：
 */

@SpringBootTest
@RunWith(SpringRunner.class)
class FastDfsUtilTest {

    @Autowired
    private FastDfsUtil fastDfsUtil;


    @Test
    void uploadFileByURL() throws IOException {
        String url = "http://39.106.161.191:8888/group1/M00/00/02/J2qhv19-r0WAWGTfAAEx80nKlUw972.png";
        String path = fastDfsUtil.uploadFileByURL(url);
        System.out.println(path);

    }
}