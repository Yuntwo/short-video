package com.irdc.shortvideo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/17 18:42
 * Description：
 */

@Data
public class PackageInfo {

    @Id
    private String id;

    /**
     * 版本号
     */
    @Column(name = "number")
    private String number;

    /**
     * 版本描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 版本地址
     */
    private String url;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
