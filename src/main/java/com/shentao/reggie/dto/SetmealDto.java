package com.shentao.reggie.dto;

import com.shentao.reggie.entity.Setmeal;
import com.shentao.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
