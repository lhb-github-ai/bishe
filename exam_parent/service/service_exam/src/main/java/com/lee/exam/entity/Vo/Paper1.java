package com.lee.exam.entity.Vo;/*
 *@Author lee
 * @date 2021/01/16
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Paper1 {

    private String paperId;
    @ApiModelProperty(value = "试卷总分")
    @TableField("total_score")
    private String totalScore;

    @ApiModelProperty(value = "考试时间")
    private String time;

    @ApiModelProperty(value = "时间段")
    private String timeFrame;

    private String type;

    private Integer status;
}
