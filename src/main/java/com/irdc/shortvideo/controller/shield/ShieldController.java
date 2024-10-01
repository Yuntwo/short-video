package com.irdc.shortvideo.controller.shield;

import com.irdc.shortvideo.VO.CommentVO;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.VO.UserIdVO;
import com.irdc.shortvideo.VO.shield.ShieldVO;
import com.irdc.shortvideo.VO.shield.ShieldedUserVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.service.ShieldService;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/10 19:55
 * Description：
 */

@Api(value = "屏蔽接口", tags = {"屏蔽"})
@RestController
@RequestMapping("/shield")
@Slf4j
public class ShieldController {

    @Autowired
    private ShieldService shieldService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "屏蔽",notes = "/shield/create")
    @PostMapping("/create")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO create(@RequestBody UserIdVO userIdVO, HttpServletRequest request){

        String toUserId = userIdVO.getUserId();
        // 1. 前端参数完整性检验
        if (StringUtils.isEmpty(toUserId)) {
            log.error("【屏蔽】传入的参数不正确，toUserId: {}", toUserId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        //2. 写入数据库
        String fromUserId = redisUtil.getUserIdByToken(request);

        ShieldVO shieldVO = new ShieldVO();
        shieldVO.setFromUserId(fromUserId);
        shieldVO.setToUserId(toUserId);

        boolean res = shieldService.createShieldRecord(shieldVO);

        if(!res) {
            log.error("【屏蔽】用户失败，res: {}",res);
            return ResultVOUtil.error(ResultEnum.SHIELD_USER_FAIL.getCode(),ResultEnum.SHIELD_USER_FAIL.getMessage());
        }

        return ResultVOUtil.success();
    }

    @ApiOperation(value = "取消屏蔽",notes = "/shield/delete")
    @PostMapping("/delete")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO delete(@RequestBody UserIdVO userIdVO, HttpServletRequest request){

        String toUserId = userIdVO.getUserId();
        // 1. 前端参数完整性检验
        if (StringUtils.isEmpty(toUserId)) {
            log.error("【取消屏蔽】传入的参数不正确，toUserId: {}", toUserId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        //2. 写入数据库
        String fromUserId = redisUtil.getUserIdByToken(request);

        shieldService.deleteShieldRecord(fromUserId, toUserId);

        return ResultVOUtil.success();
    }

    @ApiOperation(value = "获取屏蔽列表",notes = "/shield/getList")
    @PostMapping("/getList")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO getList(HttpServletRequest request){

        //2. 写入数据库
        String fromUserId = redisUtil.getUserIdByToken(request);

        List<ShieldedUserVO> result = shieldService.queryShieldedUserId(fromUserId);

        if(CollectionUtils.isEmpty(result)){
            return ResultVOUtil.error(ResultEnum.GET_SHIELD_USER_LIST_FAIL.getCode(), ResultEnum.GET_SHIELD_USER_LIST_FAIL.getMessage());
        }

        return ResultVOUtil.success(result);
    }
}
