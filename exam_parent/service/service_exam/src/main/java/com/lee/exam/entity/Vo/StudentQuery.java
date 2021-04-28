package com.lee.exam.entity.Vo;/*
 *@Author lee
 * @date 2021/03/14
 */

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class StudentQuery {


    private String studentName;
    private String studentId;
    private String institute;
    private String clazz;

}
