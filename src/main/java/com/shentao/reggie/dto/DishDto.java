package com.shentao.reggie.dto;

import com.shentao.reggie.entity.Dish;
import com.shentao.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
//DTO :数据传输对象，用于展示层贺服务层之间的数据传输
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
