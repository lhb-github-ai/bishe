package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.exam.R;
import com.lee.exam.entity.Bank;
import com.lee.exam.entity.Paper;
import com.lee.exam.entity.Score;
import com.lee.exam.entity.Teacher;
import com.lee.exam.entity.Vo.Paper1;
import com.lee.exam.entity.Vo.PaperQuery;
import com.lee.exam.entity.Vo.bankQuery;
import com.lee.exam.service.BankService;
import com.lee.exam.service.PaperService;
import com.lee.exam.service.ScoreService;
import com.lee.exam.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
@RequestMapping("/exam/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private TeacherService  teacherService;

    @Autowired
    private ScoreService scoreService;

    @ApiOperation(value = "分页带条件查询")
    @PostMapping("listByPage/{current}/{limit}/{teacherId}")
    public R listByPageAndName(@PathVariable long current, @PathVariable long limit ,@PathVariable String teacherId, @RequestBody(required = false) PaperQuery paperQuery){
        Page<Paper> pageBank = new Page<>(current,limit);
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        String PaperName=paperQuery.getPaperName();
        if(!StringUtils.isEmpty(PaperName)) {
            wrapper.like("paper_name",PaperName);
        }
        if (!StringUtils.isEmpty(teacherId)&&!teacherId.equals("1")){
            wrapper.like("author_id",teacherId);
        }
       // System.out.println(paperQuery);
        IPage<Paper> bankIPage = paperService.page(pageBank, wrapper);
        long total = bankIPage.getTotal();
        List<Paper> records = bankIPage.getRecords();
        for (Paper paper:records) {
            if (!paper.getAuthorId().equals("0000000000")){
                paper.setTeacherName(teacherService.getById(paper.getAuthorId()).getTeacherName());
            }
            else {
                paper.setTeacherName("管理员");
            }
        }
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }

    @GetMapping("list/{current}/{limit}/{id}/{type}")
    public R listAllPaper(@PathVariable long current, @PathVariable long limit, @PathVariable String id, @PathVariable String type) throws ParseException {
        Page<Paper> pageBank = new Page<>(current,limit);
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        wrapper.eq("type",type);
        IPage<Paper> bankIPage = paperService.page(pageBank, wrapper);
        long total = bankIPage.getTotal();
        List<Paper> records = bankIPage.getRecords();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Paper paper:records) {
            String timeFrame = paper.getTimeFrame();
            String[] time =  timeFrame.split("至");
            //正式

            //本学生的考试次数
            QueryWrapper<Score> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("paper_id",paper.getId());
            wrapper2.eq("student_id",id);
            List<Score> lists = scoreService.list(wrapper2);
            paper.setNumber(lists.size());

           if (paper.getType().equals("true")){
            //判断试卷考试状态
            QueryWrapper<Score> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("paper_id",paper.getId());
            wrapper1.eq("frequency",1);
            wrapper1.eq("student_id",id);
            List<Score> scores = scoreService.list(wrapper1);
             if (scores.size()>0){
                 if (scores.get(0).getPaperStatus()==1){
                     paper.setIsAvailable(true);
                     if (new Date().compareTo(simpleDateFormat.parse(time[0]))<0&&new Date().compareTo(simpleDateFormat.parse(time[1]))<0){

                         paper.setTip("考试时间还未开始(已考试)");
                     }
                     if (paper.getType().equals("true")&&new Date().compareTo(simpleDateFormat.parse(time[0]))>0&& new Date().compareTo(simpleDateFormat.parse(time[1]))<0){

                         paper.setTip("考试时间还未结束(已考试)");
                     }
                     if (new Date().compareTo(simpleDateFormat.parse(time[0]))>=0&&new Date().compareTo(simpleDateFormat.parse(time[1]))>=0){

                         paper.setTip("考试时间已结束(已考试)");
                     }
                 }else {
                     paper.setIsAvailable(false);
                     if (new Date().compareTo(simpleDateFormat.parse(time[0]))>=0&&new Date().compareTo(simpleDateFormat.parse(time[1]))>=0){
                         paper.setIsAvailable(true);
                         paper.setTip("考试时间已结束");
                     }

                     if (new Date().compareTo(simpleDateFormat.parse(time[0]))<0&&new Date().compareTo(simpleDateFormat.parse(time[1]))<0){
                         paper.setIsAvailable(true);
                         paper.setTip("考试时间还未开始");
                     }

                     if ( new Date().compareTo(simpleDateFormat.parse(time[0]))>0&& new Date().compareTo(simpleDateFormat.parse(time[1]))<0){

                         paper.setTip("考试时间还未结束");
                     }
                 }
             }else {
                 paper.setIsAvailable(false);
                 if (new Date().compareTo(simpleDateFormat.parse(time[0]))>=0&&new Date().compareTo(simpleDateFormat.parse(time[1]))>=0){

                     paper.setTip("考试时间已结束");
                 }

                 if (new Date().compareTo(simpleDateFormat.parse(time[0]))<0&&new Date().compareTo(simpleDateFormat.parse(time[1]))<0){

                     paper.setTip("考试时间还未开始");
                 }

                 if ( new Date().compareTo(simpleDateFormat.parse(time[0]))>0&& new Date().compareTo(simpleDateFormat.parse(time[1]))<0){

                     paper.setTip("考试时间还未结束");
                 }
             }
           }
           else {
           //测试试卷中
            paper.setIsAvailable(false);
            if (new Date().compareTo(simpleDateFormat.parse(time[0]))>=0&&new Date().compareTo(simpleDateFormat.parse(time[1]))>=0){
                paper.setIsAvailable(true);
                paper.setTip("测试时间已结束");
            }
            if (new Date().compareTo(simpleDateFormat.parse(time[0]))<0&&new Date().compareTo(simpleDateFormat.parse(time[1]))<0){
                paper.setIsAvailable(true);
                paper.setTip(" 测试时间还未开始");
            }
            if (new Date().compareTo(simpleDateFormat.parse(time[0]))>0&& new Date().compareTo(simpleDateFormat.parse(time[1]))<0){

                paper.setTip("测试时间还未结束");
             }
           }
        }
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }

    @PostMapping("addPaper")
    public R  addBank(@RequestBody Paper paper){
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_name",paper.getPaperName());
        Paper paper1 = paperService.getOne(wrapper);
        if (paper1==null){
            boolean save = paperService.save(paper);
            if(save) {
                return R.ok().data("paperId",paper.getId());
            } else {
                return R.error();
            }
        }else {
            return R.ok().data("repeat","repeat");

        }
    }
    @GetMapping("findByName/{paperName}")
    public R findByName(@PathVariable String paperName){
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
         if (!StringUtils.isEmpty(paperName)){

             wrapper.eq("paper_name",paperName);
         }
        List<Paper> PaperByNameList = paperService.list(wrapper);

        return  R.ok().data("list",PaperByNameList);
    }
    @GetMapping("findById/{paperId}")
    public R findById(@PathVariable String paperId){
        Paper paper = paperService.getById(paperId);
        if (!paper.getAuthorId().equals("0000000000")){
            paper.setTeacherName(teacherService.getById(paper.getAuthorId()).getTeacherName());
        }else {
            paper.setTeacherName("管理员");
        }
        return  R.ok().data("paper",paper);
    }
    @PostMapping("update")
    public  R update(@RequestBody Paper paper){
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(paper.getPaperName())) {
            wrapper.eq("level",paper.getPaperName());
        }
        wrapper.eq("paper_name",paper.getPaperName());
        Paper paper1 = paperService.getOne(wrapper);
        if (paper1==null){
            boolean update = paperService.updateById(paper);
            if(update) {
                return R.ok();
            } else {
                return R.error();
            }
        }else if (paper.getId().equals(paper1.getId())){
            boolean update = paperService.updateById(paper);
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
    @PostMapping("savePaper")
    public  R savePaper(@RequestBody Paper1 paper1) {
        System.out.println(paper1+"******************************");
        Paper paper = new Paper();
        paper.setId(paper1.getPaperId());
        paper.setTotalScore(paper1.getTotalScore());
        paper.setTime(paper1.getTime());
        paper.setTimeFrame(paper1.getTimeFrame());
        paper.setStatus(paper1.getStatus());
        paper.setType(paper1.getType());
        boolean update = paperService.updateById(paper);
        if(update) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")
    public R  removeTeacher(@PathVariable String id){
        boolean flag = paperService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

