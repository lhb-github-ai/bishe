package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.exam.R;
import com.lee.exam.entity.Bank;
import com.lee.exam.entity.Vo.bankQuery;
import com.lee.exam.service.BankService;
import com.lee.servicebase.exceptionhandler.ExamException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2020-09-12
 */
@CrossOrigin
@RestController
@RequestMapping("/exam/bank")
public class BankController {

    @Autowired
    private BankService bankServicel;
    @ApiOperation(value = "所有题库列表")
    @GetMapping("list")
    public R listAllBank(){
        List<Bank> list = bankServicel.list(null);
        return  R.ok().data("list",list);
    }
    @ApiOperation(value = "分页查询题库列表")
    @GetMapping("listByPage/{current}/{limit}")
    public R listByPage(@PathVariable long current, @PathVariable long limit){
     Page<Bank> pageBank = new Page<>(current,limit);
        IPage<Bank> bankIPage = bankServicel.page(pageBank, null);
        long total = bankIPage.getTotal();
        List<Bank> records = bankIPage.getRecords();
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }
    @ApiOperation(value = "分页带条件查询题库列表")
    @PostMapping("listByPage/{current}/{limit}")
    public R listByPageAndName(@PathVariable long current, @PathVariable long limit , @RequestBody(required = false) bankQuery bankQuery){
        Page<Bank> pageBank = new Page<>(current,limit);

        QueryWrapper<Bank> wrapper = new QueryWrapper<>();

        String name=bankQuery.getName();
        if(!StringUtils.isEmpty(name)) {

            wrapper.like("name",name);
        }
//        System.out.println(name);
        IPage<Bank> bankIPage = bankServicel.page(pageBank, wrapper);
        long total = bankIPage.getTotal();
        List<Bank> records = bankIPage.getRecords();
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }
    @ApiOperation(value = "添加")
    @PostMapping("addBank")
    public R  addBank(@RequestBody Bank bank){

        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("name",bank.getName());
        Bank bank1 = bankServicel.getOne(wrapper);
        if (bank1==null){
            boolean save = bankServicel.save(bank);
            if(save) {
                return R.ok();
            } else {
                return R.error();
            }
        }else {
            return R.ok().data("repeat","repeat");

        }
    }
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")
    public R  removeTeacher(@PathVariable String id){
        boolean flag = bankServicel.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "根据name查找")
    @GetMapping("listOneBank")
    public R listOneBank(@RequestParam String name){
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        Bank bank = bankServicel.getOne(wrapper);
        return R.ok().data("bank",bank);
    }
    @ApiOperation(value = "根据id查找")
    @GetMapping("listById/{id}")
    public R listById(@PathVariable String id){
        Bank bank = bankServicel.getById(id);
        return R.ok().data("bank",bank);
    }
    @ApiOperation(value = "修改")
    @PostMapping("updateBank")
    public  R updateBank(@RequestBody Bank bank){
        QueryWrapper<Bank> wrapper = new QueryWrapper<>();
        wrapper.eq("name",bank.getName());
        Bank bank1 = bankServicel.getOne(wrapper);
        if (bank1==null){
            boolean update = bankServicel.updateById(bank);
            if(update) {
                return R.ok();
            } else {
                return R.error();
            }
        }else if (bank.getId().equals(bank1.getId())){
            boolean update = bankServicel.updateById(bank);
            if(update) {
                return R.ok();
            } else {
                return R.error();
            }
        }
        else {
            return R.ok().data("repeat","repeat");
        }
    }
}

