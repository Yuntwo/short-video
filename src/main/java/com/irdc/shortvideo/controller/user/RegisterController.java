package com.irdc.shortvideo.controller.user;

import com.irdc.shortvideo.VO.RegisterVO;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.constant.RedisConstant;
import com.irdc.shortvideo.entity.Users;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.service.UserService;
import com.irdc.shortvideo.utils.PasswordUtil;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import com.irdc.shortvideo.utils.UUIDUtil;
import com.irdc.shortvideo.utils.aliyun.SmssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashMap;

/**
 * Package short-video
 * Created by chenliquan on 2020/9/30 19:42
 * Description：
 */

@Api(value = "用户注册接口",tags = {"用户注册"})
@RestController
@RequestMapping("/user")
@Slf4j
@Validated
public class RegisterController {

    @Autowired
    private SmssUtil smssUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "用户注册",notes = "/user/register")
    @PostMapping("/register")
    public ResultVO register(@Validated @RequestBody RegisterVO registerVO) {

        // 1. 确认前端传递参数的完整性
        if(StringUtils.isEmpty(registerVO.getPhone()) || StringUtils.isEmpty(registerVO.getNewPassword().isEmpty()) ||
                StringUtils.isEmpty(registerVO.getVerifyCode().isEmpty())) {
            log.error("【用户注册】传入的参数不正确，registerVO: {}",registerVO);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }

        String phone = registerVO.getPhone();

        // 2. 判断参数的正确性
        // 2.2 手机号是否被注册
        boolean phoneIsExist = userService.queryPhoneIsExist(phone);
        if(phoneIsExist) {
            log.error("【用户注册】用户已被注册，phone: {}",registerVO.getPhone());
            return ResultVOUtil.error(ResultEnum.USER_IS_REGISTED);
        }
        // 2.3 验证码是否正确
        // 2.3.1 验证码是否过期
        String redisVerifyCode = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.VERIFYCODE_REGISTER_PREFIX, phone));
        if(StringUtils.isEmpty(redisVerifyCode)) {
            log.error("【用户注册】验证码已过期，redisVerifyCode: {}",redisVerifyCode);
            return ResultVOUtil.error(ResultEnum.VERIFY_CODE_HAS_EXPIRED.getCode(),ResultEnum.VERIFY_CODE_HAS_EXPIRED.getMessage());
        }
        // 2.3.2 验证码是否匹配
        if(!registerVO.getVerifyCode().equals(redisVerifyCode)) {
            log.error("【用户注册】验证码输入错误，redisVerifyCode: {}",redisVerifyCode + "输入的验证码：" + registerVO.getVerifyCode());
            return ResultVOUtil.error(ResultEnum.VERIFY_CODE_IS_ERROR.getCode(),ResultEnum.VERIFY_CODE_IS_ERROR.getMessage());
        }

        // 3. 保存用户信息
        // 3.1 todo: 前端传过来的密码进行解密
        String decodePassword = registerVO.getNewPassword();
        // 3.2 对密码进行加密
        String salt = PasswordUtil.randomSalt();
        String encodePassword = PasswordUtil.encryptPassword(decodePassword, salt);

        Users user = new Users();
        // todo：生成一个随机序列作为用户的名称，设置用户名密码这部分应该直接的放在service层中，不应该在controller中出现
        user.setPhone(registerVO.getPhone());
        user.setPassword(encodePassword);
        user.setSalt(salt);
        user.setUserId(Sid.next());
        user.setSex(0);
        user.setAge(0);

        userService.createUser(user);

        HashMap<String, String> map = redisUtil.userLoginRedis(user.getUserId());

        // 4. 返回应答
        return ResultVOUtil.success(map);
    }

    @ApiOperation(value = "获取验证码",notes = "/user/register/getVerifyCode")
    @GetMapping("/register/getVerifyCode/{phone}")
    public ResultVO getIdentityCode(@Size(max = 16) @PathVariable String phone) {
        if(StringUtils.isEmpty(phone)) {
            log.error("【用户注册-获取验证码】手机号为空，phone: {}",phone);
            return ResultVOUtil.error(ResultEnum.PHONE_IS_EMPTY.getCode(),ResultEnum.PHONE_IS_EMPTY.getMessage());
        }
        smssUtil.sendSms(phone,RedisConstant.VERIFYCODE_REGISTER_PREFIX);

        return ResultVOUtil.successMsg(ResultEnum.SENDING_VERIFY_CODE.getMessage());
    }
}
