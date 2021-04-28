package com.lee.exam.mapper;

import com.lee.exam.entity.ChoiceQuestion;
import com.lee.exam.entity.Score;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.exam.entity.Vo.ChoiceQuestionQuery;
import com.lee.exam.entity.Vo.VoScore;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lee
 * @since 2021-04-14
 */
public interface ScoreMapper extends BaseMapper<Score> {

    List<Score> getExamList(VoScore voScore);

    Integer totals(VoScore voScore);
}
