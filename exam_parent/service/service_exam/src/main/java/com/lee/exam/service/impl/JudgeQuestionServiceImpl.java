package com.lee.exam.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.exam.Listener.ExcelListener;
import com.lee.exam.Listener.JudgeExcelListener;
import com.lee.exam.entity.ChoiceQuestion;
import com.lee.exam.entity.JudgeQuestion;
import com.lee.exam.entity.Vo.ChoiceQuestionExcel;
import com.lee.exam.entity.Vo.ChoiceQuestionQuery;
import com.lee.exam.entity.Vo.JudgeQuestionExcel;
import com.lee.exam.mapper.JudgeQuestionMapper;
import com.lee.exam.service.BankService;
import com.lee.exam.service.JudgeQuestionService;
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
 * 判断题题库表 服务实现类
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
@Service
public class JudgeQuestionServiceImpl extends ServiceImpl<JudgeQuestionMapper, JudgeQuestion> implements JudgeQuestionService {

    @Override
    public JudgeQuestion listById(String id) {

        return baseMapper.listById(id);
    }

    @Override
    public Map<String, Object> getQuestionList(ChoiceQuestionQuery choiceQuestionQuery) {
        List<JudgeQuestion> question = baseMapper.findQuestion(choiceQuestionQuery);
        Integer totals = baseMapper.totals(choiceQuestionQuery);
        HashMap map = new HashMap();
        map.put("question",question);
        map.put("totals",totals);
        return map;
    }

    @Override
    public String addBatch(MultipartFile file, JudgeQuestionService judgeQuestionService, BankService bankService) {
        String flag="";
        try{
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, JudgeQuestionExcel.class,new JudgeExcelListener(judgeQuestionService,bankService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("************");
            flag="fail";
        }
        System.out.println(flag);
        return flag;

    }

    @Override
    public JudgeQuestion QueryByLevelAndNum(String num, String level,String bankId) {

        QueryWrapper<JudgeQuestion> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(num)) {
            queryWrapper.eq("num",Integer.valueOf(num).intValue());
        }
        if(!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level",level);
        }
        queryWrapper.eq("bankId",bankId);
        JudgeQuestion judgeQuestion = baseMapper.selectOne(queryWrapper);
        return judgeQuestion;
    }

    @Override
    public Integer QueryByLevel(String level,String bankId) {
        QueryWrapper<JudgeQuestion> queryWrapper = new QueryWrapper<>();
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
