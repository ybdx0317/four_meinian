package com.atguigu.meinian.service;

import com.atguigu.meinian.pojo.Member;

import java.util.List;

public interface MemberService {
    /**
     * 添加会员信息
     * @param member
     */
    void addMember(Member member);

    /**
     * 获取过去一年的每个月的会员数量
     * @param monthList
     * @return
     */
    List<Integer> findMemberCountByMonth(List<String> monthList);
}
