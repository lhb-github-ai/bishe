package com.lee.exam.service;

import com.lee.exam.entity.ChoiceQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.exam.entity.Vo.ChoiceQuestionQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
public interface ChoiceQuestionService extends IService<ChoiceQuestion> {


    Map<String, Object> getQuestionList(ChoiceQuestionQuery choiceQuestionQuery);

    ChoiceQuestion listById(String id);

    String addBatch(MultipartFile file, ChoiceQuestionService questionService, BankService bankService);


    ChoiceQuestion QueryByLevelAndNum(String num, String level,String bankId);

    Integer QueryByLevel(String level,String bankId);
}
