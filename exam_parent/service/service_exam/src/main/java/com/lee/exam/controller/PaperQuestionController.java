package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.exam.R;
import com.lee.exam.SuJiRandom;
import com.lee.exam.entity.ChoiceQuestion;
import com.lee.exam.entity.JudgeQuestion;
import com.lee.exam.entity.Paper;
import com.lee.exam.entity.PaperQuestion;
import com.lee.exam.entity.Vo.SuJiNum;
import com.lee.exam.entity.Vo.Question;
import com.lee.exam.service.ChoiceQuestionService;
import com.lee.exam.service.JudgeQuestionService;
import com.lee.exam.service.PaperQuestionService;
import com.lee.exam.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/exam/paper-question")
public class PaperQuestionController {

    @Autowired
    private PaperQuestionService paperQuestionService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private ChoiceQuestionService choiceQuestionService;
    @Autowired
    private JudgeQuestionService judgeQuestionService;
    @GetMapping("listByBankId/{paperId}/{level}")
    public R listByBankId(@PathVariable String paperId ,@PathVariable(required = false) String level){
        QueryWrapper<ChoiceQuestion> wrapper = new QueryWrapper<>();
//        System.out.println("**********"+level);
        wrapper.eq("bankId",paperService.getById(paperId).getBankId());
        if (!StringUtils.isEmpty(level)&&!level.equals("全部")){
            wrapper.eq("level",level);
        }
        wrapper.orderByAsc("level");
        List<ChoiceQuestion> choiceQuestions = choiceQuestionService.list(wrapper);

        QueryWrapper<JudgeQuestion> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("bankId",paperService.getById(paperId).getBankId());
        if (!StringUtils.isEmpty(level)&&!level.equals("全部")){
            wrapper1.eq("level",level);
        }
        wrapper1.orderByAsc("level");
        List<JudgeQuestion> judgeQuestion = judgeQuestionService.list(wrapper1);

        return R.ok().data("judgeQuestion",judgeQuestion).data("choiceQuestionServiceOne",choiceQuestions);
    }
    @GetMapping("listByPaperId/{paperId}")
    public  R listByPaperId(@PathVariable String paperId){
        Paper paper =  paperService.listByPaperId(paperId);
        return R.ok().data("paper",paper);
    }
   @PostMapping("/addPaperQuestion")
    public  R addPaperQuestion(@RequestBody Question question, String[] checkedChoiceQuestions){
       QueryWrapper<PaperQuestion> wrapper = new QueryWrapper<>();
       wrapper.eq("paerp_id",question.getPaperId());
       wrapper.eq("double_check",question.getDoubleCheck());
       wrapper.eq("is_z_or_s",1);
       boolean remove = paperQuestionService.remove(wrapper);
       for (int i=0;i<checkedChoiceQuestions.length;i++){
//            System.out.println(question);
//            System.out.println(checkedChoiceQuestions[i]);
            PaperQuestion paperQuestion = new PaperQuestion();
            paperQuestion.setPaerpId(question.getPaperId());
            paperQuestion.setQuestionId(checkedChoiceQuestions[i]);
            paperQuestion.setDoubleCheck(Integer.valueOf(question.getDoubleCheck()));
            paperQuestion.setIsZOrS(1);
            boolean save = paperQuestionService.save(paperQuestion);

           if (!save) {
               return R.error().data("fail", "添加失败");
           }
        }
       return R.ok();
   }
   @GetMapping("listChoiceQuestion/{paerp_id}/{doubleCheck}/{isZOrS}")
    public  R listChoiceQuestion(@PathVariable String paerp_id,@PathVariable String doubleCheck,@PathVariable String isZOrS){

       List<PaperQuestion> paperQuestions = paperQuestionService.listQuestion(paerp_id,doubleCheck,isZOrS);
//       System.out.println("*********************************************************************"+paperQuestions);
       return R.ok().data("paperQuestions",paperQuestions);
   }
    @GetMapping("listChoiceQuestionDX/{paerp_id}/{doubleCheck}")
    public  R listChoiceQuestionDX(@PathVariable String paerp_id,@PathVariable String doubleCheck){

        List<PaperQuestion>   paperQuestions  =  paperQuestionService.listQuestionDX(paerp_id,doubleCheck);

        return R.ok().data("paperQuestionsDX",paperQuestions);
    }
    @GetMapping("listJudgeQuestion/{paerp_id}/{doubleCheck}/{isZOrS}")
    public  R listJudgeQuestion(@PathVariable String paerp_id,@PathVariable String doubleCheck,@PathVariable String isZOrS){

        List<PaperQuestion> paperQuestions = paperQuestionService.listJudgeQuestion(paerp_id,doubleCheck,isZOrS);
        return R.ok().data("paperJudgeQuestions",paperQuestions);
    }
    @GetMapping("listQuestionPD/{paerp_id}/{doubleCheck}")
    public  R listQuestionPD(@PathVariable String paerp_id,@PathVariable String doubleCheck){
        List<PaperQuestion> paperQuestions=paperQuestionService.listJudgeQuestionPD(paerp_id,doubleCheck);
        return R.ok().data("paperQuestionsPD",paperQuestions);
    }
    @DeleteMapping("/remove/{paperId}/{isZOrS}")
    public R  remove(@PathVariable String paperId,@PathVariable String isZOrS){
        if (!(StringUtils.isEmpty(paperId)&&StringUtils.isEmpty(isZOrS))){
            QueryWrapper<PaperQuestion> questionQueryWrapper = new QueryWrapper<>();
            questionQueryWrapper.eq("paerp_id",paperId);
            questionQueryWrapper.eq("is_z_or_s",isZOrS);
            boolean flag = paperQuestionService.remove(questionQueryWrapper);
            if(flag) {
                return R.ok();
            } else {
                return R.error();
            }
        }else {
            return R.error();
        }
    }
    @PostMapping("removeBath")
    public R  removeBath(String[] checkedChoiceQuestions){
//        System.out.println("*********************************************************************"+checkedChoiceQuestions);
        for (int i=0;i<checkedChoiceQuestions.length;i++) {
            boolean flag = paperQuestionService.removeById(checkedChoiceQuestions[i]);
            if (!flag) {
                return R.error();
            }
        }
        return R.ok();
    }
    @DeleteMapping("{id}")
    public R  deleteById(@PathVariable String id){
//        System.out.println("*********************************************************************"+checkedChoiceQuestions);
            boolean flag = paperQuestionService.removeById(id);
            if (!flag) {
                return R.error();
        }
        return R.ok();
    }
    @PostMapping("update")
    public R  update(@RequestBody PaperQuestion paperQuestion){
            return R.ok();
    }
    @GetMapping("SuJi/{size}/{num}")
    public R  SuJi(@PathVariable String size ,@PathVariable String num){

       String suiji= SuJiRandom.random(Integer.valueOf(size).intValue(),Integer.valueOf(num).intValue());

        return  R.ok();
    }

    @PostMapping("/addSuijiPaperQuestion")
    public  R addSuijiPaperQuestion(@RequestBody SuJiNum suij){
        QueryWrapper<PaperQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("paerp_id",suij.getPaperId());
        boolean  remove= paperQuestionService.remove(wrapper);
        if (remove){
            boolean flag = paperQuestionService.addSuijiPaperQuestion(suij);
            if (flag){
                return  R.ok().message("添加成功");
            }else {
                return  R.error().message("添加失败");
            }
        }else {
            return  R.error();
        }


    }

}

