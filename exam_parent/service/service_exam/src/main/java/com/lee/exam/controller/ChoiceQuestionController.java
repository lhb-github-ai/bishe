package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.exam.R;
import com.lee.exam.entity.Bank;
import com.lee.exam.entity.ChoiceQuestion;
import com.lee.exam.entity.Paper;
import com.lee.exam.entity.Vo.ChoiceQuestionQuery;
import com.lee.exam.service.BankService;
import com.lee.exam.service.ChoiceQuestionService;
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
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
@CrossOrigin
@RestController
@RequestMapping("/exam/choice_question")
public class ChoiceQuestionController {

    /****************** select  *  from  choice_question order by rand() limit 2   ********************/

    @Autowired
    private ChoiceQuestionService questionService;
    @Autowired
    private BankService bankService;
    @Autowired
    private PaperService paperService;

    @ApiOperation(value = "所有题目列表")
    @GetMapping("list")
    public R listAll(){
        List<ChoiceQuestion> list = questionService.list(null);
        return  R.ok().data("list",list);
    }
    @ApiOperation(value = "分页所有题目列表")
    @GetMapping("listByPage/{current}/{limit}")
    public R listByPage(@PathVariable Long current,@PathVariable Long limit){
        Page<ChoiceQuestion> page=new Page<>(current,limit);
        IPage<ChoiceQuestion> questionIPage = questionService.page(page, null);
        long total = questionIPage.getTotal();
        List<ChoiceQuestion> records = questionIPage.getRecords();
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
//        courseQuery.setPageNumber((int) ((current-1)*limit));
//        courseQuery.setPageSize((int) limit);
        Map<String ,Object> map= questionService.getQuestionList(choiceQuestionQuery);
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加")
    @PostMapping("addChoiceQuestion")
    public R  addBank(@RequestBody ChoiceQuestion choiceQuestion){

        QueryWrapper<Bank> wrapperName = new QueryWrapper<>();
        if(!StringUtils.isEmpty(choiceQuestion.getBankName())) {

            wrapperName.eq("name",choiceQuestion.getBankName());
        }
        Bank bankServiceOne = bankService.getOne(wrapperName);
        QueryWrapper<ChoiceQuestion> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(choiceQuestion.getQuestion())) {

            wrapper.eq("question",choiceQuestion.getQuestion());
        }
        if (bankServiceOne==null){
            return R.ok().data("nonExistent","nonExistent");
        }
        choiceQuestion.setBankId(choiceQuestion.getBankId());
        QueryWrapper<ChoiceQuestion> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("bankId",choiceQuestion.getBankId());
        wrapper1.eq("level",choiceQuestion.getLevel());
        choiceQuestion.setNum(questionService.count(wrapper1)+1);
        ChoiceQuestion choiceQuestion1 = questionService.getOne(wrapper);
        if (choiceQuestion1==null){
            boolean save = questionService.save(choiceQuestion);

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
    public R  removeTeacher(@PathVariable String id){
        ChoiceQuestion choiceQuestion = questionService.getById(id);
        QueryWrapper<Bank> wrapperName = new QueryWrapper<>();
        if(!StringUtils.isEmpty(choiceQuestion.getBankId())) {

            wrapperName.eq("id",choiceQuestion.getBankId());
        }
        Bank bankServiceOne = bankService.getOne(wrapperName);

        Integer bankCount = bankServiceOne.getBankCount();
        bankServiceOne.setBankCount(bankCount-1);

        boolean flag = questionService.removeById(id);

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
        ChoiceQuestion choiceQuestion = questionService.listById(id);
        return R.ok().data("choiceQuestion",choiceQuestion);
    }
    @ApiOperation(value = "修改")
    @PostMapping("update")
    public  R updateBank(@RequestBody ChoiceQuestion choiceQuestion){

        QueryWrapper<Bank> wrapperName = new QueryWrapper<>();
        if(!StringUtils.isEmpty(choiceQuestion.getQuestion())) {

            wrapperName.eq("name",choiceQuestion.getBankName());
        }

        Bank bankServiceOne = bankService.getOne(wrapperName);
        if (bankServiceOne==null){
            return R.ok().data("nonExistent","nonExistent");
        }
        choiceQuestion.setBankId(choiceQuestion.getBankId());

        QueryWrapper<ChoiceQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("question",choiceQuestion.getQuestion());
        ChoiceQuestion ChoiceQuestion1 = questionService.getOne(wrapper);
        if (ChoiceQuestion1==null){
            boolean update = questionService.updateById(choiceQuestion);
            if(update) {
                return R.ok();
            } else {
                return R.error();
            }
        } else  if(ChoiceQuestion1.getId().equals(choiceQuestion.getId())){
            boolean update = questionService.updateById(choiceQuestion);
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
    //    System.out.println("***********+"+level+"+*******************"+paperId);
        Paper paper = paperService.getById(paperId);
        Integer number=questionService.QueryByLevel(level,paper.getBankId());

        return  R.ok().data("number",number);
    }

    /* 批量添加运用excel*/

    @PostMapping("addBatch")
    public R  addBatch(MultipartFile file){
       String msg= questionService.addBatch(file,questionService,bankService);
       if (msg.equals("fail")){
           return R.error().data("msg",msg);
       }
        return R.ok();
    }
}

