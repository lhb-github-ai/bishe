package com.lee.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.exam.R;
import com.lee.exam.entity.Banner;
import com.lee.exam.entity.Notice;
import com.lee.exam.entity.Vo.NoticeQuery;
import com.lee.exam.service.AdminService;
import com.lee.exam.service.BannerService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2021-04-23
 */
@CrossOrigin
@RestController
@RequestMapping("/exam/banner")
public class BannerController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BannerService bannerService;
    @PostMapping("addNotice")
    public R addNotice(@RequestBody Banner banner){

        boolean save = bannerService.save(banner);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")
    public R  remove(@PathVariable String id){
        boolean flag = bannerService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "修改")
    @PostMapping("updateNotice")
    public  R updateNotice(@RequestBody Banner banner){
        boolean update = bannerService.updateById(banner);
        if(update) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    @ApiOperation(value = "根据id查找")
    @GetMapping("listById/{id}")
    public R listById(@PathVariable String id){
        Banner banner = bannerService.getById(id);
        return R.ok().data("banner",banner);
    }
    @PostMapping("listByPage/{current}/{limit}")
    public R listByPage(@PathVariable long current, @PathVariable long limit){
        Page<Banner> pageStudent = new Page<>(current,limit);
        IPage<Banner> studentIPage = bannerService.page(pageStudent, null);
        long total = studentIPage.getTotal();
        List<Banner> records = studentIPage.getRecords();
        for (Banner banner:
                records) {
            banner.setAdminName(adminService.getById(banner.getAdminId()).getAdminName());
        }
        HashMap map = new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }
    @GetMapping("listAll")
    public R listAll(){
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.like("status",true);
        List<Banner> list = bannerService.list(wrapper);
        return R.ok().data("banners",list);
    }

}

