package com.lee.exam.service;/*
 *@Author lee
 * @date 2021/03/17
 */

import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {

    String uploadFileAvatar(MultipartFile file);
}
