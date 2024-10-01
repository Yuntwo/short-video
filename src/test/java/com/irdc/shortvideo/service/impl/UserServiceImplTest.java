package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.VO.FollowingFollowerVO;
import com.irdc.shortvideo.VO.VideoWithUserVO;
import com.irdc.shortvideo.entity.Users;
import com.irdc.shortvideo.service.UserService;
import com.irdc.shortvideo.service.VideoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/3 16:23
 * Description：
 */

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;


    public static final String phone = "13020093535";

    @Test
    void queryPhoneIsExist() {
        boolean result = userService.queryPhoneIsExist("123444");
        System.out.println(result);

    }

    @Test
    void saveUser() {
        Users users = new Users();
        users.setPhone("1122211");
        users.setPassword("pwd");
        userService.createUser(users);
    }

    @Test
    void updateUserInfo() {
        Users users = userService.queryUserInfoByphone(phone);
        System.out.println(users);
        users.setUsername("yangshu");
        userService.updateUserInfo(users);
    }

    @Test
    void queryUserInfoByUserId() {
        System.out.println(Sid.nextShort());
        System.out.println(Sid.nextShort());
        System.out.println(Sid.nextShort());
        System.out.println(Sid.next());

    }

    @Test
    void queryUserInfoByphone() {
        System.out.println(userService.queryUserInfoByphone("13020093535"));
    }

    @Test
    void queryFollowerInfoByUserId(){
        List<FollowingFollowerVO> results = userService.queryFollowerInfoByUserId("222");
        for(FollowingFollowerVO result : results){
            System.out.println(result.toString());
        }

//        System.out.println(results);
    }

    @Test
    void queryFollowingInfoByUserId(){
        List<FollowingFollowerVO> results = userService.queryFollowingInfoByUserId("111");
        for(FollowingFollowerVO result : results){
            System.out.println(result.toString());
        }

        System.out.println(results);
    }

//    @Test
//    void queryUserLikeVideoInfoByUserId(){
//        List<VideoWithUserVO> results = videoService.queryUserLikeVideoInfoByUserId("222");
//        for(VideoWithUserVO result : results){
//            System.out.println(result.toString());
//        }
//
//        System.out.println(results);
//    }
//
//    @Test
//    void queryUserPublishVideoInfoByUserId(){
//        List<VideoWithUserVO> results = videoService.queryUserPublishVideoInfoByUserId("333");
//        for(VideoWithUserVO result : results){
//            System.out.println(result.toString());
//        }
//
//        System.out.println(results);
//    }

}