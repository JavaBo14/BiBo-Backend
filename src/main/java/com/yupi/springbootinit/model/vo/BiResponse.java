package com.yupi.springbootinit.model.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BiResponse {
    private String genChart;
    private String genResult;
    private Long chartId;
    private List<Map<String, Object>> data;
}
