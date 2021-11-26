package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.mapper.TravelGroupMapper;
import com.atguigu.meinian.pojo.TravelGroup;
import com.atguigu.meinian.service.TravelGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    private TravelGroupMapper travelGroupMapper;

    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) {
        //添加跟团游信息
        travelGroupMapper.addTravelGroup(travelGroup);
        //添加跟团游和自由行的中间表信息
        travelGroupMapper.addTravelGroupAndItem(travelGroup.getId(), travelItemIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<TravelGroup> page = travelGroupMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }


    @Override
    public void delete(Integer id) {
        //先删除跟团游和自由行的中间表信息
        travelGroupMapper.deleteTravelGroupAndItem(id);
        //再删除跟团游信息
        travelGroupMapper.deleteTravelGroup(id);
    }


    @Override
    public TravelGroup findById(Integer id) {
        return travelGroupMapper.findById(id);
    }


    @Override
    public List<Integer> findTravelItemIdsByTravelGroupId(Integer id) {
        return travelGroupMapper.findTravelItemIdsByTravelGroupId(id);
    }

    @Override
    public void update(TravelGroup travelGroup, Integer[] travelItemIds) {
        //修改跟团游信息
        travelGroupMapper.updateTravelGroup(travelGroup);
        //删除跟团游和自由行的中间表信息
        travelGroupMapper.deleteTravelGroupAndItem(travelGroup.getId());
        //添加跟团游和自由行的中间表信息
        travelGroupMapper.addTravelGroupAndItem(travelGroup.getId(), travelItemIds);
    }


    @Override
    public List<TravelGroup> findAll() {
        return travelGroupMapper.findAll();
    }
}
