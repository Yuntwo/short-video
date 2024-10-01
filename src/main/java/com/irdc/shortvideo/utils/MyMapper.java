package com.irdc.shortvideo.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author yangshu
 */


public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
