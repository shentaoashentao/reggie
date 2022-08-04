package com.shentao.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shentao.reggie.common.R;
import com.shentao.reggie.entity.Employee;
import com.shentao.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //员工登录
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request,@RequestBody Employee employee){


        //1.将页面提交的密码 进行md5加密
        String password = employee.getPassword();
       password =  DigestUtils.md5DigestAsHex(password.getBytes());

       //2.根据页面提交的用户名查询数据库
       LambdaQueryWrapper<Employee> queryWrapper =  new LambdaQueryWrapper<>();
       queryWrapper.eq(Employee::getUsername,employee.getUsername());
       Employee emp = employeeService.getOne(queryWrapper);

       //3.如果没有查询到 返回登录失败的结果
        if(emp == null){
            return R.error("登陆失败");
        }

        //4.密码比对 不一致返回登陆失败结果
        if(!emp.getPassword().equals(password)){
            return R.error("登陆失败");
        }

        //5.登陆进去了以后 查看员工状态 若已禁用，返回员工已禁用结果
        if(emp.getStatus()==0){
            return R.error("账号已禁用");
        }

        //6.登陆成功，将id存入session并返回登陆成功结果
        request.getSession().setAttribute("employee",employee.getId());
        return R.success(emp);
    }
        //退出
        @PostMapping("/logout")
        public R<String> logout(HttpServletRequest request){

            //清除session中保存的登录id
            request.getSession().removeAttribute("employee");
            return R.success("退出成功");
        }



}
