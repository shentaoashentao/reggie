package com.shentao.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shentao.reggie.entity.DishFlavor;
import com.shentao.reggie.mapper.DishFlavorMapper;
import com.shentao.reggie.service.DishFlavorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
