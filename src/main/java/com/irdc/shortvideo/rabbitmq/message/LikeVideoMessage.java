package com.irdc.shortvideo.rabbitmq.message;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/12 21:22
 * Description：
 */

@Data
public class LikeVideoMessage implements Serializable {


    private static final long serialVersionUID = -2985110770814692512L;

    private String userId;

    private String videoId;

    private boolean like;
}
