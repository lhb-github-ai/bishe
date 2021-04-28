package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.exam.R;
import com.lee.exam.entity.Notice;
import com.lee.exam.entity.Student;
import com.lee.exam.entity.Teacher;
import com.lee.exam.entity.Vo.NoticeQuery;
import com.lee.exam.entity.Vo.StudentQuery;
import com.lee.exam.service.AdminService;
import com.lee.exam.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2021-04-17
 */
@CrossOrigin
@RestController
@RequestMapping("/exam/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "添加")
    @PostMapping("addNotice")
    public R addNotice(@RequestBody Notice notice){

        boolean save = noticeService.save(notice);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")
    public R  remove(@PathVariable String id){
        boolean flag = noticeService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "修改")
    @PostMapping("updateNotice")
    public  R updateNotice(@RequestBody Notice notice){
        boolean update = noticeService.updateById(notice);
        if(update) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "根据id查找")
    @GetMapping("listById/{id}")
    public R listById(@PathVariable String id){
        Notice notice = noticeService.getById(id);
        return R.ok().data("notice",notice);
    }
    @PostMapping("listByPage/{current}/{limit}")
    public R listByPage(@PathVariable long current, @PathVariable long limit , @RequestBody(required = false) NoticeQuery noticeQuery){
        Page<Notice> pageStudent = new Page<>(current,limit);

        QueryWrapper<Notice> wrapper = new QueryWrapper<>();

        String NoticeTitle=noticeQuery.getNoticeTitle();
        if(!StringUtils.isEmpty(NoticeTitle)) {

            wrapper.like("notice_title",NoticeTitle);
        }
        IPage<Notice> studentIPage = noticeService.page(pageStudent, wrapper);
        long total = studentIPage.getTotal();
        List<Notice> records = studentIPage.getRecords();
        for (Notice notice:
        records) {
            notice.setAdminName(adminService.getById(notice.getAdminId()).getAdminName());
        }
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }
    @GetMapping("listAll")
    public R listAll(){
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
            wrapper.like("status",true);
        List<Notice> list = noticeService.list(wrapper);
        return R.ok().data("notices",list);
    }
}

