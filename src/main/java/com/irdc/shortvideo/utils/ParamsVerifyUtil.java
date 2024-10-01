package com.irdc.shortvideo.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author yangshu on 2020/11/20 15:28
 * Description：controller中传递的参数进行参数校验
 * 表单验证
 */
public class ParamsVerifyUtil {

    public static String processErrorString(BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(FieldError fieldError:bindingResult.getFieldErrors()){
            stringBuilder.append(fieldError.getDefaultMessage()).append(",");
        }
        return stringBuilder.substring(0,stringBuilder.length()-1);
    }

}
