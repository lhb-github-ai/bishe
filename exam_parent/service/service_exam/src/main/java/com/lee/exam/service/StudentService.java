package com.lee.exam.service;

import com.lee.exam.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-03-14
 */
public interface StudentService extends IService<Student> {

    String addBatch(MultipartFile file, StudentService studentService);
}
