package com.bo.springbootinit.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
//public class ExcelUtils {
//    public static String excelTocsv(MultipartFile multipartFile) {
//        List<Map<Integer, String>> list = new ArrayList<>();
//        try {
//            list = EasyExcel.read(multipartFile.getInputStream())
//                    .excelType(ExcelTypeEnum.XLSX)
//                    .sheet()
//                    .headRowNumber(0)
//                    .doReadSync();
//        } catch (IOException e) {
//            log.error("表格处理错误", e);
//        }
//        if (CollUtil.isEmpty(list)) {
//            return "";
//        }
//
//        // 存储检查重复数据的集合
//        Set<String> uniqueDataSet = new HashSet<>();
//
//        // 转换为 csv
//        StringBuilder stringBuilder = new StringBuilder();
//        // 读取表头
//        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap) list.get(0);
//        List<String> headerList = headerMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
//        stringBuilder.append(StringUtils.join(headerList, ",")).append("\n");
//
//        // 读取数据
//        for (int i = 1; i < list.size(); i++) {
//            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap) list.get(i);
//            List<String> dataList = dataMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
//            String csvRow = StringUtils.join(dataList, ",");
//
//            // 检查是否重复
//            if (uniqueDataSet.contains(csvRow)) {
//                log.warn("重复数据: " + csvRow);
//                continue; // 跳过重复数据
//            }
//
//            // 将不重复的数据添加到集合中
//            uniqueDataSet.add(csvRow);
//
//            stringBuilder.append(csvRow).append("\n");
//        }
//        return stringBuilder.toString();
//    }
//@Slf4j
//public class ExcelUtils {
//    public static String excelTocsv(MultipartFile multipartFile) {
//        List<Map<Integer, String>> list = new ArrayList<>();
//        try {
//            // 判断文件类型
//            String filename = multipartFile.getOriginalFilename();
//            ExcelTypeEnum excelType = filename.endsWith(".xls") ? ExcelTypeEnum.XLS : ExcelTypeEnum.XLSX;
//
//            list = EasyExcel.read(multipartFile.getInputStream())
//                    .excelType(excelType)  // 动态指定 Excel 类型
//                    .sheet()
//                    .headRowNumber(0)
//                    .doReadSync();
//        } catch (IOException e) {
//            log.error("表格处理错误", e);
//        }
//        if (list == null || list.isEmpty()) {
//            return "";
//        }
//
//        // 存储检查重复数据的集合
//        Set<String> uniqueDataSet = new HashSet<>();
//
//        // 转换为 CSV
//        StringBuilder stringBuilder = new StringBuilder();
//
//        // 读取表头
//        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap) list.get(0);
//        List<String> headerList = new ArrayList<>();
//        for (String value : headerMap.values()) {
//            if (value != null && !value.isEmpty()) {
//                headerList.add(value);
//            }
//        }
//        stringBuilder.append(String.join(",", headerList)).append("\n");
//
//        // 读取数据
//        for (int i = 1; i < list.size(); i++) {
//            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap) list.get(i);
//            List<String> dataList = new ArrayList<>();
//            for (String value : dataMap.values()) {
//                if (value != null && !value.isEmpty()) {
//                    dataList.add(value);
//                }
//            }
//            String csvRow = String.join(",", dataList);
//
//            // 检查是否重复
//            if (uniqueDataSet.contains(csvRow)) {
//                log.warn("重复数据: " + csvRow);
//                continue; // 跳过重复数据
//            }
//
//            // 将不重复的数据添加到集合中
//            uniqueDataSet.add(csvRow);
//
//            stringBuilder.append(csvRow).append("\n");
//        }
//        return stringBuilder.toString();
//    }
@Slf4j
public class ExcelUtils {

    // 处理 Excel 文件 (支持 .xls 和 .xlsx)
    public static String excelToCsv(MultipartFile multipartFile) {
        List<Map<Integer, String>> list = new ArrayList<>();
        try {
            // 判断是 .xls 还是 .xlsx
            ExcelTypeEnum excelType = multipartFile.getOriginalFilename().endsWith(".xls") ? ExcelTypeEnum.XLS : ExcelTypeEnum.XLSX;
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(excelType)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("表格处理错误", e);
        }

        if (CollectionUtils.isEmpty(list)) {
            return "";
        }

        // 存储检查重复数据的集合
        Set<String> uniqueDataSet = new HashSet<>();
        StringBuilder stringBuilder = new StringBuilder();

        // 读取表头
        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap) list.get(0);
        List<String> headerList = headerMap.values().stream()
                .filter(ObjectUtils::isNotEmpty)
                .collect(Collectors.toList());
        stringBuilder.append(StringUtils.join(headerList, ",")).append("\n");

        // 读取数据
        for (int i = 1; i < list.size(); i++) {
            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap) list.get(i);
            List<String> dataList = dataMap.values().stream()
                    .filter(ObjectUtils::isNotEmpty)
                    .collect(Collectors.toList());
            String csvRow = StringUtils.join(dataList, ",");

            // 检查是否重复
            if (uniqueDataSet.contains(csvRow)) {
                log.warn("重复数据: " + csvRow);
                continue; // 跳过重复数据
            }

            uniqueDataSet.add(csvRow);
            stringBuilder.append(csvRow).append("\n");
        }
        return stringBuilder.toString();
    }

    // 处理 CSV 文件
    public static String csvToCsv(MultipartFile multipartFile) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> uniqueDataSet = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 检查是否重复
                if (uniqueDataSet.contains(line)) {
                    log.warn("重复数据: " + line);
                    continue; // 跳过重复数据
                }

                uniqueDataSet.add(line); // 添加不重复的数据
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            log.error("CSV 文件处理错误", e);
        }

        return stringBuilder.toString();
    }
}


//    public static void main(String[] args) {
//        excelTocsv(null);
//    }
