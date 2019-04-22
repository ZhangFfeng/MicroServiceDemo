package com.gaeainfo.demo.constant;

/**
 * 状态常量配置文件
 *
 * @Author: 张丰
 * @Version 1.0
 */
@SuppressWarnings("ALL")
public enum StatusConstants {
    SCUUESS("1", "成功"), FAILED("2", "失败");
    // 是否成功标志 1 成功；2 失败
    private String value;
    // 是否成功描述 成功；失败
    private String desc;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private StatusConstants(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}