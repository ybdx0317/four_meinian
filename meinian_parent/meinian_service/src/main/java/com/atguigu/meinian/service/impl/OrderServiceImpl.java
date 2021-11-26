package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.constant.MessageConstant;
import com.atguigu.meinian.entity.Result;
import com.atguigu.meinian.mapper.MemberMapper;
import com.atguigu.meinian.mapper.OrderMapper;
import com.atguigu.meinian.mapper.OrderSettingMapper;
import com.atguigu.meinian.pojo.Member;
import com.atguigu.meinian.pojo.Order;
import com.atguigu.meinian.pojo.OrderSetting;
import com.atguigu.meinian.service.OrderService;
import com.atguigu.meinian.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Result saveOrder(Map map) throws Exception {
        //获取用户预约的日期
        String orderDateString = (String) map.get("orderDate");
        //将orderDateString转换为日期类型
        Date orderDate = DateUtils.parseString2Date(orderDateString);
        //根据预约日期获取预约设置信息
        OrderSetting orderSetting = orderSettingMapper.findOrderSettingByOrderDate(orderDate);
        //判断orderSetting是否为null
        if (orderSetting == null){
            //表示根据当前日期没有查询出预约设置信息，即当前日期不能预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }else {
            //表示根据当前日期查询出了预约设置信息，即当前日期可以预约
            //然后判断当前日期预约是否已满
            if(orderSetting.getNumber() == orderSetting.getReservations()){
                //表示预约已满
                return new Result(false, MessageConstant.ORDER_FULL);
            }
        }
        //表示当前日期可以预约，并且预约未满
        //获取手机号
        String telephone = (String) map.get("telephone");
        //根据手机号查询会员信息
        Member member = memberMapper.findMemberByTelephone(telephone);
        //判断meber是否为空
        if (member == null){
            //表示不是会员,自动将用户信息注册为会员
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setRegTime(new Date());
            //添加会员信息
            memberMapper.addMember(member);
        }else {
            //表示当前用户是会员，判断当前会员是否在同一天预约了同一个套餐
            Integer setmealId = Integer.valueOf((String) map.get("setmealId"));
            Order order = new Order(member.getId(),orderDate,null,null,setmealId);
            Integer count = orderMapper.findOrderCountByCondition(order);
            if(count > 0){
                //表示当前会员在同一天已经预约了此套餐
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }
        //表示可以预约
        //更新预约设置的已预约人数
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingMapper.updateReservationsByOrderDate(orderSetting);
        //保存预约信息
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderDate);
        order.setSetmealId(Integer.valueOf((String) map.get("setmealId")));
        order.setOrderType(Order.ORDERTYPE_WEIXIN);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        orderMapper.saveOrder(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map findOrderById(Integer id) {
        return orderMapper.findOrderById(id);
    }
}
