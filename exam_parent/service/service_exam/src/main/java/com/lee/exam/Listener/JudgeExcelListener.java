package com.lee.exam.Listener;/*
 *@Author lee
 * @date 2020/09/15
 */

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.exam.entity.Bank;
import com.lee.exam.entity.ChoiceQuestion;
import com.lee.exam.entity.JudgeQuestion;
import com.lee.exam.entity.Vo.ChoiceQuestionExcel;
import com.lee.exam.entity.Vo.JudgeQuestionExcel;
import com.lee.exam.service.BankService;
import com.lee.exam.service.ChoiceQuestionService;
import com.lee.exam.service.JudgeQuestionService;
import com.lee.servicebase.exceptionhandler.ExamException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Map;

public class JudgeExcelListener extends AnalysisEventListener<JudgeQuestionExcel> {

    public JudgeQuestionService judgeQuestionService;
    public BankService bankService;

    public JudgeExcelListener(JudgeQuestionService judgeQuestionService, BankService bankService) {
        this.judgeQuestionService=judgeQuestionService;
        this.bankService=bankService;
    }

    @Override
    public void invoke(JudgeQuestionExcel judgeQuestionExcel, AnalysisContext analysisContext) {

        JudgeQuestion judgeQuestion = new JudgeQuestion();
        BeanUtils.copyProperties(judgeQuestionExcel,judgeQuestion);
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
      //  System.out.println("ssssssssssssssssssss"+bankService.bankIsNull(judgeQuestionExcel.getBankName()));
        if (bankService.bankIsNull(judgeQuestionExcel.getBankName())==0){
            throw new ExamException(20001, "您的excel表格中题库名不存在，请改正excel表格之后重试");
        }
        if ((!StringUtils.isEmpty(judgeQuestionExcel.getBankName()))) {
            wrapper.eq("name",judgeQuestionExcel.getBankName());
        }
        Bank bankServiceOne = bankService.getOne(wrapper);
        judgeQuestion.setBankId(bankServiceOne.getId());
        QueryWrapper<JudgeQuestion> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("bankId",judgeQuestion.getBankId());
        wrapper1.eq("level",judgeQuestion.getLevel());
        judgeQuestion.setNum(judgeQuestionService.count(wrapper1)+1);
      //  System.out.println("*****"+judgeQuestionExcel);
      //  System.out.println("+++++++++++"+judgeQuestion);
        judgeQuestionService.save(judgeQuestion);

        Integer bankCount = bankServiceOne.getBankCount();
        bankServiceOne.setBankCount(bankCount+1);
        bankService.updateById(bankServiceOne);
//        System.out.println("*****"+judgeQuestionExcel);
//        System.out.println("+++++++++++"+choiceQuestion);
    }



    //读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头："+headMap);
    }



    //读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
