package com.lee.exam.service;

import com.lee.exam.entity.Paper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
public interface PaperService extends IService<Paper> {

    Paper listByPaperId(String paperId);
}
