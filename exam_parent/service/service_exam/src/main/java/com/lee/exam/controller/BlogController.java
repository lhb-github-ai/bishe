package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.exam.R;
import com.lee.exam.entity.Blog;
import com.lee.exam.entity.Notice;
import com.lee.exam.entity.Student;
import com.lee.exam.entity.Vo.BlogQuery;
import com.lee.exam.entity.Vo.NoticeQuery;
import com.lee.exam.service.BlogService;
import com.lee.exam.service.StudentService;
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
 * @since 2021-04-25
 */
@CrossOrigin
@RestController
@RequestMapping("/exam/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private StudentService studentService;
    @PostMapping("addBlog")
    public R addNotice(@RequestBody Blog blog){
        boolean save = blogService.save(blog);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @PostMapping("listByPage/{current}/{limit}")
    public R listByPage(@PathVariable long current, @PathVariable long limit , @RequestBody(required = false) BlogQuery blogQuery){
        Page<Blog> pageStudent = new Page<>(current,limit);

        QueryWrapper<Blog> wrapper = new QueryWrapper<>();

        String typeId=blogQuery.getTypeId();
        if(!StringUtils.isEmpty(typeId)) {

            wrapper.eq("type_id",typeId);
        }
        wrapper.orderByDesc("gmt_create");
        IPage<Blog> studentIPage = blogService.page(pageStudent, wrapper);

        long total = studentIPage.getTotal();
        List<Blog> records = studentIPage.getRecords();
        for (Blog blog: records) {
            Student student = studentService.getById(blog.getBlogId());
            blog.setAvatar(student.getAvatar());
            blog.setStudentName(student.getStudentName());
        }
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }
    @ApiOperation(value = "根据id查找")
    @GetMapping("listById/{id}")
    public R listById(@PathVariable String id){

        Blog byId = blogService.getById(id);
        Student student = studentService.getById(byId.getBlogId());
        byId.setAvatar(student.getAvatar());
        byId.setStudentName(student.getStudentName());
        return R.ok().data("blog",byId);
    }
}

