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
 * @since 2021-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Score对象", description="")
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "试卷ID")
    @TableField("paper_id")
    private String paperId;

    @TableField(exist = false)
    private String paperName;

    @ApiModelProperty(value = "学生id")
    @TableField("student_id")
    private String studentId;

    @TableField(exist = false)
    private String studentName;

    @ApiModelProperty(value = "分数")
    private String score;

    @ApiModelProperty(value = "0 表示未考试 1表示已考试 （仅在正式试卷中有效）")
    @TableField("paper_status")
    private Integer  paperStatus;

    @ApiModelProperty(value = "第几次考试")
    private Integer frequency;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Boolean isDeleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
