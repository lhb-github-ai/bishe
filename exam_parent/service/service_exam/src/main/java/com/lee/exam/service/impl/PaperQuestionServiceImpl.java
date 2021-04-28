package com.lee.exam.service.impl;

import com.lee.exam.SuJiRandom;
import com.lee.exam.entity.ChoiceQuestion;
import com.lee.exam.entity.JudgeQuestion;
import com.lee.exam.entity.Paper;
import com.lee.exam.entity.PaperQuestion;
import com.lee.exam.entity.Vo.SuJiNum;
import com.lee.exam.mapper.PaperQuestionMapper;
import com.lee.exam.service.ChoiceQuestionService;
import com.lee.exam.service.JudgeQuestionService;
import com.lee.exam.service.PaperQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.exam.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
@Service
public class PaperQuestionServiceImpl extends ServiceImpl<PaperQuestionMapper, PaperQuestion> implements PaperQuestionService {
    @Autowired
    private   ChoiceQuestionService choiceQuestionService ;
    @Autowired
    private  JudgeQuestionService judgeQuestionService;

    @Override
    public List<PaperQuestion> listQuestion(String paerp_id, String doubleCheck,String isZOrS) {
        return baseMapper.listQuestion(paerp_id,doubleCheck,isZOrS);
    }

    @Override
    public List<PaperQuestion> listJudgeQuestion(String paerp_id, String doubleCheck,String isZOrS) {
        return baseMapper.listJudgeQuestion(paerp_id,doubleCheck,isZOrS);
    }


    @Autowired
    private PaperService paperService;
    @Override
    public boolean addSuijiPaperQuestion(SuJiNum suji) {
        Paper paper = paperService.getById(suji.paperId);
          int sizeDXOne= choiceQuestionService.QueryByLevel("1",paper.getBankId());
          int sizeDXTwo= choiceQuestionService.QueryByLevel("2",paper.getBankId());
          int sizeDXThree= choiceQuestionService.QueryByLevel("3",paper.getBankId());
          int sizeDXFour= choiceQuestionService.QueryByLevel("4",paper.getBankId());
          int sizeDXFive= choiceQuestionService.QueryByLevel("5",paper.getBankId());
        int sizePDOne= judgeQuestionService.QueryByLevel("1",paper.getBankId());
        int sizePDTwo= judgeQuestionService.QueryByLevel("2",paper.getBankId());
        int sizePdThree= judgeQuestionService.QueryByLevel("3",paper.getBankId());
        int sizePdFour= judgeQuestionService.QueryByLevel("4",paper.getBankId());
        int sizePDFive= judgeQuestionService.QueryByLevel("5",paper.getBankId());

      //单选 1111
        if (suji.getDXone()>0&&sizeDXOne>0){
        String str1 = SuJiRandom.random(sizeDXOne,suji.getDXone());
        String[] strOne = str1.split(",");
        for (String one:strOne){
            PaperQuestion paperQuestion1 = new PaperQuestion();
            ChoiceQuestion choiceQuestion = choiceQuestionService.QueryByLevelAndNum(one, "1",paper.getBankId());
         //   System.out.println("11111111111111111111111111111111111111111111"+choiceQuestion);
            paperQuestion1.setIsZOrS(0);
            paperQuestion1.setDoubleCheck(0);
            paperQuestion1.setPaerpId(suji.getPaperId());
            paperQuestion1.setQuestionId(choiceQuestion.getId());
            baseMapper.insert(paperQuestion1);
         }
        }
        //单选 2222
        if (suji.getDXtwo()>0&&sizeDXTwo>0) {
            String str2 = SuJiRandom.random(sizeDXTwo, suji.getDXtwo());
            String[] strTwo = str2.split(",");
            for (String Two : strTwo) {
                PaperQuestion paperQuestion2 = new PaperQuestion();
                ChoiceQuestion choiceQuestion = choiceQuestionService.QueryByLevelAndNum(Two, "2",paper.getBankId());
             //   System.out.println("2222222222222222222222222222222222222222222222222222" + choiceQuestion);
                paperQuestion2.setIsZOrS(0);
                paperQuestion2.setDoubleCheck(0);
                paperQuestion2.setPaerpId(suji.getPaperId());
                paperQuestion2.setQuestionId(choiceQuestion.getId());
                baseMapper.insert(paperQuestion2);
            }
        }

        //单选 3333
        if (suji.getDXthree()>0&&sizeDXThree>0) {
            String str3 = SuJiRandom.random(sizeDXThree, suji.getDXthree());
            String[] strThree = str3.split(",");
            for (String Three : strThree) {
                PaperQuestion paperQuestion3 = new PaperQuestion();
                ChoiceQuestion choiceQuestion = choiceQuestionService.QueryByLevelAndNum(Three, "3",paper.getBankId());
             //   System.out.println("33333333333333333333333333333333333333333333" + choiceQuestion);
                paperQuestion3.setIsZOrS(0);
                paperQuestion3.setDoubleCheck(0);
                paperQuestion3.setPaerpId(suji.getPaperId());
                paperQuestion3.setQuestionId(choiceQuestion.getId());
                baseMapper.insert(paperQuestion3);
            }
        }

        //单选 444
        if (suji.getDXfour()>0&&sizeDXFour>0) {
            String str4 = SuJiRandom.random(sizeDXFour, suji.getDXfour());
            String[] strFour = str4.split(",");
            for (String Four : strFour) {
                PaperQuestion paperQuestion4 = new PaperQuestion();
                ChoiceQuestion choiceQuestion = choiceQuestionService.QueryByLevelAndNum(Four, "4",paper.getBankId());
            //    System.out.println("44444444444444444444444444444444444444" + choiceQuestion);
                paperQuestion4.setIsZOrS(0);
                paperQuestion4.setDoubleCheck(0);
                paperQuestion4.setPaerpId(suji.getPaperId());
                paperQuestion4.setQuestionId(choiceQuestion.getId());
                baseMapper.insert(paperQuestion4);
            }
        }
         //单选 5555
        if (suji.getDXfive()>0&&sizeDXFive>0) {
            String str5 = SuJiRandom.random(sizeDXFive, suji.getDXfive());
            String[] strFive = str5.split(",");
            for (String Five : strFive) {
                PaperQuestion paperQuestion5 = new PaperQuestion();
                ChoiceQuestion choiceQuestion = choiceQuestionService.QueryByLevelAndNum(Five, "5",paper.getBankId());
               // System.out.println("55555555555555555555555555555555555" + choiceQuestion);
                paperQuestion5.setIsZOrS(0);
                paperQuestion5.setDoubleCheck(0);
                paperQuestion5.setPaerpId(suji.getPaperId());
                paperQuestion5.setQuestionId(choiceQuestion.getId());
                baseMapper.insert(paperQuestion5);
            }
        }
        //判断 1111
        if (suji.getPDone()>0&&sizePDOne>0) {
            String str6 = SuJiRandom.random(sizePDOne, suji.getPDone());
            String[] strSix = str6.split(",");
            for (String Six : strSix) {
                PaperQuestion paperQuestion6 = new PaperQuestion();
                JudgeQuestion judgeQuestion = judgeQuestionService.QueryByLevelAndNum(Six, "1",paper.getBankId());
             //   System.out.println("6666666666666666666666666666666666666" + judgeQuestion);
                paperQuestion6.setIsZOrS(0);
                paperQuestion6.setDoubleCheck(1);
                paperQuestion6.setPaerpId(suji.getPaperId());
                paperQuestion6.setQuestionId(judgeQuestion.getId());
                baseMapper.insert(paperQuestion6);
            }
        }
        //判断 2222
        if (suji.getPDtwo()>0&&sizePDTwo>0) {
            String str7 = SuJiRandom.random(sizePDTwo, suji.getPDtwo());
            String[] strSeven = str7.split(",");
            for (String Seven : strSeven) {
                PaperQuestion paperQuestion7 = new PaperQuestion();
                JudgeQuestion judgeQuestion = judgeQuestionService.QueryByLevelAndNum(Seven, "2",paper.getBankId());
             //   System.out.println("7777777777777777777777777777777777777" + judgeQuestion);
                paperQuestion7.setIsZOrS(0);
                paperQuestion7.setDoubleCheck(1);
                paperQuestion7.setPaerpId(suji.getPaperId());
                paperQuestion7.setQuestionId(judgeQuestion.getId());
                baseMapper.insert(paperQuestion7);
            }
        }
        //判断 3333
        if (suji.getPDthree()>0&&sizePdThree>0) {
            String str8 = SuJiRandom.random(sizePdThree, suji.getPDthree());
            String[] strEight = str8.split(",");
            for (String Eight : strEight) {
                PaperQuestion paperQuestion8 = new PaperQuestion();
                JudgeQuestion judgeQuestion = judgeQuestionService.QueryByLevelAndNum(Eight, "3",paper.getBankId());
               // System.out.println("888888888888888888888888888888888888" + judgeQuestion);
                paperQuestion8.setIsZOrS(0);
                paperQuestion8.setDoubleCheck(1);
                paperQuestion8.setPaerpId(suji.getPaperId());
                paperQuestion8.setQuestionId(judgeQuestion.getId());
                baseMapper.insert(paperQuestion8);
            }
        }
        //判断 4444
        if (suji.getPDfour()>0&&sizePdFour>0) {
            String str9 = SuJiRandom.random(sizePdFour, suji.getPDfour());
            String[] strNine = str9.split(",");
            for (String Nine : strNine) {
                PaperQuestion paperQuestion9 = new PaperQuestion();
                JudgeQuestion judgeQuestion = judgeQuestionService.QueryByLevelAndNum(Nine, "4",paper.getBankId());
             //   System.out.println("99999999999999999999999" + judgeQuestion);
                paperQuestion9.setIsZOrS(0);
                paperQuestion9.setDoubleCheck(1);
                paperQuestion9.setPaerpId(suji.getPaperId());
                paperQuestion9.setQuestionId(judgeQuestion.getId());
                baseMapper.insert(paperQuestion9);
            }
        }
        //判断 55555
        if (suji.getPDfive()>0&&sizePDFive>0) {
            String str10 = SuJiRandom.random(sizePDFive, suji.getPDfive());
            String[] strTen = str10.split(",");
            for (String Ten : strTen) {
                PaperQuestion paperQuestion10 = new PaperQuestion();
                JudgeQuestion judgeQuestion = judgeQuestionService.QueryByLevelAndNum(Ten, "5",paper.getBankId());
             //   System.out.println("***************************" + judgeQuestion);
                paperQuestion10.setIsZOrS(0);
                paperQuestion10.setDoubleCheck(1);
                paperQuestion10.setPaerpId(suji.getPaperId());
                paperQuestion10.setQuestionId(judgeQuestion.getId());
                baseMapper.insert(paperQuestion10);
            }
        }
        return true;
    }

    @Override
    public List<PaperQuestion> listQuestionDX(String paerp_id, String doubleCheck) {

        return baseMapper.listQuestionDX(paerp_id,doubleCheck);
    }

    @Override
    public List<PaperQuestion> listJudgeQuestionPD(String paerp_id, String doubleCheck) {

        return baseMapper.listQuestionPD(paerp_id,doubleCheck);
    }


}
