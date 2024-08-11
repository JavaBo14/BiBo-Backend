package com.bo.springbootinit.mapper;

import com.bo.springbootinit.model.entity.Chart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* @author Bo
* @description 针对表【chart(图表信息表)】的数据库操作Mapper
* @createDate 2024-06-12 21:16:57
* @Entity generator.domain.Chart
*/
public interface ChartMapper extends BaseMapper<Chart> {

    @Select("SELECT table_name FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name LIKE CONCAT('chart_', #{userId}, '_data%')")
    List<String> getExistingTableNames(@Param("userId") Long userId);
//    @Select("SELECT COUNT(*) FROM ${tableName}")
//    int getTableRowCount(@Param("tableName") String tableName);
    // 统计每列的非空数据数量
//    @Select("SELECT COUNT(${columnName}) FROM ${tableName}")
//    int countNonNullValues(@Param("tableName") String tableName, @Param("columnName") String columnName);
    @Select("SELECT COUNT(*) * (SELECT COUNT(*) FROM information_schema.columns WHERE table_name = #{tableName}) FROM ${tableName}")
    int getTableDataCount(@Param("tableName") String tableName);
    @Select("SELECT * FROM chart_${userId}_data#{id}")
    List<Map<String, Object>> getChartDataByUserId(@Param("userId") Long userId, @Param("id") Integer id);
    Integer getLastChartIdByUserId(@Param("userId") Long userId);

    void createTable(@Param("tableName") String tableName, @Param("columns") List<String> columns);

    void insertData(@Param("tableName") String tableName, @Param("columns") List<String> columns, @Param("values") List<List<String>> values);

}




