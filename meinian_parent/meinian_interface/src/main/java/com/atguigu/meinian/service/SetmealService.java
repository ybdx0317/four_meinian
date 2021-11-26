package com.atguigu.meinian.service;

import com.atguigu.meinian.entity.PageResult;
import com.atguigu.meinian.entity.QueryPageBean;
import com.atguigu.meinian.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealService {

    /**
     * 添加套餐信息
     * @param setmeal
     * @param travelGroupIds
     */
    void add(Setmeal setmeal, Integer[] travelGroupIds);

    /**
     * 查询套餐游的分页信息
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    String getByImg(Integer id);

    List<Integer> findTravelItemIdsByTravelGroupId(Integer id);

    void update(Setmeal setmeal, Integer[] travelGroupIds);

    /**
     * 查询所有的套餐游信息
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 根据id查询套餐游的详情
     * @param id
     * @return
     */
    Setmeal findSetmealDetailById(Integer id);

    /**
     * 获取预约的套餐游统计信息
     * @return
     */
    List<Map> getSetmealReport();
}
