package com.shentao.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shentao.reggie.common.R;
import com.shentao.reggie.entity.Category;
import com.shentao.reggie.entity.Employee;
import com.shentao.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
分类管理
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //新增
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);

        categoryService.save(category);
        return R.success("新增成功");
    }

    //分页
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        log.info("page = {},pageSize = {},name={}",page,pageSize);

        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        //queryWrapper.like(StringUtils.isNotEmpty(name),Category::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Category::getSort);
        //执行查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    //根据id删除
    @DeleteMapping
    public R<String> delete(Long id){
        log.info("删除分类，id为：{}",id);

        categoryService.removeById(id);
        return R.success("分类信息删除成功");
    }

    //根据id修改分类信息
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类信息：{}",category);

        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

}

