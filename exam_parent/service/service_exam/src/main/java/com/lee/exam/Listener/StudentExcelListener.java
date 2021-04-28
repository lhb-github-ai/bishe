package com.lee.exam.Listener;/*
 *@Author lee
 * @date 2021/03/20
 */

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import com.lee.exam.entity.JudgeQuestion;
import com.lee.exam.entity.Student;
import com.lee.exam.entity.Vo.StudentExcel;
import com.lee.exam.service.JudgeQuestionService;
import com.lee.exam.service.StudentService;
import org.springframework.beans.BeanUtils;

import java.util.Map;

public class StudentExcelListener extends AnalysisEventListener<StudentExcel> {

    public StudentService studentService;

    public StudentExcelListener(StudentService studentService) {
         this.studentService=studentService;
    }

    @Override
    public void invoke(StudentExcel studentExcel, AnalysisContext context) {
        Student student = new Student();
        BeanUtils.copyProperties(studentExcel,student);
        student.setPwd(student.getStudentId());
        student.setAvatar("https://www.hualigs.cn/image/6058803b2e8a1.jpg");
        studentService.save(student);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
    //读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头："+headMap);
    }
}
