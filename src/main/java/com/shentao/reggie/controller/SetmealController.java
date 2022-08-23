package com.shentao.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shentao.reggie.common.R;
import com.shentao.reggie.dto.SetmealDto;
import com.shentao.reggie.entity.Category;
import com.shentao.reggie.entity.Employee;
import com.shentao.reggie.entity.Setmeal;
import com.shentao.reggie.service.CategoryService;
import com.shentao.reggie.service.SetmealDishService;
import com.shentao.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/*
套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealService setmealService;

    /*
    新增套餐
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody SetmealDto setmealDto){
        log.info("新增套餐，套餐信息：{}",setmealDto.toString());
        setmealService.saveWithDish(setmealDto);
        //dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /*
分页查询
基于MybatisPlus的插件
 */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page = {},pageSize = {},name={}",page,pageSize, name);

        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        //构造条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //执行查询
        setmealService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) ->{
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item,setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null){
                //分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        return R.success(dtoPage);
    }

    /*
    删除套餐
     */
    @DeleteMapping
    public R<String> delete(@PathVariable List<Long> ids){

        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }
}
