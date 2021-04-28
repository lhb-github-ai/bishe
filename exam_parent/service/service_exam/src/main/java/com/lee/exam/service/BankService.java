package com.lee.exam.service;

import com.lee.exam.entity.Bank;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
public interface BankService extends IService<Bank> {

    Integer bankIsNull(String name);
}
