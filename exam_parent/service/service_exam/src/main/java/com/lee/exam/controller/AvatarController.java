package com.lee.exam.controller;/*
 *@Author lee
 * @date 2021/03/17
 */

import com.lee.exam.R;
import com.lee.exam.service.AvatarService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(description="头像")
@CrossOrigin
@RestController
@RequestMapping("/avatar")
public class AvatarController {

    @Autowired
    public AvatarService avatarService;
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        String url =avatarService.uploadFileAvatar(file);

        return R.ok().data("url",url);
    }

}
