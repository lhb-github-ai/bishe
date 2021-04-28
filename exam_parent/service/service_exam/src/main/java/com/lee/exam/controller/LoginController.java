package com.lee.exam.controller;/*
 *@Author lee
 * @date 2020/07/23
 */


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.exam.R;
import com.lee.exam.entity.Admin;
import com.lee.exam.entity.Teacher;
import com.lee.exam.service.AdminService;
import com.lee.exam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("service/user")

public class LoginController {

    @PostMapping("login")
    public R login(){

        return R.ok().data("token","admin");
    }

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
                return R.ok().data("message","登录成功").data("msg","success").data("id",list.get(0).getId()).data("role",list.get(0).getRole());
            }
            else {
                return R.ok().data("message","密码错误！").data("msg","fail");
            }
        } else if(list1.size()>0) {
            if (list1.get(0).getPwd().equals(password)){
                return R.ok().data("message","登录成功").data("msg","success").data("id",list1.get(0).getId()).data("role",list1.get(0).getRole());
            }else {
                return R.ok().data("message","密码错误！").data("msg","fail");
            }

        }
        else{
            //return R.ok().data("token","admin");
           return R.ok().data("message","账号不存在或输入有误！").data("msg","fail");
        }

    }



    @GetMapping("info/{id}/{role}")
    public R info(@PathVariable String id, @PathVariable String role){
       // System.out.println("**********"+id+"***********"+role);
        if (role.equals("1")){
            Teacher teacher = teacherService.getById(id);
            return R.ok().data("roles","老师").data("name",teacher.getTeacherName()).data("avatar",teacher.getAvatar());
        }if(role.equals("0")){
            Admin admin = adminService.getById(id);
            return R.ok().data("roles","管理员").data("name",admin.getAdminName()).data("avatar",admin.getAvatar());
        }
      else {
            return R.error();
        }
    }
    @PostMapping("logout")
    public R logout(){

        return R.ok();
    }
}
