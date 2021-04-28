package com.lee.exam.service.impl;

import com.lee.exam.entity.Bank;
import com.lee.exam.mapper.BankMapper;
import com.lee.exam.service.BankService;
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
public class BankServiceImpl extends ServiceImpl<BankMapper, Bank> implements BankService {
    @Override
    public Integer bankIsNull(String name) {
        return baseMapper.bankIsNull(name);
    }
}
