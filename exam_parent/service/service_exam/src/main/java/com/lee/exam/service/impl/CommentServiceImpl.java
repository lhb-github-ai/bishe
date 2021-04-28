package com.lee.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.exam.entity.Comment;
import com.lee.exam.entity.Notice;
import com.lee.exam.entity.Student;
import com.lee.exam.mapper.CommentMapper;
import com.lee.exam.mapper.StudentMapper;
import com.lee.exam.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.exam.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-04-26
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private StudentService studentService;

    @Override
    public List<Comment> listCommentByBlogId(String blogId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",blogId);
        wrapper.orderByAsc("gmt_create");
        List<Comment> comments = baseMapper.selectList(wrapper);
        for (Comment comment:comments) {
            Student student = studentService.getById(comment.getStudentId());
            comment.setAvatar(student.getAvatar());
            comment.setStudentName(student.getStudentName());
        }
        return eachComment1(comments);
    }

    private List<Comment> eachComment1(List<Comment> comments) {

        for (Comment comment:comments) {
            for (Comment comment2:comments) {
                if (comment2.getParentCommentId().equals(comment.getId())){
                    comment2.setParentName(comment.getStudentName());
                }
            }
        }
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getParentCommentId().equals("0")){
                commentsView.add(comment);
            }
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren1(commentsView,comments);
        return commentsView;
    }
    private List<Comment> tempReplys2 = new ArrayList<>();
    private void combineChildren1(List<Comment> commentsView,List<Comment> comments) {
        for (Comment comment : commentsView) {
            for (Comment comment1 : comments) {
                if (!comment1.getParentCommentId().equals("0")&&comment.getLeavl().equals(comment1.getLeavl()) ) {
                    tempReplys2.add(comment1);
                }
            }
            comment.setReplyComments(tempReplys2);
            tempReplys2 = new ArrayList<>();
        }

    }
//    //存放迭代找出的所有子代的集合
//    private List<Comment> tempReplys1 = new ArrayList<>();
//
//    private void recursively1(Comment comment) {
//        tempReplys.add(comment);//顶节点添加到临时存放集合
//        if (comment.getReplyComments().size()>0) {
//            List<Comment> replys = comment.getReplyComments();
//            for (Comment reply1 : replys) {
//                tempReplys.add(reply1);
//                if (reply1.getReplyComments().size()>0) {
//                    recursively(reply1);
//                }
//            }
//        }
//    }
//
//
//
//
//
//
//
//    /**
//     * 循环每个顶级的评论节点
//     * @param comments
//     * @return
//     */
//    private List<Comment> eachComment(List<Comment> comments) {
//        List<Comment> commentsView = new ArrayList<>();
//        for (Comment comment : comments) {
//            Comment c = new Comment();
//            BeanUtils.copyProperties(comment,c);
//            commentsView.add(c);
//        }
//        //合并评论的各层子代到第一级子代集合中
//        combineChildren(commentsView);
//        return commentsView;
//    }
//    /**
//     *
//     * @param comments root根节点，blog不为空的对象集合
//     * @return
//     */
//    private void combineChildren(List<Comment> comments) {
//
//        for (Comment comment : comments) {
//            List<Comment> replys1 = comment.getReplyComments();
//            for(Comment reply1 : replys1) {
//                //循环迭代，找出子代，存放在tempReplys中
//                recursively(reply1);
//            }
//            //修改顶级节点的reply集合为迭代处理后的集合
//            comment.setReplyComments(tempReplys);
//            //清除临时存放区
//            tempReplys = new ArrayList<>();
//        }
//    }
//    //存放迭代找出的所有子代的集合
//    private List<Comment> tempReplys = new ArrayList<>();
//    /**
//     * 递归迭代，剥洋葱
//     * @param comment 被迭代的对象
//     * @return
//     */
//    private void recursively(Comment comment) {
//        tempReplys.add(comment);//顶节点添加到临时存放集合
//        if (comment.getReplyComments().size()>0) {
//            List<Comment> replys = comment.getReplyComments();
//            for (Comment reply : replys) {
//                tempReplys.add(reply);
//                if (reply.getReplyComments().size()>0) {
//                    recursively(reply);
//                }
//            }
//        }
//    }
}
