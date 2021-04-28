package com.lee.exam.service;

import com.lee.exam.entity.JudgeQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.exam.entity.Vo.ChoiceQuestionQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 判断题题库表 服务类
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
public interface JudgeQuestionService extends IService<JudgeQuestion> {

    JudgeQuestion listById(String id);

    Map<String, Object> getQuestionList(ChoiceQuestionQuery choiceQuestionQuery);

    String addBatch(MultipartFile file, JudgeQuestionService judgeQuestionService, BankService bankService);

    JudgeQuestion QueryByLevelAndNum(String num, String level,String bankId);

    Integer QueryByLevel(String level,String bankId);
}
