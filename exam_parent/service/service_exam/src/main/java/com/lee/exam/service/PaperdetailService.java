package com.lee.exam.service;

import com.lee.exam.entity.Paperdetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.exam.entity.Vo.PaperDetail;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-04-15
 */
public interface PaperdetailService extends IService<Paperdetail> {

    List<PaperDetail> listPaperDetail(String id, Integer frequency, Integer doubleCheck);

    List<PaperDetail>  listPaperDetailPD(String id, Integer frequency, Integer doubleCheck);
}
