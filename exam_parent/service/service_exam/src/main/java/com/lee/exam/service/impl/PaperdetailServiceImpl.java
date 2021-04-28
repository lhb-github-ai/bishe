package com.lee.exam.service.impl;

import com.lee.exam.entity.Paperdetail;
import com.lee.exam.entity.Vo.PaperDetail;
import com.lee.exam.mapper.PaperdetailMapper;
import com.lee.exam.service.PaperdetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-04-15
 */
@Service
public class PaperdetailServiceImpl extends ServiceImpl<PaperdetailMapper, Paperdetail> implements PaperdetailService {

    @Override
    public  List<PaperDetail>  listPaperDetail(String id, Integer frequency, Integer doubleCheck) {
        return baseMapper.listPaperDetail(id,frequency,doubleCheck);
    }

    @Override
    public List<PaperDetail> listPaperDetailPD(String id, Integer frequency, Integer doubleCheck) {
        return baseMapper.listPaperDetailPD(id,frequency,doubleCheck);
    }
}
