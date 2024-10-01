package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.VO.AchievementVO;
import com.irdc.shortvideo.dto.AchievementIdAndScoreDto;
import com.irdc.shortvideo.entity.AchievementStatic;
import com.irdc.shortvideo.entity.UserAchievementStatic;
import com.irdc.shortvideo.entity.Users;
import com.irdc.shortvideo.mapper.AchievementStaticMapper;
import com.irdc.shortvideo.mapper.UserAchievementStaticMapper;
import com.irdc.shortvideo.mapper.UsersMapper;
import com.irdc.shortvideo.service.AchievementStaticService;
import com.irdc.shortvideo.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/15 20:47
 * Description：
 */
@Slf4j
@Service
public class AchievementStaticServiceImpl implements AchievementStaticService {

    @Autowired
    private AchievementStaticMapper achievementStaticMapper;

    @Autowired
    private UserAchievementStaticMapper userAchievementStaticMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private LikeService likeService;

    @Override
    public List<AchievementVO> getAchievement(String userId) {
        List<AchievementVO> achievementVOList = new ArrayList<>();
        List<UserAchievementStatic> ussList = userAchievementStaticMapper.queryAchievementStaticByUserId(userId);
        if(ussList == null || ussList.isEmpty()){
            return null;
        }
        for(UserAchievementStatic uss : ussList){
            AchievementVO achievementVO = new AchievementVO();
            AchievementStatic achievementStatic = achievementStaticMapper.queryAchievementStaticByAchievementId(uss.getAchievementId());
            achievementVO.setTitle(achievementStatic.getTitle());
//            achievementVO.setMedal(achievementStatic.getMedal());
            achievementVO.setType(achievementStatic.getType());
            achievementVO.setLevel(achievementStatic.getLevel());
            achievementVO.setCreateTime(uss.getCreateTime());

            achievementVOList.add(achievementVO);
        }

        return achievementVOList;
    }

    @Override
    public void updateAchievement(String userId) {

        Users users = usersMapper.queryUserByUserId(userId);
        //todo: 使用Redis优化,if else 太多了，可以使用策略模式
        List<Integer> typeList = achievementStaticMapper.queryAchievementStaticType();
        for(Integer type : typeList){
            //粉丝数
            if(type == 1){
                Integer userScore = users.getFansNum();
                List<AchievementStatic> achievementStaticList = achievementStaticMapper.queryAchievementStaticByType(type);
                for(AchievementStatic achievementStatic : achievementStaticList){
                    if(userScore >= achievementStatic.getScore()){
                        if(userAchievementStaticMapper.queryIsExist(userId,achievementStatic.getAchievementId()) == null){
                            UserAchievementStatic uas = new UserAchievementStatic();
                            uas.setId(Sid.next());
                            uas.setAchievementId(achievementStatic.getAchievementId());
                            uas.setUserId(userId);
                            uas.setStatus(0);
                            userAchievementStaticMapper.insertSelective(uas);
                        }
                    }
                }
                //关注别人的数量
            }else if(type == 2){
                Integer userScore = users.getFollowNum();
                List<AchievementStatic> achievementStaticList = achievementStaticMapper.queryAchievementStaticByType(type);
                for(AchievementStatic achievementStatic : achievementStaticList){
                    if(userScore >= achievementStatic.getScore()){
                        if(userAchievementStaticMapper.queryIsExist(userId,achievementStatic.getAchievementId()) == null){
                            UserAchievementStatic uas = new UserAchievementStatic();
                            uas.setId(Sid.next());
                            uas.setAchievementId(achievementStatic.getAchievementId());
                            uas.setUserId(userId);
                            uas.setStatus(0);
                            userAchievementStaticMapper.insertSelective(uas);
                        }
                    }
                }
                //点赞数
            }else if(type == 3){
//                Integer userScore = users.getLikeNum();
                Integer userScore = likeService.queryLikeNumByUserId(userId);
                List<AchievementStatic> achievementStaticList = achievementStaticMapper.queryAchievementStaticByType(type);
                for(AchievementStatic achievementStatic : achievementStaticList){
                    if(userScore >= achievementStatic.getScore()){
                        if(userAchievementStaticMapper.queryIsExist(userId,achievementStatic.getAchievementId()) == null){
                            UserAchievementStatic uas = new UserAchievementStatic();
                            uas.setId(Sid.next());
                            uas.setAchievementId(achievementStatic.getAchievementId());
                            uas.setUserId(userId);
                            uas.setStatus(0);
                            userAchievementStaticMapper.insertSelective(uas);
                        }
                    }
                }
            }else{
                log.error("未知的成就类型");
            }
        }
    }
}
