package com.irdc.shortvideo.controller.packageInfo;

import com.irdc.shortvideo.VO.PackageInfoVO;
import com.irdc.shortvideo.VO.ResultVO;
import com.irdc.shortvideo.entity.PackageInfo;
import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.service.PackageInfoService;
import com.irdc.shortvideo.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Package shortvideo
 *
 * @author yangshu on 2020/10/17 19:00
 * Description：
 */

@Api(value = "安装包接口", tags = {"安装包接口"})
@RestController
@Slf4j
@RequestMapping("/package")
public class PackageInfoController {

    @Autowired
    private PackageInfoService packageInfoService;

    @ApiOperation(value = "获取最新安装包信息",notes = "/package/get")
    @GetMapping("/get")
    public ResultVO find(){

        PackageInfoVO res = packageInfoService.findOne();

        if(res == null){
            return ResultVOUtil.error(ResultEnum.GET_PACKAGE_INFO_FAILED.getCode(), ResultEnum.GET_PACKAGE_INFO_FAILED.getMessage());
        }

        return ResultVOUtil.success(res);
    }
}
