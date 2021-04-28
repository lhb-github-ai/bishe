package com.lee.exam.mapper;

import com.lee.exam.entity.PaperQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
public interface PaperQuestionMapper extends BaseMapper<PaperQuestion> {

    List<PaperQuestion> listQuestion(String paerp_id, String doubleCheck,String isZOrS);

    List<PaperQuestion> listJudgeQuestion(String paerp_id, String doubleCheck,String isZOrS);

    List<PaperQuestion> listQuestionDX(String paerp_id, String doubleCheck);

    List<PaperQuestion> listQuestionPD(String paerp_id, String doubleCheck);
}
