package com.yupi.springbootinit.model.enums;

public enum ChartStatus {
    WAIT("wait"),
    RUNNING("running"),
    SUCCEED("succeed"),
    FAILED("failed");

    private final String status;

    ChartStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    // 新增方法，返回小写字符串表示
    public String toLowerCase() {
        return status.toLowerCase();
    }
}

