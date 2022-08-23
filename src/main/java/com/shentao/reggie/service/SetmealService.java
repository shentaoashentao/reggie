package com.shentao.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shentao.reggie.dto.SetmealDto;
import com.shentao.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {


    public void saveWithDish(SetmealDto setmealDto);

    //删除套餐的时候 把关联的菜品数据也删除
    public void removeWithDish(List<Long> ids);
}
