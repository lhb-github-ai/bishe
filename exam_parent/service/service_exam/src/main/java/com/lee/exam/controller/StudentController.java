package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.exam.R;
import com.lee.exam.entity.Bank;
import com.lee.exam.entity.Student;
import com.lee.exam.entity.Teacher;
import com.lee.exam.entity.Vo.StudentQuery;
import com.lee.exam.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/exam/student")
public class StudentController {

    @Autowired
    private StudentService studentService;



    /**前台页面*/
    @GetMapping("login/{studentId}/{password}")
    public R login(@PathVariable String studentId, @PathVariable String password){
        System.out.println(studentId);
        System.out.println(password);
        QueryWrapper<Student> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("student_id",studentId);
        List<Student> list1 = studentService.list(QueryWrapper);

        //System.out.println("********************************"+teacher.getPwd()+"**********");
        if (list1.size()>0) {
            if (list1.get(0).getPwd().equals(password)) {
                return R.ok().data("token", "student").data("msg", "success");
            } else {
                return R.ok().data("message", "密码错误！").data("msg", "fail");
            }
        }
        else {
            return R.ok().data("message","账号不存在或输入有误！").data("msg","fail");
        }
    }

    @GetMapping("getInfo/{studentId}")
    public R getInfo(@PathVariable String studentId){
        QueryWrapper<Student> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("student_id",studentId);
        Student student = studentService.getOne(QueryWrapper);

        return R.ok().data("student",student);
    }

    /**后台页面*/
    @GetMapping("list")
    public R listAllBank(){
        List<Student> list = studentService.list(null);
        return  R.ok().data("list",list);
    }
    @ApiOperation(value = "分页查询题库列表")
    @GetMapping("listByPage/{current}/{limit}")
    public R listByPage(@PathVariable long current, @PathVariable long limit){
        Page<Student> pageStudent = new Page<>(current,limit);
        IPage<Student> studentIPage = studentService.page(pageStudent, null);
        long total = studentIPage.getTotal();
        List<Student> records = studentIPage.getRecords();
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }
    @ApiOperation(value = "分页带条件查询题库列表")
    @PostMapping("listByPage/{current}/{limit}")
    public R listByPageAndName(@PathVariable long current, @PathVariable long limit , @RequestBody(required = false) StudentQuery studentQuery){
        Page<Student> pageStudent = new Page<>(current,limit);

        QueryWrapper<Student> wrapper = new QueryWrapper<>();

        String studentName=studentQuery.getStudentName();
        if(!StringUtils.isEmpty(studentName)) {

            wrapper.like("student_name",studentName);
        }
        String studentId=studentQuery.getStudentId();
        if(!StringUtils.isEmpty(studentId)) {

            wrapper.eq("student_id",studentId);
        }
        String clazz=studentQuery.getClazz();
        if(!StringUtils.isEmpty(clazz)) {

            wrapper.like("clazz",clazz);
        }
        String institute=studentQuery.getInstitute();
        if(!StringUtils.isEmpty(institute)) {

            wrapper.like("institute",institute);
        }
//        System.out.println(name);
        IPage<Student> studentIPage = studentService.page(pageStudent, wrapper);
        long total = studentIPage.getTotal();
        List<Student> records = studentIPage.getRecords();
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }
    @ApiOperation(value = "添加")
    @PostMapping("addStudent")
    public R  addBank(@RequestBody Student student){
        student.setRole("2");
        student.setPwd(student.getStudentId());
            boolean save = studentService.save(student);
            if(save) {
                return R.ok();
            } else {
                return R.error();
            }
    }
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")
    public R  removeTeacher(@PathVariable String id){
        boolean flag = studentService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "修改")
    @PostMapping("updateStudent")
    public  R updateBank(@RequestBody Student student){
            boolean update = studentService.updateById(student);
            if(update) {
                return R.ok();
            } else {
                return R.error();
            }
    }
    @ApiOperation(value = "根据id查找")
    @GetMapping("listById/{id}")
    public R listById(@PathVariable String id){
        Student student = studentService.getById(id);
        return R.ok().data("student",student);
    }

    /* 批量添加运用excel*/

    @PostMapping("addBatch")
    public R  addBatch(MultipartFile file){
        String msg= studentService.addBatch(file,studentService);
        if (msg.equals("fail")){
            return R.error().data("msg",msg);
        }
        return R.ok();
    }
}

