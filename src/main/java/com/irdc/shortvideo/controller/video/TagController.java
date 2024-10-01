package com.irdc.shortvideo.controller.video;

import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.service.TagService;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/30 15:59
 * Description：
 */
@Api(value = "获取视频标签接口", tags = {"获取视频标签"})
@RestController
@RequestMapping("/video")
@Slf4j
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "获得视频标签",notes = "/video/tag")
    @GetMapping("/tag")
    public ResultVO videoTag() {
        List<String> result = tagService.queryAllVideoTag();
        return ResultVOUtil.success(result);
    }
}
