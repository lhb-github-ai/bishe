package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.exam.R;
import com.lee.exam.entity.Type;
import com.lee.exam.service.AdminService;
import com.lee.exam.service.TypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2021-04-25
 */
@CrossOrigin
@RestController
@RequestMapping("/exam/type")
public class TypeController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "添加")
    @PostMapping("addNotice")
    public R addNotice(@RequestBody Type type){

        boolean save = typeService.save(type);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")
    public R  remove(@PathVariable String id){
        boolean flag = typeService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "修改")
    @PostMapping("updateNotice")
    public  R updateNotice(@RequestBody Type type){
        boolean update = typeService.updateById(type);
        if(update) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "根据id查找")
    @GetMapping("listById/{id}")
    public R listById(@PathVariable String id){
        Type byId = typeService.getById(id);
        return R.ok().data("type",byId);
    }
    @PostMapping("listByPage/{current}/{limit}")
    public R listByPage(@PathVariable long current, @PathVariable long limit ){
        Page<Type> pageStudent = new Page<>(current,limit);

        QueryWrapper<Type> wrapper = new QueryWrapper<>();

        IPage<Type> studentIPage = typeService.page(pageStudent, wrapper);
        long total = studentIPage.getTotal();
        List<Type> records = studentIPage.getRecords();
        for (Type type: records) {
            type.setAdminName(adminService.getById(type.getAdminId()).getAdminName());
        }
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }
    @GetMapping("listAll")
    public R listAll(){
        List<Type> list = typeService.list(null);
        return R.ok().data("types",list);
    }

}

