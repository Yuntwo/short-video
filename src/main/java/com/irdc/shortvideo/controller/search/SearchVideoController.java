package com.irdc.shortvideo.controller.search;

import com.irdc.shortvideo.VO.*;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.constant.VideoConstant;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.service.SearchService;
import com.irdc.shortvideo.utils.RedisUtil;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.service.VideoService;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Package short-video
 * Created by chenliquan on 2020/9/30 20:20
 * Description：
 */
@Api(value = "视频搜索接口",tags = {"视频搜索接口"})
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchVideoController {


    @Autowired
    private SearchService searchService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private VideoService videoService;

    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    @ApiOperation(value = "保存搜索记录",notes = "/search/saveRecord")
    @PostMapping("/saveRecord")
    public ResultVO searchVideo(@Validated @RequestBody SearchVideoVO searchVideoVO, HttpServletRequest request) {
        String content = searchVideoVO.getSearchContent();
        if(StringUtils.isEmpty(content)) {
            log.error("【搜索视频】传入的参数不正确，content: {}",content);
            return ResultVOUtil.error(ResultEnum.SEARCH_CONTENT_IS_NULL.getCode(),ResultEnum.SEARCH_CONTENT_IS_NULL.getMessage());
        }
        String userId = redisUtil.getUserIdByToken(request);
        searchService.createSearch(userId, content);
        return ResultVOUtil.success();
    }

    @ApiOperation(value = "搜索视频列表", notes = "/search/getSearchVideo")
    @PostMapping("/getSearchVideo")
    @UserAuthentication(pass = true,role = AuthAopEnum.USER)
    public ResultVO getSearchVideo(@Validated @RequestBody GetSearchVideoVO getSearchVideoVO) {
        String searchContent = getSearchVideoVO.getSearchContent();
        if(StringUtils.isEmpty(searchContent)) {
            log.error("【视频搜索】没有输入搜索内容，getSearchVideoVO: {}",searchContent);
            return ResultVOUtil.error(ResultEnum.SEARCH_CONTENT_IS_NULL.getCode(),ResultEnum.SEARCH_CONTENT_IS_NULL.getMessage());
        }
        Integer page = StringUtils.isEmpty(getSearchVideoVO.getPage()) ? 1 : getSearchVideoVO.getPage();
        Integer size = StringUtils.isEmpty(getSearchVideoVO.getSize()) ? VideoConstant.PAGE_SIZE : getSearchVideoVO.getSize();

        PagedResultVO result = videoService.searchVideosByTitleOrDesc(searchContent, page, size);



        return ResultVOUtil.success(result);
    }


    @ApiOperation(value = "获得6大热搜词",notes = "/search/hottest6Search")
    @GetMapping("/hottest6Search")
    public ResultVO hottest6Search(){
        List<String> result = searchService.queryHottest6Search();
        return ResultVOUtil.success(result);
    }
}
