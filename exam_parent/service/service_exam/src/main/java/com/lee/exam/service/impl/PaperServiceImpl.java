package com.lee.exam.service.impl;

import com.lee.exam.entity.Paper;
import com.lee.exam.mapper.PaperMapper;
import com.lee.exam.service.PaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperService {

    @Override
    public Paper listByPaperId(String paperId) {

        return baseMapper.listByPaperId(paperId);
    }
}
