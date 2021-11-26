package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealMapper {
    void addSetmeal(Setmeal setmeal);

    void addSetmealAndTravelGroup(@Param("setmealId") Integer id,@Param("travelGroupIds") Integer[] travelGroupIds);

    Page<Setmeal> findPage(@Param("queryString") String queryString);

    void deleteTravelGroupAndItem(@Param("id") Integer id);

    void deleteTravelGroup(@Param("id") Integer id);

    String getByImg(@Param("id") Integer id);

    List<Integer> findTravelItemIdsByTravelGroupId(@Param("id") Integer id);

    void updateSetmeal(Setmeal setmeal);

    void add2s(@Param("setmealId") Integer id,@Param("travelGroupIds") Integer[] travelGroupIds);


    List<Setmeal> findAll();

    Setmeal findSetmealDetailById(@Param("id") Integer id);

    /**
     * 获取预约的套餐游的统计信息
     * @return
     */
    List<Map> getSetmealReport();
}
