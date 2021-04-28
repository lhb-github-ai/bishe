package com.lee.exam.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PaperQuestion对象", description="")
public class PaperQuestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "试卷id")
    private String paerpId;

    @TableField(exist = false)
    private String QuestionName;

    @ApiModelProperty(value = "题库id")
    private String questionId;



    @ApiModelProperty(value = "0表示单选，1表示判断")
    private Integer doubleCheck;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
    @ApiModelProperty(value = "1表示自定义，0表示随机")
    private Integer IsZOrS;

    ////ChoiceQuestion
    @TableField(exist = false)
    private String optionA;
    @TableField(exist = false)
    private String optionB;
    @TableField(exist = false)
    private String optionC;
    @TableField(exist = false)
    private String optionD;
    @TableField(exist = false)
    private String analysis;
    @TableField(exist = false)
    private String answer;
    @TableField(exist = false)
    private  String updateTime;
}
