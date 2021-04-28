package com.lee.exam.Listener;/*
 *@Author lee
 * @date 2020/07/25
 */

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.exam.R;
import com.lee.exam.entity.Bank;
import com.lee.exam.entity.ChoiceQuestion;
import com.lee.exam.entity.Vo.ChoiceQuestionExcel;
import com.lee.exam.service.BankService;
import com.lee.exam.service.ChoiceQuestionService;
import com.lee.servicebase.exceptionhandler.ExamException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<ChoiceQuestionExcel> {


    public ChoiceQuestionService questionService;
    public BankService bankService;
    public ExcelListener(ChoiceQuestionService questionService, BankService bankService) {
        this.questionService=questionService;
        this.bankService=bankService;
    }

    //一行行读取
    @Override
    public void invoke(ChoiceQuestionExcel demoExcel, AnalysisContext analysisContext) {

        ChoiceQuestion choiceQuestion = new ChoiceQuestion();
        BeanUtils.copyProperties(demoExcel,choiceQuestion);
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
       // System.out.println("ssssssssssssssssssss"+bankService.bankIsNull(demoExcel.getBankName()));
        if (bankService.bankIsNull(demoExcel.getBankName())==0){
            throw new ExamException(20001, "您的excel表格中题库名不存在，请改正excel表格之后重试");
        }
        if ((!StringUtils.isEmpty(demoExcel.getBankName()))) {
            wrapper.eq("name",demoExcel.getBankName());
        }
        Bank bankServiceOne = bankService.getOne(wrapper);
        choiceQuestion.setBankId(bankServiceOne.getId());
        QueryWrapper<ChoiceQuestion> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("bankId",choiceQuestion.getBankId());
        wrapper1.eq("level",choiceQuestion.getLevel());
        choiceQuestion.setNum(questionService.count(wrapper1)+1);
//        System.out.println("*****"+demoExcel);
//        System.out.println("+++++++++++"+choiceQuestion);
        questionService.save(choiceQuestion);

        Integer bankCount = bankServiceOne.getBankCount();
        bankServiceOne.setBankCount(bankCount+1);
        bankService.updateById(bankServiceOne);
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
