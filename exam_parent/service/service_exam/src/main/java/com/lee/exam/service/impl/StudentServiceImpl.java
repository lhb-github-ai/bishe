package com.lee.exam.service.impl;

import com.alibaba.excel.EasyExcel;
import com.lee.exam.Listener.ExcelListener;
import com.lee.exam.Listener.StudentExcelListener;
import com.lee.exam.entity.Student;
import com.lee.exam.entity.Vo.ChoiceQuestionExcel;
import com.lee.exam.entity.Vo.StudentExcel;
import com.lee.exam.mapper.StudentMapper;
import com.lee.exam.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-03-14
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public String addBatch(MultipartFile file, StudentService studentService) {

        String flag="";
        try{
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, StudentExcel.class,new StudentExcelListener(studentService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
            //  System.out.println("************");
            flag="fail";
        }
        // System.out.println(flag);
        return flag;
    }
}
