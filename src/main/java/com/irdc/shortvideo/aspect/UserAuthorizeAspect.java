//package com.irdc.shortvideo.aspect;
//
//import com.irdc.shortvideo.constant.RedisConstant;
//import com.irdc.shortvideo.exception.UserAuthorizeException;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import java.util.concurrent.TimeUnit;
//
///**
// * Package shortvideo
// *
// * @author yangshu on 2020/10/5 16:10
// * Description：用户登录验证
// */
//
//@Aspect
//@Component
//@Slf4j
//public class UserAuthorizeAspect {
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Pointcut("execution(public * com.irdc.shortvideo.controller..*.*(..))" +
//            "&& !execution(public * com.irdc.shortvideo.controller.user.LoginController.*(..))")
//    public void verify1(){}
//
//    @Pointcut("!execution(public * com.irdc.shortvideo.controller.user.RegisterController.*(..))")
//    public void verify2(){}
//
//    @Pointcut("verify1() && verify2()")
//    public void verify(){}
//
//    @Before("verify()")
//    public void doVerify(){
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        assert attributes != null;
//        HttpServletRequest request = attributes.getRequest();
//
//
//        // 1. 查询token
//        String token = request.getHeader("token");
//        if(token == null){
//            log.warn("【登录校验】header中查不到token");
//            throw new UserAuthorizeException();
//        }
//        // 2. 查询redis  -- 根据token中的得到userId，如果token是伪造的话，就会查不到对应的redis
//        String userId = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PERFIX, token));
//        if(userId == null){
//            log.warn("【登录校验】Redis中查不到token");
//            throw new UserAuthorizeException();
//        }
//
//        // 3. 用户验证通过后，更新token在redis中的过期时间
//        Long expireToken = redisTemplate.getExpire(String.format(RedisConstant.TOKEN_PERFIX, token), TimeUnit.HOURS);
//        System.out.println(expireToken);
//        // 过期时间只剩一个小时，更新expire
//        if(expireToken == null){
//            log.warn("【登录校验】Redis中查不到token");
//            throw new UserAuthorizeException();
//        }
//        if(expireToken < 1) {
//            redisTemplate.expire(String.format(RedisConstant.TOKEN_PERFIX, token),RedisConstant.EXPIRE_USER_LOGIN, TimeUnit.DAYS);
//            redisTemplate.expire(String.format(RedisConstant.USERID_PERFIX, userId),RedisConstant.EXPIRE_USER_LOGIN, TimeUnit.DAYS);
//        }
//
//    }
//}
