package com.lee.exam.mapper;

import com.lee.exam.entity.Bank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
public interface BankMapper extends BaseMapper<Bank> {

    Integer bankIsNull(String name);
}
