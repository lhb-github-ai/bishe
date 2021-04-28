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
 * 判断题题库表
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="JudgeQuestion对象", description="判断题题库表")
public class JudgeQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "试题编号")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "题库名称")
    @TableField("bankId")
    private String bankId;

    @TableField(exist = false)
    @ExcelProperty(value = "题库名")
    private String bankName;

    @ApiModelProperty(value = "试题内容")
    private String question;

    @ApiModelProperty(value = "正确答案")
    private String answer;

    @ApiModelProperty(value = "题目解析")
    private String analysis;

    @ApiModelProperty(value = "难度等级")
    private String level;

    @ApiModelProperty(value = "排序")
    private Integer Num;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
