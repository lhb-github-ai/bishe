package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.exam.R;
import com.lee.exam.entity.Student;
import com.lee.exam.entity.Teacher;
import com.lee.exam.entity.Vo.StudentQuery;
import com.lee.exam.entity.Vo.TeacherQuery;
import com.lee.exam.service.TeacherService;
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
 * @since 2021-03-14
 */
@CrossOrigin
@RestController
@RequestMapping("/exam/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    @GetMapping("list")
    public R listAllBank(){
        List<Teacher> list = teacherService.list(null);
        return  R.ok().data("list",list);
    }
    @ApiOperation(value = "分页查询题库列表")
    @GetMapping("listByPage/{current}/{limit}")
    public R listByPage(@PathVariable long current, @PathVariable long limit){
        Page<Teacher> page= new Page<>(current,limit);
        IPage<Teacher> IPage = teacherService.page(page, null);
        long total = IPage.getTotal();
        List<Teacher> records = IPage.getRecords();
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }
    @ApiOperation(value = "分页带条件查询题库列表")
    @PostMapping("listByPage/{current}/{limit}")
    public R listByPageAndName(@PathVariable long current, @PathVariable long limit , @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<Teacher> page = new Page<>(current,limit);

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();

        String teacherName=teacherQuery.getTeacherName();
        if(!StringUtils.isEmpty(teacherName)) {

            wrapper.like("teacher_name",teacherName);
        }
        String type=teacherQuery.getType();
        if(!StringUtils.isEmpty(type)) {

            wrapper.eq("type",type);
        }
        String TeacherId=teacherQuery.getTeacherId();
        if(!StringUtils.isEmpty(TeacherId)) {

            wrapper.eq("teacher_id",TeacherId);
        }
//        System.out.println(name);
        IPage<Teacher> IPage = teacherService.page(page, wrapper);
        long total = IPage.getTotal();
        List<Teacher> records = IPage.getRecords();
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }
    @ApiOperation(value = "添加")
    @PostMapping("addTeacher")
    public R  addBank(@RequestBody Teacher teacher){
        teacher.setRole("1");
        teacher.setPwd(teacher.getTeacherId());
        boolean save = teacherService.save(teacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")
    public R  removeTeacher(@PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "修改")
    @PostMapping("updateTeacher")
    public  R updateBank(@RequestBody Teacher teacher){
        boolean update = teacherService.updateById(teacher);
        if(update) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "根据id查找")
    @GetMapping("listById/{id}")
    public R listById(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }
}

