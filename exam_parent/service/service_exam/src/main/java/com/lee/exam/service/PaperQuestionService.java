package com.lee.exam.service;


import com.lee.exam.entity.PaperQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.exam.entity.Vo.SuJiNum;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
public interface PaperQuestionService extends IService<PaperQuestion> {

    List<PaperQuestion> listQuestion(String paerp_id, String doubleCheck,String isZOrS);

    List<PaperQuestion> listJudgeQuestion(String paerp_id, String doubleCheck,String isZOrS);

    boolean addSuijiPaperQuestion(SuJiNum suJi);

    List<PaperQuestion> listQuestionDX(String paerp_id, String doubleCheck);

    List<PaperQuestion> listJudgeQuestionPD(String paerp_id, String doubleCheck);
}
