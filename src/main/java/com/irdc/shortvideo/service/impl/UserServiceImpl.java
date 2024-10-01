package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.VO.FollowingFollowerVO;
import com.irdc.shortvideo.entity.FollowingFollower;
import com.irdc.shortvideo.entity.Users;
import com.irdc.shortvideo.entity.Videos;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.exception.BusinessException;
import com.irdc.shortvideo.mapper.FollowingFollowerMapper;
import com.irdc.shortvideo.mapper.UsersMapper;
import com.irdc.shortvideo.mapper.VideosMapper;
import com.irdc.shortvideo.service.UserService;
import com.irdc.shortvideo.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import com.irdc.shortvideo.mapper.UsersAndFollowingFollowerMapper;

import java.util.Date;
import java.util.List;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/3 16:14
 * Description：
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersAndFollowingFollowerMapper usersAndFollowingFollowerMapper;

    @Autowired
    private FollowingFollowerMapper followingFollowerMapper;

    @Value("${irdc.userDefaultAvatar}")
    private String userDefaultAvatar;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryPhoneIsExist(String phone) {
        Users user = usersMapper.queryPhoneIsExist(phone);
        return user != null;
    }

    /**
     * 查询用户名是否存在
     *
     * @param username
     * @return
     */
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = usersMapper.queryUsernameIsExist(username);
        return user != null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfoByphone(String phone) {
        return usersMapper.queryPhoneIsExist(phone);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createUser(Users user) {

        // user.setUsername(user.getPhone());

        // todo: 需要进行修改,数据库中username的字段长为16，但是下面这种生成方法太长了，不能直接的进行截取，涉及到Sid的生成规则
        String username = String.format("yudian_%s",Sid.nextShort());
        user.setUsername(username);
        user.setFaceImage(userDefaultAvatar);

        usersMapper.createUser(user);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserIsExistByuserId(String userId) {
        Users users = usersMapper.queryUserByUserId(userId);
        if(users == null){
            return false;
        }else if(users.getStatus() == 0){
            return true;
        }else if(users.getStatus() == 1){
            log.info("用户已注销");
            return false;
        }else{
            log.info("违规用户");
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserInfo(Users user) {
        try{
            usersMapper.updateUserInfo(user);
        }catch (Exception ex) {
            throw new BusinessException(ResultEnum.UPDATE_USER_PROFILE_FAIL);
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfoByUserId(String userId) {
        return usersMapper.queryUserByUserId(userId);
    }



    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FollowingFollowerVO> queryFollowerInfoByUserId(String userId) {
        return usersAndFollowingFollowerMapper.queryFollowerInfoByUserId(userId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FollowingFollowerVO> queryFollowingInfoByUserId(String userId) {
        return usersAndFollowingFollowerMapper.queryFollowingInfoByUserId(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void followOthers(String followerId, String followingId) {
        FollowingFollower followingFollower = new FollowingFollower();
        // followingFollower.setId(UUIDUtil.getId());
        followingFollower.setId(Sid.next());
        followingFollower.setFollowingId(followingId);
        followingFollower.setFollowerId(followerId);
        followingFollower.setCreateTime(new Date());

        if(followingFollowerMapper.insert(followingFollower) == 1){
            usersMapper.addFansNum(followingId);
            usersMapper.addFollowNum(followerId);
        }else{
            log.info("关注失败");
        }

    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void cancelFollowOthers(String followerId, String followingId) {
        followingFollowerMapper.deleteFollowRelationship(followingId, followerId);
        usersMapper.reduceFansNum(followingId);
        usersMapper.reduceFollowNum(followerId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isFollowRelationExist(String followerId, String followingId) {
        List<FollowingFollower> result = followingFollowerMapper.queryFollowByFollowerIdAndFollowingId(followerId, followingId);

        return result != null && result.size() > 0;

    }

    @Override
    public boolean islegal(String userId) {
        return usersMapper.queryStatus(userId) != 2;
    }

    @Override
    public boolean canUploadAcademicVideo(String userId) {
        return usersMapper.queryUploadAuthority(userId) == 2;
    }
}
