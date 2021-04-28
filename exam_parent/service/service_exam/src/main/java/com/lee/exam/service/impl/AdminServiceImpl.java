package com.lee.exam.service.impl;

import com.lee.exam.entity.Admin;
import com.lee.exam.mapper.AdminMapper;
import com.lee.exam.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员信息表 服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-03-14
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
