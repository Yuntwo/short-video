package com.irdc.shortvideo.utils;

import org.n3r.idworker.Sid;

import java.util.UUID;


/**
 * 产生随机序列
 * @author chenliquan
 */
public class UUIDUtil {
    /**
     * 随机生成id
     *
     * @return
     */
    public static String getId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 生成随机码，防止重复的添加
     *
     * @return
     */
    public static String getCode() {
        return getId();
    }

    public static void main(String[] args) {
        System.out.println(Sid.next());
        System.out.println(getId());
        System.out.println(getCode());
    }
}
