package com.lee.exam.service;

import com.lee.exam.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.exam.entity.Notice;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-04-26
 */
public interface CommentService extends IService<Comment> {

    List<Comment> listCommentByBlogId(String blogId);
}
