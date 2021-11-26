package com.atguigu.meinian.service;

import com.atguigu.meinian.entity.Result;

import java.util.Map;

public interface OrderService {

    Result saveOrder(Map map) throws Exception;


    Map findOrderById(Integer id);
}
