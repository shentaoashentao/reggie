package com.shentao.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ReggieApplication {
    public static void main(String[] args) {
        //这个就是springboot的启动命令，运行main，执行这一句， SpringApplication是一个定义好的类。
        // run的参数是当前类的class，和main的参数，
        // 他会找到上面的springbootapplication注解开始进行你所做的所有配置。
        SpringApplication.run(ReggieApplication.class,args);
        log.info("success");
    }
}
