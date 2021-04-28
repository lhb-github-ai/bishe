package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.exam.R;
import com.lee.exam.entity.Comment;
import com.lee.exam.entity.Notice;
import com.lee.exam.entity.Student;
import com.lee.exam.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2021-04-26
 */
@CrossOrigin
@RestController
@RequestMapping("/exam/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "添加")
    @PostMapping("addStudent")
    public R addBank(@RequestBody Comment comment){
        if (comment.getParentCommentId().equals("0")){
            QueryWrapper<Comment> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_comment_id",0);
            int count = commentService.count(wrapper);
            comment.setLeavl(count+1);
        }
        boolean save = commentService.save(comment);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @GetMapping("listAll/{blogId}")
    public R listAll(@PathVariable String blogId){

        List<Comment> list = commentService.listCommentByBlogId(blogId);
        return R.ok().data("comments",list);
    }

}

