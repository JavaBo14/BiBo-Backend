package com.bo.springbootinit.common;

import com.bo.springbootinit.constant.CsvDataConstant;
import com.bo.springbootinit.utils.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
@Component
public class UserInput {
    @Resource
    private CsvDataConstant csvDataConstant;
    // 其他成员变量和方法

    public String buildUserInput(String goal, String chartType, MultipartFile multipartFile) {
        StringBuilder userInput = new StringBuilder();
        userInput.append("分析需求：").append("\n");

        String userGoal = goal;
        if (StringUtils.isNotBlank(chartType)) {
            userGoal += "，请使用" + chartType;
        }
        userInput.append(userGoal).append("\n");
        userInput.append("数据：").append("\n");

        String csvData = ExcelUtils.excelTocsv(multipartFile);
        csvDataConstant.setCsvData(csvData);
        userInput.append(csvData).append("\n");

        return userInput.toString();
    }
}
