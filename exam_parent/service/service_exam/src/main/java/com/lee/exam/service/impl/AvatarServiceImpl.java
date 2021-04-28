package com.lee.exam.service.impl;/*
 *@Author lee
 * @date 2021/03/17
 */

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.lee.exam.service.AvatarService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;
import java.io.InputStream;
import org.joda.time.DateTime;


@Service
public class AvatarServiceImpl implements AvatarService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "";
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "";
        String accessKeySecret = "";
        String bucketName = "";
        try (InputStream inputStream = file.getInputStream()) {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
// 上传文件流。
            String filename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid+filename;


            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename=datePath+"/"+filename;

            ossClient.putObject(bucketName, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            String url="https://"+bucketName+"."+endpoint+"/"+filename;
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
