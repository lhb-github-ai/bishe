package com.lee.exam.service.impl;

import com.lee.exam.entity.Type;
import com.lee.exam.mapper.TypeMapper;
import com.lee.exam.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-04-25
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

}
