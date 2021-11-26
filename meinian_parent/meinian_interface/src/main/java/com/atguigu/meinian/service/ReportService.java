package com.atguigu.meinian.service;

import java.util.Map;

/**
 * Date:2021/10/11
 * Author:ybc
 * Description:
 */
public interface ReportService {

    /**
     * 查询运营数据统计
     * @return
     */
    Map<String, Object> getBusinessReportData() throws Exception;
}
