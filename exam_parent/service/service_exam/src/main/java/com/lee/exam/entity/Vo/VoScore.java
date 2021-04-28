package com.lee.exam.entity.Vo;/*
 *@Author lee
 * @date 2021/04/16
 */

import lombok.Data;

@Data
public class VoScore {

    private String StudentName;

    private String PaperName;

    private String authorId;

    private Long   current;

    private Long   limit;
}
