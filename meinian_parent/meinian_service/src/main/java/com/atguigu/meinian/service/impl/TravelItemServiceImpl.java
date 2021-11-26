package com.atguigu.meinian.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.constant.MessageConstant;
import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.mapper.TravelItemMapper;
import com.atguigu.meinian.pojo.TravelItem;
import com.atguigu.meinian.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService{

    @Autowired
    private TravelItemMapper travelItemMapper;

    @Override
    public void add(TravelItem travelItem) {
        travelItemMapper.add(travelItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<TravelItem> page = travelItemMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }


    @Override
    public void delete(Integer id) {
        travelItemMapper.delete(id);
    }


    @Override
    public TravelItem findById(Integer id) {
        return travelItemMapper.findById(id);
    }


    @Override
    public void update(TravelItem travelItem) {
        travelItemMapper.update(travelItem);
    }

    @Override
    public List<TravelItem> findAll() {
        return travelItemMapper.findAll();
    }
}
