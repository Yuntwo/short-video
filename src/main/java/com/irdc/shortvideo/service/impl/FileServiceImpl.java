package com.irdc.shortvideo.service.impl;

import com.irdc.shortvideo.enums.ResultEnum;
import com.irdc.shortvideo.service.FileService;
import com.irdc.shortvideo.utils.FastDfsUtil;
import com.irdc.shortvideo.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/11 15:36
 * Description：
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private FastDfsUtil fastDfsUtil;

    @Override
    public String uploadFile(MultipartFile file) {

        if (file.isEmpty()) {
            log.error("【文件上传】选择文件不能为空，file: {}", file);
            return null;
        }

        try{
            return fastDfsUtil.uploadFile(file);
        }catch (Exception e){
            log.error("【文件上传】服务异常，Exception: {}", e.getMessage());
            return null;
        }
    }
}
