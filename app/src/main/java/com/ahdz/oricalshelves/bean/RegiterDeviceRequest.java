package com.ahdz.oricalshelves.bean;

public class RegiterDeviceRequest {
    private Integer height;
    private Integer width;
    private long timestamp;
    private String deviceId;
    private String imei;
    private String bundleId;//报名
    private String bundleVersion;//版本号
    private String deviceName;//设备名称
    private String channelCode;//设备名称

    public String getChannelCode() {
        return channelCode;
    }

    public RegiterDeviceRequest setChannelCode(String channelCode) {
        this.channelCode = channelCode;
        return this;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public RegiterDeviceRequest setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public int getDevceType() {
        return devceType;
    }

    public RegiterDeviceRequest setDevceType(int devceType) {
        this.devceType = devceType;
        return this;
    }

    private int devceType;//设备名称

    public String getBundleId() {
        return bundleId;
    }

    public RegiterDeviceRequest setBundleId(String bundleId) {
        this.bundleId = bundleId;
        return this;
    }

    public String getBundleVersion() {
        return bundleVersion;
    }

    public RegiterDeviceRequest setBundleVersion(String bundleVersion) {
        this.bundleVersion = bundleVersion;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public RegiterDeviceRequest setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public RegiterDeviceRequest setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public long getTimestamp() {
        return timestamp;

    }

    public RegiterDeviceRequest setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public RegiterDeviceRequest setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getImei() {
        return imei;
    }

    public RegiterDeviceRequest setImei(String imei) {
        this.imei = imei;
        return this;
    }
}
