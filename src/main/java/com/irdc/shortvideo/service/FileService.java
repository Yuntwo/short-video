package com.irdc.shortvideo.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/11 15:35
 * Description：
 */
public interface FileService {

    String uploadFile(MultipartFile file);
}
