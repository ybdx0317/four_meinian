package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.mapper.MemberMapper;
import com.atguigu.meinian.mapper.OrderMapper;
import com.atguigu.meinian.service.ReportService;
import com.atguigu.meinian.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date:2021/10/11
 * Author:ybc
 * Description:
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 获得运营统计数据
     * Map数据格式：
     *      reportDate（当前时间）--String
     *      todayNewMember（今日新增会员数） -> number
     *      totalMember（总会员数） -> number
     *      thisWeekNewMember（本周新增会员数） -> number
     *      thisMonthNewMember（本月新增会员数） -> number
     *      todayOrderNumber（今日预约数） -> number
     *      todayVisitsNumber（今日出游数） -> number
     *      thisWeekOrderNumber（本周预约数） -> number
     *      thisWeekVisitsNumber（本周出游数） -> number
     *      thisMonthOrderNumber（本月预约数） -> number
     *      thisMonthVisitsNumber（本月出游数） -> number
     *      hotSetmeal（热门套餐（取前4）） -> List<Map>
     */
    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        //reportDate（当前时间）--String
        String reportDate = DateUtils.parseDate2String(DateUtils.getToday());
        //获取本周周一的日期
        String weekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获取本周周日的日期
        String weekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
        //获取本月第一天的日期
        String monthFirst = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //获取本月最后一天的日期
        String monthLast = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());
        //todayNewMember（今日新增会员数） -> number
        Integer todayNewMember = memberMapper.getTodayNewMember(reportDate);
        //totalMember（总会员数） -> number
        Integer totalMember = memberMapper.getTotalMember();
        //thisWeekNewMember（本周新增会员数） -> number
        Integer thisWeekNewMember = memberMapper.getThisWeekAndMonthNewMember(weekMonday);
        //thisMonthNewMember（本月新增会员数） -> number
        Integer thisMonthNewMember = memberMapper.getThisWeekAndMonthNewMember(monthFirst);
        //todayOrderNumber（今日预约数） -> number
        Integer todayOrderNumber = orderMapper.getTodayOrderNumber(reportDate);
        //todayVisitsNumber（今日出游数） -> number
        Integer todayVisitsNumber = orderMapper.getTodayVisitsNumber(reportDate);
        //thisWeekOrderNumber（本周预约数） -> number
        Integer thisWeekOrderNumber = orderMapper.getThisWeekAndMonthOrderNumber(weekMonday, weekSunday);
        //thisWeekVisitsNumber（本周出游数） -> number
        Integer thisWeekVisitsNumber = orderMapper.getThisWeekAndMonthVisitsNumber(weekMonday);
        //thisMonthOrderNumber（本月预约数） -> number
        Integer thisMonthOrderNumber = orderMapper.getThisWeekAndMonthOrderNumber(monthFirst, monthLast);
        //thisMonthVisitsNumber（本月出游数） -> number
        Integer thisMonthVisitsNumber = orderMapper.getThisWeekAndMonthVisitsNumber(monthFirst);
        //hotSetmeal（热门套餐（取前4）） -> List<Map>
        List<Map> hotSetmeal = orderMapper.getHotSetmeal();
        //创建map集合存储运营统计信息
        Map<String, Object> map = new HashMap<>();
        map.put("reportDate", reportDate);
        map.put("todayNewMember", todayNewMember);
        map.put("totalMember", totalMember);
        map.put("thisWeekNewMember", thisWeekNewMember);
        map.put("thisMonthNewMember", thisMonthNewMember);
        map.put("todayOrderNumber", todayOrderNumber);
        map.put("todayVisitsNumber", todayVisitsNumber);
        map.put("thisWeekOrderNumber", thisWeekOrderNumber);
        map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        map.put("thisMonthOrderNumber", thisMonthOrderNumber);
        map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        map.put("hotSetmeal", hotSetmeal);
        return map;
    }
}
