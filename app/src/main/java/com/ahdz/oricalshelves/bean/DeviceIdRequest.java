package com.ahdz.oricalshelves.bean;


public class DeviceIdRequest {
    private Integer height;
    private Integer width;
    private long timestamp;
    private String ip;
    private String imei;

    public Integer getHeight() {
        return height;
    }

    public DeviceIdRequest setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public DeviceIdRequest setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public DeviceIdRequest setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public DeviceIdRequest setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getImei() {
        return imei;
    }

    public DeviceIdRequest setImei(String imei) {
        this.imei = imei;
        return this;
    }
}
