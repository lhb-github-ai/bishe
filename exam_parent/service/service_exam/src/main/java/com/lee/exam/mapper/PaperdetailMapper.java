package com.lee.exam.mapper;

import com.lee.exam.entity.Paperdetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.exam.entity.Vo.PaperDetail;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lee
 * @since 2021-04-15
 */
public interface PaperdetailMapper extends BaseMapper<Paperdetail> {

    List<PaperDetail> listPaperDetail(String id, Integer frequency, Integer doubleCheck);

    List<PaperDetail>  listPaperDetailPD(String id, Integer frequency, Integer doubleCheck);
}
