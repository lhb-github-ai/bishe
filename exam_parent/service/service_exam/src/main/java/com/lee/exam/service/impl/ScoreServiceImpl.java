package com.lee.exam.service.impl;

import com.lee.exam.entity.ChoiceQuestion;
import com.lee.exam.entity.Score;
import com.lee.exam.entity.Vo.VoScore;
import com.lee.exam.mapper.ScoreMapper;
import com.lee.exam.service.ScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-04-14
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {

    @Override
    public Map<String, Object> getExamList(VoScore voScore) {
        List<Score> score = baseMapper.getExamList(voScore);
        Integer totals = baseMapper.totals(voScore);
        HashMap map = new HashMap();
        map.put("Score",score);
        map.put("totals",totals);
        return map;
    }
}
