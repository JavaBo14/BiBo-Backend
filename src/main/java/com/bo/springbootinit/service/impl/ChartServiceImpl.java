package com.bo.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.springbootinit.common.ErrorCode;
import com.bo.springbootinit.exception.BusinessException;
import com.bo.springbootinit.model.entity.Chart;
import com.bo.springbootinit.service.ChartService;
import com.bo.springbootinit.mapper.ChartMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Bo
 * @description 针对表【chart(图表信息表)】的数据库操作Service实现
 * @createDate 2024-06-12 21:16:57
 */
@Slf4j
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
        implements ChartService {
    @Resource
    private ChartMapper chartMapper;

    @Override
    public void saveChartData(Long userId, String csvData, String chartType) {
        // 获取现有表名
        List<String> existingTableNames = chartMapper.getExistingTableNames(userId);

        // 解析 CSV 数据
        BufferedReader reader = new BufferedReader(new StringReader(csvData));
        String headerLine;
        try {
            headerLine = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("读取 CSV 数据错误", e);
        }
        if (headerLine == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "CSV数据为空");
        }

        // 解析列
        List<String> columns = Arrays.asList(headerLine.split(","));
        List<List<String>> values = new ArrayList<>();

        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException("读取 CSV 数据错误", e);
            }

            // 使用 ArrayList 替代 Arrays.asList
            List<String> row = new ArrayList<>(Arrays.asList(line.split(",")));

            // 填充空值
            while (row.size() < columns.size()) {
                row.add(null); // 或者 "" 视具体需求而定
            }

            values.add(row);
        }

        // 计算新数据的总量
        int newDataCount = values.size() * columns.size(); // 每个数据项的计数

        // 检查数据是否重复
        for (String tableName : existingTableNames) {
            int existingDataCount = chartMapper.getTableDataCount(tableName);
            if (newDataCount == existingDataCount) {
                // 如果数据总量相同，认为数据重复，不进行操作
                return;
            }
        }

        // 生成新表名
        Integer lastChartId = chartMapper.getLastChartIdByUserId(userId);
        Integer newId = (lastChartId == null) ? 1 : lastChartId + 1;
        String newTableName = "chart_" + userId + "_data" + newId;

        // 动态生成列定义
        List<String> columnDefinitions = new ArrayList<>();
        for (String column : columns) {
            columnDefinitions.add(column + " VARCHAR(255)");
        }

        // 创建新表
        chartMapper.createTable(newTableName, columnDefinitions);

        // 插入数据
        chartMapper.insertData(newTableName, columns, values);
    }

    public List<Map<String, Object>> getChartDataByUserId(Long userId) {
        // 查询用户的最后一个 ID
        Integer lastId = chartMapper.getLastChartIdByUserId(userId);
        //        int nextId = (lastId != null) ? lastId + 1 : 1;
        // 查询数据
        List<Map<String, Object>> chartDataByUserId = chartMapper.getChartDataByUserId(userId, lastId);
        return chartDataByUserId;
    }
}




