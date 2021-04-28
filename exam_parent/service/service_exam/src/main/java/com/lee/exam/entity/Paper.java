package com.lee.exam.entity;

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
@ApiModel(value="Paper对象", description="")
public class Paper implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "试卷总分")
    @TableField("total_score")
    private String totalScore;

    @ApiModelProperty(value = "考试时间")
    private String time;

    @ApiModelProperty(value = "时间段")
    private String timeFrame;

    @ApiModelProperty(value = "试卷描述")
    private String description;

    @ApiModelProperty(value = "题库id")
    @TableField("bank_id")
    private String bankId;

    @ApiModelProperty(value = "试卷名称")
    @TableField("paper_name")
    private String paperName;

    @ApiModelProperty(value = "是否为正式试卷")
    @TableField("type")
    private String type;


    @ApiModelProperty(value = "试卷作者")
    @TableField("author_id")
    private String  authorId;

    @TableField(exist = false)
    private String  teacherName;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Boolean isDeleted;

    @TableField(exist = false)
    private String BankName;

    @TableField(exist = false)
    private Boolean  IsAvailable;

    @TableField(exist = false)
    private String  Tip;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "考试次数")
    @TableField("number")
    private Integer number;



    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
