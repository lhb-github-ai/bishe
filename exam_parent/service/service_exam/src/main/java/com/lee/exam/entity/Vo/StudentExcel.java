package com.lee.exam.entity.Vo;/*
 *@Author lee
 * @date 2021/03/20
 */

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class StudentExcel {

    @ExcelProperty(value = "学生姓名")
    private String studentName;

        @ExcelProperty(value = "学生年龄")
    private Integer age;

    @ExcelProperty(value = "学生头像")
    private String avatar;

    @ExcelProperty(value = "学生账号")
    private String studentId;

    @ExcelProperty(value = "性别")
    private String sex;

    @ExcelProperty(value = "学院")
    private String institute;

    @ExcelProperty(value = "班级")
    private String clazz;

    @ExcelProperty(value = "电话号码")
    private String tel;

    @ExcelProperty(value = "邮箱")
    private String email;
}
