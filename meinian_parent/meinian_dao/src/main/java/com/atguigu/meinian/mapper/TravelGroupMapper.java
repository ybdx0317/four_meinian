package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.TravelGroup;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelGroupMapper {


    void addTravelGroup(TravelGroup travelGroup);

    void addTravelGroupAndItem(@Param("travelGroupId") Integer id,@Param("travelItemIds") Integer[] travelItemIds);

    Page<TravelGroup> findPage(@Param("queryString") String queryString);

    void deleteTravelGroupAndItem(@Param("id") Integer id);

    void deleteTravelGroup(@Param("id") Integer id);

    TravelGroup findById(@Param("id") Integer id);

    List<Integer> findTravelItemIdsByTravelGroupId(@Param("id") Integer id);

    void updateTravelGroup(TravelGroup travelGroup);

    List<TravelGroup> findAll();

    /**
     * 根据套餐游的id查询跟团游信息
     * @param setmealId
     * @return
     */
    List<TravelGroup> findTravelGroupBySetmealId(@Param("setmealId") Integer setmealId);
}
