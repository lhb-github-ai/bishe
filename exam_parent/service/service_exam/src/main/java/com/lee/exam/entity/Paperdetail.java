package com.lee.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Paperdetail对象", description="")
public class Paperdetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "题目id")
    private String questionId;

    @ApiModelProperty(value = "试卷Id")
    private String paperId;

    @TableField(exist = false)
    @ApiModelProperty(value = "试卷Id")
    private String paerpId;

    @ApiModelProperty(value = "第几次考试")
    private Integer frequency;

    @ApiModelProperty(value = "填的答案")
    private String options;

    @ApiModelProperty(value = "学生id")
    private String studentId;

    @ApiModelProperty(value = "0表示单选 1表示判断")
    private Integer doubleCheck;


}
