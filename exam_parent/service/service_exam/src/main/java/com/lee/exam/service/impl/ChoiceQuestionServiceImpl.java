package com.lee.exam.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.exam.Listener.ExcelListener;
import com.lee.exam.entity.ChoiceQuestion;
import com.lee.exam.entity.Vo.ChoiceQuestionExcel;
import com.lee.exam.entity.Vo.ChoiceQuestionQuery;
import com.lee.exam.mapper.ChoiceQuestionMapper;
import com.lee.exam.service.BankService;
import com.lee.exam.service.ChoiceQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
@Service
public class ChoiceQuestionServiceImpl extends ServiceImpl<ChoiceQuestionMapper, ChoiceQuestion> implements ChoiceQuestionService {


    @Override
    public Map<String, Object> getQuestionList(ChoiceQuestionQuery choiceQuestionQuery) {
        List<ChoiceQuestion> question = baseMapper.findQuestion(choiceQuestionQuery);
        Integer totals = baseMapper.totals(choiceQuestionQuery);
        HashMap map = new HashMap();
        map.put("question",question);
        map.put("totals",totals);
        return map;
    }

    @Override
    public ChoiceQuestion listById(String id) {
        return baseMapper.findById(id);
    }

    @Override
    public String addBatch(MultipartFile file, ChoiceQuestionService questionService, BankService bankService) {
        String flag="";
        try{
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ChoiceQuestionExcel.class,new ExcelListener(questionService,bankService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
          //  System.out.println("************");
            flag="fail";
        }
       // System.out.println(flag);
        return flag;

    }

    @Override
    public ChoiceQuestion  QueryByLevelAndNum(String num, String level,String bankId) {
        QueryWrapper<ChoiceQuestion> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(num)) {

            queryWrapper.eq("num",Integer.valueOf(num).intValue());
        }
        if(!StringUtils.isEmpty(level)) {

            queryWrapper.eq("level",level);
        }
        queryWrapper.eq("bankId",bankId);
        ChoiceQuestion choiceQuestion = baseMapper.selectOne(queryWrapper);
        return choiceQuestion;
    }

    @Override
    public Integer QueryByLevel(String level,String bankId) {
        QueryWrapper<ChoiceQuestion> queryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(level)) {

            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(level)) {

            queryWrapper.eq("bankId",bankId);
        }
        Integer count = baseMapper.selectCount(queryWrapper);
        return count;
    }


}
