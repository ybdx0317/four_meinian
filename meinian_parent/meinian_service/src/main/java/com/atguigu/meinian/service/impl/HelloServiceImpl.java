package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.service.HelloService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Date:2021/9/25
 * Author:ybc
 * Description:
 */
@Service(interfaceClass = HelloService.class)
@Transactional
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello,"+name;
    }
}
