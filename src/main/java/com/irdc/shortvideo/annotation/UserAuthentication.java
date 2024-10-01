package com.irdc.shortvideo.annotation;

import com.irdc.shortvideo.enums.AuthAopEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/6 09:07
 * Description：用户权限注解
 */

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface UserAuthentication {
    /**
     *  true为启用验证
     *  false为跳过验证
     * @return
     */
    boolean pass() default true;

    /**
     * role表示调用注解的用户类型，如果是ANON，则后续切面方法中逻辑控制跳过一些验证；如果是USER则必须通过验证
     * @return
     */
    AuthAopEnum role() default AuthAopEnum.ANON;

}
