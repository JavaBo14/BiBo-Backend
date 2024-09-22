package com.bo.springbootinit.common;

import cn.hutool.core.io.FileUtil;
import com.bo.springbootinit.constant.CommonConstant;
import com.bo.springbootinit.constant.CsvDataConstant;
import com.bo.springbootinit.exception.ThrowUtils;
import com.bo.springbootinit.utils.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class UserInput {

    @Resource
    private CsvDataConstant csvDataConstant;

    // 校验并构建用户输入
    public String buildUserInput(String goal, String chartType, MultipartFile multipartFile) {
        // 校验文件大小
        long size = multipartFile.getSize();
        String originalFilename = multipartFile.getOriginalFilename();
        ThrowUtils.throwIf(size > CommonConstant.ONE_MB, ErrorCode.PARAMS_ERROR, "目标文件过大");

        // 获取文件后缀名
        String suffix = FileUtil.getSuffix(originalFilename);
        List<String> excelSuffixList = Arrays.asList("xlsx", "xls");

        // 检查文件后缀是否合法
        ThrowUtils.throwIf(!(suffix.equals("csv") || excelSuffixList.contains(suffix)), ErrorCode.PARAMS_ERROR, "文件格式错误");

        // 构建用户输入
        StringBuilder userInput = new StringBuilder();
        userInput.append("分析需求：").append("\n");

        // 构建用户需求
        String userGoal = goal;
        if (StringUtils.isNotBlank(chartType)) {
            userGoal += "，请使用" + chartType;
        }
        userInput.append(userGoal).append("\n");

        userInput.append("数据：").append("\n");

        // 根据文件后缀处理数据
        String csvData = "";
        if (suffix.equals("csv")) {
            csvData = ExcelUtils.csvToCsv(multipartFile);
        } else if (excelSuffixList.contains(suffix)) {
            csvData = ExcelUtils.excelToCsv(multipartFile);
        }

        // 保存 CSV 数据到 CsvDataConstant
        csvDataConstant.setCsvData(csvData);
        userInput.append(csvData).append("\n");

        return userInput.toString();
    }
}
