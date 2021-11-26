package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    Integer findOrderCountByCondition(Order order);

    void saveOrder(Order order);

    Map findOrderById(@Param("id") Integer id);

    /**
     * 查询当天的预约数
     * @param reportDate
     * @return
     */
    Integer getTodayOrderNumber(@Param("today") String reportDate);

    /**
     * 查询当天出游的预约数
     * @param reportDate
     * @return
     */
    Integer getTodayVisitsNumber(@Param("today") String reportDate);

    /**
     * 查询本周或本月的预约数量
     * @param begin
     * @param end
     * @return
     */
    Integer getThisWeekAndMonthOrderNumber(@Param("begin") String begin, @Param("end") String end);

    /**
     * 查询本周或本月出游的预约数
     * @param orderDate
     * @return
     */
    Integer getThisWeekAndMonthVisitsNumber(@Param("orderDate") String orderDate);

    /**
     * 查询四条热门套餐游
     * @return
     */
    List<Map> getHotSetmeal();
}
