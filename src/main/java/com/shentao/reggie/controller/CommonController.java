package com.shentao.reggie.controller;

import com.shentao.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/*
文件上传和下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /*
    文件上传
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        log.info(file.toString());

        //原始的文件名
        String originalFilename = file.getOriginalFilename();
        //获得文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID重新生成文件名，防止文件名重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basePath+originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
