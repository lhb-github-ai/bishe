package com.lee.exam.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.exam.R;
import com.lee.exam.entity.Bank;
import com.lee.exam.entity.Paperdetail;
import com.lee.exam.entity.Score;
import com.lee.exam.entity.Vo.PaperDetail;
import com.lee.exam.service.PaperService;
import com.lee.exam.service.PaperdetailService;
import com.lee.exam.service.ScoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2021-04-15
 */
@CrossOrigin
@RestController
@RequestMapping("/exam/paperdetail")
public class PaperDetailController {

    @Autowired
    private PaperdetailService paperdetailService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private ScoreService scoreService;

    @PostMapping("add/{studentId}")
    public R addPaperDetail(@PathVariable String studentId, @RequestBody List<Paperdetail> paperdetail){
        System.out.println("学生Id"+studentId);
        for (Paperdetail list:paperdetail){

            QueryWrapper<Score> wrapper = new QueryWrapper<>();
            wrapper.eq("paper_id",list.getPaerpId());
            wrapper.eq("student_id",studentId);
            List<Score> scores = scoreService.list(wrapper);

            list.setFrequency(scores.size());
            list.setStudentId(studentId);
            list.setPaperId(list.getPaerpId());
             paperdetailService.save(list);
        }
        return  R.ok();
    }

    @GetMapping("listPaperDetail/{id}/{frequency}")
    public R listByPage(@PathVariable String  id, @PathVariable String frequency){
        System.out.println(frequency);
       Integer frequency1 =Integer.valueOf(frequency);

      List<PaperDetail>  paperDetailDX = paperdetailService.listPaperDetail(id,frequency1,0);

        List<PaperDetail>   paperDetailPD = paperdetailService.listPaperDetailPD(id,frequency1,1);
        return R.ok().data("paperDetailDX",paperDetailDX).data("paperDetailPD",paperDetailPD);
    }
}

