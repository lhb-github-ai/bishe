package com.lee.exam.entity.Vo;/*
 *@Author lee
 * @date 2021/04/16
 */

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class PaperDetail {

    private String question;

    private String options;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String analysis;

    private String answer;


}
