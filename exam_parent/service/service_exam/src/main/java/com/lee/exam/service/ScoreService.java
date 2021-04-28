package com.lee.exam.service;

import com.lee.exam.entity.Score;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.exam.entity.Vo.VoScore;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-04-14
 */
public interface ScoreService extends IService<Score> {

    Map<String, Object> getExamList(VoScore voScore);
}
