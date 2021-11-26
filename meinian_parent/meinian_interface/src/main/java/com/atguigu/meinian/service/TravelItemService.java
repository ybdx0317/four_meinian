package com.atguigu.meinian.service;

import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.pojo.TravelItem;

import java.util.List;

public interface TravelItemService {

    /**
     * 添加自由行信息
     * @param travelItem
     */
    void add(TravelItem travelItem);


    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    TravelItem findById(Integer id);

    void update(TravelItem travelItem);

    List<TravelItem> findAll();
}
