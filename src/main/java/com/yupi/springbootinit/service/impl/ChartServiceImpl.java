package com.yupi.springbootinit.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.service.ChartService;
import com.yupi.springbootinit.mapper.ChartMapper;
import com.yupi.springbootinit.utils.CheckForDuplicateCsvDataUtils;
import com.yupi.springbootinit.utils.YoudaoTranslatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    implements ChartService{
    @Resource
    private ChartMapper chartMapper;
    @Override
    public void saveChartData(Long userId, String csvData,String chartType) {
        Integer lastChartId = chartMapper.getLastChartIdByUserId(userId);

        Integer newId = (lastChartId == null) ? 1 : lastChartId + 1;

        //类型转换英文
//        String translatedChartType = YoudaoTranslatorUtils.translateChartType(chartType);

        String tableName = "chart_" + userId + "_data" + newId;

        BufferedReader reader = new BufferedReader(new StringReader(csvData));
        String headerLine = null;
        try {
            headerLine = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (headerLine == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "CSV数据为空");
        }

        List<String> columns = Arrays.asList(headerLine.split(","));
        List<List<String>> values = new ArrayList<>();

        String line;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<String> row = Arrays.asList(line.split(","));
            values.add(row);
        }

        //检验csvData数据
        if (StringUtils.isEmpty(csvData)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "CSV 数据为空");
        }

        // 检查 CSV 数据是否重复
        boolean isDuplicate = CheckForDuplicateCsvDataUtils.checkForDuplicateCsvData(csvData);

        if (!isDuplicate) {
            chartMapper.createTable(tableName, columns);
            chartMapper.insertData(tableName, columns, values);
        }
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




