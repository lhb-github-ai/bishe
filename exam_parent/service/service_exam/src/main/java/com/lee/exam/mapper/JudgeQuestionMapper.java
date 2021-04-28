package com.lee.exam.mapper;

import com.lee.exam.entity.ChoiceQuestion;
import com.lee.exam.entity.JudgeQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.exam.entity.Vo.ChoiceQuestionQuery;

import java.util.List;

/**
 * <p>
 * 判断题题库表 Mapper 接口
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
public interface JudgeQuestionMapper extends BaseMapper<JudgeQuestion> {

    JudgeQuestion listById(String id);

    List<JudgeQuestion> findQuestion(ChoiceQuestionQuery choiceQuestionQuery);

    Integer totals(ChoiceQuestionQuery choiceQuestionQuery);

}
