package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.TravelItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelItemMapper {

    /**
     * 添加自由行信息
     * @param travelItem
     */
    void add(TravelItem travelItem);

    Page<TravelItem> findPage(@Param("queryString") String queryString);

    void delete(Integer id);

    TravelItem findById(Integer id);

    void update(TravelItem travelItem);

    List<TravelItem> findAll();

    /**
     * 根据跟团游的id查询自由行信息
     * @param travelGroupId
     * @return
     */
    List<TravelItem> findTravelItemByTravelGroupId(@Param("travelGroupId") Integer travelGroupId);
}
