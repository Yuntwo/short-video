package com.irdc.shortvideo.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * Package videoBack
 * Created by yangshu on 2020/9/20 15:38
 * Description：
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = -622823529324993068L;


    // 错误码
    private Integer code;

    // 提示信息
    private String msg;

    // 数据内容
    private T data;
}
