package com.irdc.shortvideo.controller.lab;

import com.irdc.shortvideo.VO.LabVO;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.annotation.UserAuthentication;
import com.irdc.shortvideo.enums.AuthAopEnum;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.service.LabService;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Package short-video
 *
 * @author chenliquan on 2020/11/26 14:59
 * Description：
 */

@Api(value = "实验室接口", tags = {"实验室"})
@RestController
@RequestMapping("/lab")
@Slf4j
public class LabController {
    @Autowired
    private LabService labService;

    @UserAuthentication(pass = true, role = AuthAopEnum.USER)
    @ApiOperation(value = "获得所有实验室信息",notes = "/lab/all")
    @GetMapping("/all")
    public ResultVO getAllLab() {
        List<LabVO> result = labService.queryAllLab();
        if(CollectionUtils.isEmpty(result)) {
            return ResultVOUtil.error(ResultEnum.LAB_IS_EMPTY.getCode(),ResultEnum.LAB_IS_EMPTY.getMessage());
        }
        return ResultVOUtil.success(result);
    }
}
