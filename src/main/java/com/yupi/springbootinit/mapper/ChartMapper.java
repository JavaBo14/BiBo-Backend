package com.yupi.springbootinit.mapper;

import com.yupi.springbootinit.model.entity.Chart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

/**
* @author Bo
* @description 针对表【chart(图表信息表)】的数据库操作Mapper
* @createDate 2024-06-12 21:16:57
* @Entity generator.domain.Chart
*/
public interface ChartMapper extends BaseMapper<Chart> {
    @Select("SELECT * FROM chart_${userId}_data#{id}")
    List<Map<String, Object>> getChartDataByUserId(@Param("userId") Long userId, @Param("id") Integer id);
    Integer getLastChartIdByUserId(@Param("userId") Long userId);

    void createTable(@Param("tableName") String tableName, @Param("columns") List<String> columns);

    void insertData(@Param("tableName") String tableName, @Param("columns") List<String> columns, @Param("values") List<List<String>> values);}




