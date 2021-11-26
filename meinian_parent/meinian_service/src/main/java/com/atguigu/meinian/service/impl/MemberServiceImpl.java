package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.mapper.MemberMapper;
import com.atguigu.meinian.pojo.Member;
import com.atguigu.meinian.service.MemberService;
import com.atguigu.meinian.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void addMember(Member member) {
        Member memberByTelephone = memberMapper.findMemberByTelephone(member.getPhoneNumber());
        if(memberByTelephone == null){
            memberMapper.addMember(member);
        }
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> monthList) {
        //创建list集合，存储每个月的会员数量
        List<Integer> memberCountList = new ArrayList<>();
        //对每个月的月份的集合进行循环
        for (String month : monthList) {
            //获取指定月份的最后一天的日期
            String regTime = DateUtils.getLastDayOfMonth(month);
            //根据每个月的最后一天的日期查询会员数量
            Integer memberCount = memberMapper.findCountByRegTime(regTime);
            memberCountList.add(memberCount);
        }
        return memberCountList;
    }
}
