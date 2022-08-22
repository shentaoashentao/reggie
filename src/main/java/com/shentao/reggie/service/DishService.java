package com.shentao.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shentao.reggie.dto.DishDto;
import com.shentao.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，操作dish和dish_flavor两张表
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    //更新菜品信息 和 口味信息
    public void updateWithFlavor(DishDto dishDto);
}
