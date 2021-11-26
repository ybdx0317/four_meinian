package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper {
    /**
     * 根据手机号查询会员信息
     * @param telephone
     * @return
     */
    Member findMemberByTelephone(@Param("telephone") String telephone);

    /**
     * 添加会员信息
     * @param member
     */
    void addMember(Member member);

    /**
     * 根据每个月的最后一天查询会员数量
     * @param regTime
     * @return
     */
    Integer findCountByRegTime(@Param("regTime") String regTime);

    /**
     * 当天新增会员数量
     * @param reportDate
     * @return
     */
    Integer getTodayNewMember(@Param("today") String reportDate);

    /**
     * 查询所有的会员数量
     * @return
     */
    Integer getTotalMember();

    /**
     * 查询本周或本月的新增会员数
     * @param regTime
     * @return
     */
    Integer getThisWeekAndMonthNewMember(@Param("regTime") String regTime);
}
