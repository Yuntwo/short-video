package com.irdc.shortvideo.mapper;

import com.irdc.shortvideo.VO.LabVO;
import com.irdc.shortvideo.entity.Lab;
import com.irdc.shortvideo.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 11:52
 * Description：
 */
@Mapper
public interface LabMapper extends MyMapper<Lab> {
    List<LabVO> queryAllLab();
}
