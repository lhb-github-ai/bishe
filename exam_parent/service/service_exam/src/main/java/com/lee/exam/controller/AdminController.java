package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.exam.R;
import com.lee.exam.entity.Admin;
import com.lee.exam.entity.Teacher;
import com.lee.exam.entity.Type;
import com.lee.exam.service.AdminService;
import com.lee.exam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 管理员信息表 前端控制器
 * </p>
 *
 * @author lee
 * @since 2021-03-14
 */
@CrossOrigin
@RestController
@RequestMapping("/exam/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @PostMapping("loginUser/{username}/{password}")
    public R login(@PathVariable String username, @PathVariable String password){
        // System.out.println("********************************"+username+"**********"+password);
        //管理员
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("admin_id",username);
        List<Admin> list = adminService.list(adminQueryWrapper);
        //  System.out.println("********************************"+list+"**********");
        //教师
        QueryWrapper<Teacher> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("teacher_id",username);
        List<Teacher> list1 = teacherService.list(QueryWrapper);
        //System.out.println("********************************"+teacher.getPwd()+"**********");
        if (list.size()>0){
            if (list.get(0).getPwd().equals(password)){
                return R.ok().data("token","admin");
            }
            else {
                return R.ok().data("message","密码错误！").data("msg","fail");
            }
        } else if(list1.size()>0) {
            if (list1.get(0).getPwd().equals(password)){
                return R.ok().data("token","admin");
            }else {
                return R.ok().data("message","密码错误！").data("msg","fail");
            }

        }
        else{
            return R.ok().data("token","admin");
            //return R.ok().data("message","账号不存在或输入有误！").data("msg","fail");
        }

    }
    @GetMapping("listById/{id}")
    public R listById(@PathVariable String id){
        Admin byId = adminService.getById(id);
        return R.ok().data("admin",byId);
    }
    @PostMapping("updateNotice")
    public  R update(@RequestBody Admin admin){
        boolean update = adminService.updateById(admin);
        if(update) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

