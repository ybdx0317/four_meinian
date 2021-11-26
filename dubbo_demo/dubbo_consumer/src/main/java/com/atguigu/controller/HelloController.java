package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    //@Reference(loadbalance = "random")
    @Reference
    private HelloService helloService;

    @RequestMapping("/hello")
    @ResponseBody
    public String testHelloWorld(String name){
        return helloService.sayHello(name);
    }



}
