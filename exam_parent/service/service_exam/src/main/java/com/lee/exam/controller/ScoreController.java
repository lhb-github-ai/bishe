package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.exam.R;
import com.lee.exam.entity.Paper;
import com.lee.exam.entity.Score;
import com.lee.exam.entity.Student;
import com.lee.exam.entity.Vo.StudentQuery;
import com.lee.exam.entity.Vo.VoScore;
import com.lee.exam.service.PaperService;
import com.lee.exam.service.ScoreService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2021-04-14
 */
@CrossOrigin
@RestController
@RequestMapping("/exam/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private PaperService paperService;
    @PostMapping("addScore")
    public R addScore(@RequestBody Score score){
        Paper paper = new Paper();
//        paper.setPaperStatus(1);
        paper.setId(score.getPaperId());
        paper.setNumber(paperService.getById(score.getPaperId()).getNumber()+1);

        //获取多少次

        QueryWrapper<Score> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id",score.getPaperId());
        wrapper.eq("student_id",score.getStudentId());
        List<Score> scores = scoreService.list(wrapper);

        score.setFrequency(scores.size()+1);
        score.setPaperStatus(1);

        boolean save = scoreService.save(score);
        if (save){
            paperService.updateById(paper);
            return  R.ok();
        }
      else {
            return  R.error();
        }
    }

    @ApiOperation(value = "分页带条件查询")
    @GetMapping("listByPage/{current}/{limit}/{studentId}")
    public R listByPageAndName(@PathVariable long current, @PathVariable long limit , @PathVariable String studentId){
        Page<Score> pageScore = new Page<>(current,limit);
        QueryWrapper<Score> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(studentId)) {
            wrapper.eq("student_id",studentId);
        }
        wrapper.orderByAsc("paper_id");
        IPage<Score> ScoreIPage = scoreService.page(pageScore, wrapper);
       int total=0;
        List<Score> records = ScoreIPage.getRecords();
        List<Score> scores = new ArrayList<>();
        for (Score score:records) {
            Paper paper = paperService.getById(score.getPaperId());
            score.setPaperName(paper.getPaperName());
              if (paper.getType().equals("false")){
                  if (paper.getId().equals(score.getPaperId())){
                      scores.add(score);
                      total++;
                  }

              }
        }
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",scores);
        return R.ok().data(map);
    }
    @ApiOperation(value = "分页带条件查询")
    @PostMapping("listByPageAndId/{current}/{limit}")
    public R listByPageAndId(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) VoScore voScore  ){
        voScore.setCurrent(((current-1)*limit));
        voScore.setLimit(limit);
//        courseQuery.setPageNumber((int) ((current-1)*limit));
//        courseQuery.setPageSize((int) limit);
        Map<String ,Object> map= scoreService.getExamList(voScore);
        return R.ok().data(map);
    }
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")
    public R  removeTeacher(@PathVariable String id){
        boolean flag = scoreService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

