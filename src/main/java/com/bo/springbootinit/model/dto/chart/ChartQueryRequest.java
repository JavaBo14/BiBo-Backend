package com.bo.springbootinit.model.dto.chart;

import com.bo.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/JavaBo14">Bo</a>
 * @from <a href="https://github.com/JavaBo14/Bi">
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChartQueryRequest extends PageRequest implements Serializable {

    private Long id;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表名称
     */
    private String name;



    /**
     * 图表类型
     */
    private String chartType;

    private Long userId;

    private static final long serialVersionUID = 1L;

}