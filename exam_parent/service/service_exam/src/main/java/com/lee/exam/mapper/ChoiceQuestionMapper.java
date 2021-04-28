package com.lee.exam.mapper;

import com.lee.exam.entity.ChoiceQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.exam.entity.Vo.ChoiceQuestionQuery;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
public interface ChoiceQuestionMapper extends BaseMapper<ChoiceQuestion> {

    List<ChoiceQuestion> findQuestion(ChoiceQuestionQuery choiceQuestionQuery);

    Integer totals(ChoiceQuestionQuery choiceQuestionQuery);

    Integer number(String bankId);

    ChoiceQuestion findById(String id);



}
