package com.shentao.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shentao.reggie.common.CustomException;
import com.shentao.reggie.dto.SetmealDto;
import com.shentao.reggie.entity.Setmeal;
import com.shentao.reggie.entity.SetmealDish;
import com.shentao.reggie.mapper.SetmealMapper;
import com.shentao.reggie.service.SetmealDishService;
import com.shentao.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;


    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {

        //保存套餐的基本信息 操作setmeal 执行insert语句
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品的关联信息 操作setmeal_dish 执行insert语句
        //批量保存
        setmealDishService.saveBatch(setmealDishes);

    }

    /*
    删除套餐
     */
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //select count(*) from   where id in()
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //判断状态是否是禁用
        queryWrapper.eq(Setmeal::getStatus,1);
        //批量删除
        queryWrapper.in(Setmeal::getId,ids);

        int count =  this.count(queryWrapper);
        if(count > 0){
            //如果不能删除 则抛出异常
            throw new CustomException("套餐正在售卖");
        }
        //如果可以删除 先删除套餐中的数据 setmeal
        this.removeByIds(ids);

        //删除关系表的数据 setmeal_dish
        //delete from setmeal_dish  where sermeal_id in ()
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.in(SetmealDish::getSetmealId,1);
        setmealDishService.remove(lambdaQueryWrapper) ;
    }

}
