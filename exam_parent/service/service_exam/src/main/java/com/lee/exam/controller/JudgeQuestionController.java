package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.exam.R;
import com.lee.exam.entity.Bank;
import com.lee.exam.entity.ChoiceQuestion;
import com.lee.exam.entity.JudgeQuestion;
import com.lee.exam.entity.Paper;
import com.lee.exam.entity.Vo.ChoiceQuestionQuery;
import com.lee.exam.service.BankService;
import com.lee.exam.service.JudgeQuestionService;
import com.lee.exam.service.PaperService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 判断题题库表 前端控制器
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
@CrossOrigin
@RestController
@RequestMapping("/exam/judge_question")
public class JudgeQuestionController {

    @Autowired
    private JudgeQuestionService judgeQuestionService;
    @Autowired
    private BankService bankService;
    @Autowired
    private PaperService paperService;

    @ApiOperation(value = "所有题目列表")
    @GetMapping("list")
    public R listAllJudgeQuestion(){
        List<JudgeQuestion> list = judgeQuestionService.list(null);
        return  R.ok().data("list",list);
    }

    @ApiOperation(value = "分页所有题目列表")
    @GetMapping("listByPage/{current}/{limit}")
    public R listByPage(@PathVariable Long current, @PathVariable Long limit){
        Page<JudgeQuestion> page=new Page<>(current,limit);
        IPage<JudgeQuestion> questionIPage = judgeQuestionService.page(page, null);
        long total = questionIPage.getTotal();
        List<JudgeQuestion> records = questionIPage.getRecords();
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }

    @ApiOperation(value = "分页带条件查询题目列表")
    @PostMapping("listByPage/{current}/{limit}")
    public R listByPageAndName(@PathVariable long current, @PathVariable long limit , @RequestBody(required = false) ChoiceQuestionQuery choiceQuestionQuery){

        choiceQuestionQuery.setCurrent(((current-1)*limit));
        choiceQuestionQuery.setLimit(limit);
        Map<String ,Object> map= judgeQuestionService.getQuestionList(choiceQuestionQuery);
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加")
    @PostMapping("addJudgeQuestion")
    public R  addJudgeQuestion(@RequestBody JudgeQuestion judgeQuestion){

        QueryWrapper<Bank> wrapperName = new QueryWrapper<>();
        if(!StringUtils.isEmpty(judgeQuestion.getBankName())) {

            wrapperName.eq("name",judgeQuestion.getBankName());
        }
        Bank bankServiceOne = bankService.getOne(wrapperName);
        QueryWrapper<JudgeQuestion> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(judgeQuestion.getQuestion())) {

            wrapper.eq("question",judgeQuestion.getQuestion());
        }
        if (bankServiceOne==null){
            return R.ok().data("nonExistent","nonExistent");
        }
        judgeQuestion.setBankId(judgeQuestion.getBankId());
        QueryWrapper<JudgeQuestion> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("bankId",judgeQuestion.getBankId());
        wrapper1.eq("level",judgeQuestion.getLevel());

        judgeQuestion.setNum(judgeQuestionService.count(wrapper1)+1);
        JudgeQuestion judgeQuestion1 = judgeQuestionService.getOne(wrapper);
        if (judgeQuestion1==null){
            boolean save = judgeQuestionService.save(judgeQuestion);

            Integer bankCount = bankServiceOne.getBankCount();
            bankServiceOne.setBankCount(bankCount+1);
            if(save) {
                bankService.updateById(bankServiceOne);
                return R.ok();
            } else {
                return R.error();
            }
        }else {
            return R.ok().data("repeat","repeat");
        }
    }
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")
    public R  removeJudgeQuestion(@PathVariable String id){
        JudgeQuestion judgeQuestion = judgeQuestionService.getById(id);
        QueryWrapper<Bank> wrapperName = new QueryWrapper<>();
        if(!StringUtils.isEmpty(judgeQuestion.getBankId())) {

            wrapperName.eq("id",judgeQuestion.getBankId());
        }
        Bank bankServiceOne = bankService.getOne(wrapperName);

        Integer bankCount = bankServiceOne.getBankCount();
        bankServiceOne.setBankCount(bankCount-1);

        boolean flag = judgeQuestionService.removeById(id);

        if(flag) {
            bankService.updateById(bankServiceOne);
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "根据id查找")
    @GetMapping("listById/{id}")
    public R listById(@PathVariable String id){
        JudgeQuestion judgeQuestion = judgeQuestionService.listById(id);
        return R.ok().data("judgeQuestion",judgeQuestion);
    }
    @ApiOperation(value = "修改")
    @PostMapping("update")
    public  R updateJudgeQuestion(@RequestBody JudgeQuestion judgeQuestion){

        QueryWrapper<Bank> wrapperName = new QueryWrapper<>();
        if(!StringUtils.isEmpty(judgeQuestion.getQuestion())) {

            wrapperName.eq("name",judgeQuestion.getBankName());
        }

        Bank bankServiceOne = bankService.getOne(wrapperName);
        if (bankServiceOne==null){
            return R.ok().data("nonExistent","nonExistent");
        }
        judgeQuestion.setBankId(judgeQuestion.getBankId());

        QueryWrapper<JudgeQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("question",judgeQuestion.getQuestion());
        JudgeQuestion judgeQuestion1 = judgeQuestionService.getOne(wrapper);
        if (judgeQuestion1==null){
            boolean update = judgeQuestionService.updateById(judgeQuestion);
            if(update) {
                return R.ok();
            } else {
                return R.error();
            }
        } else  if(judgeQuestion1.getId().equals(judgeQuestion.getId())){
            boolean update = judgeQuestionService.updateById(judgeQuestion);
            if(update) {
                return R.ok();
            } else {
                return R.error();
            }
        }
        else {
            return R.ok().data("repeat","repeat");
        }
    }

    @ApiOperation(value = "根据Level查找")
    @GetMapping("listByLV/{level}/{paperId}")
    public R QueryByLevel(@PathVariable String level,@PathVariable String paperId){
   //     System.out.println("***********+"+level+"+*******************"+paperId);
        Paper paper = paperService.getById(paperId);

        Integer number=judgeQuestionService.QueryByLevel(level,paper.getBankId());

        return  R.ok().data("number",number);
    }


    /* 批量添加运用excel*/

    @PostMapping("addBatch")
    public R  addBatch(MultipartFile file){
        String msg= judgeQuestionService.addBatch(file,judgeQuestionService,bankService);
        if (msg.equals("fail")){
            return R.error().data("msg",msg);
        }
        return R.ok();
    }
}

