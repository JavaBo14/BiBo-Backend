package com.yupi.springbootinit.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
public class ExcelUtils {
    public static String excelTocsv(MultipartFile multipartFile){
        // 读取数据
        List<Map<Integer, String>> list = new ArrayList<>();
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("表格处理错误", e);
        }
        if (CollUtil.isEmpty(list)) {
            return "";
        }

        // 存储检查重复数据的集合
        Set<String> uniqueDataSet = new HashSet<>();

        // 转换为 csv
        StringBuilder stringBuilder = new StringBuilder();
        // 读取表头
        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap) list.get(0);
        List<String> headerList = headerMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
        stringBuilder.append(StringUtils.join(headerList, ",")).append("\n");

        // 读取数据
        for (int i = 1; i < list.size(); i++) {
            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap) list.get(i);
            List<String> dataList = dataMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
            String csvRow = StringUtils.join(dataList, ",");

            // 检查是否重复
            if (uniqueDataSet.contains(csvRow)) {
                log.warn("重复数据: " + csvRow);
                continue; // 跳过重复数据
            }

            // 将不重复的数据添加到集合中
            uniqueDataSet.add(csvRow);

            stringBuilder.append(csvRow).append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        excelTocsv(null);
    }
}
