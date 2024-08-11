package com.bo.springbootinit.service;

import com.bo.springbootinit.model.entity.Chart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author Bo
* @description 针对表【chart(图表信息表)】的数据库操作Service
* @createDate 2024-06-12 21:16:57
*/
public interface ChartService extends IService<Chart> {

    void saveChartData(Long userId, String csvData,String chartType);

    List<Map<String, Object>> getChartDataByUserId(Long userId);
}
