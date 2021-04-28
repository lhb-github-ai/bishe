package com.lee.exam.entity.Vo;/*
 *@Author lee
 * @date 2020/09/13
 */


import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@Getter
public class ChoiceQuestionQuery implements Serializable {
    private String bankId;
    private String question;
    private String bankName;
    private Long   current;
    private Long   limit;
}
