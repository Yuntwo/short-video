package com.irdc.shortvideo.rabbitmq.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/12 16:00
 * Description：
 */

@Data
public class ReportMessage implements Serializable {

    private static final long serialVersionUID = 5001696386161002485L;

    private String videoId;

    private String userId;

    private String content;

    private String tag;

}