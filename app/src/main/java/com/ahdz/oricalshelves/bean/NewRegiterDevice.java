package com.ahdz.oricalshelves.bean;

public class NewRegiterDevice {
    String devices;
    String generateId;
    String imei;
    Long userId;
    String sign;

    public String getDevices() {
        return devices;
    }

    public NewRegiterDevice setDevices(String devices) {
        this.devices = devices;
        return this;
    }

    public String getGenerateId() {
        return generateId;
    }

    public NewRegiterDevice setGenerateId(String generateId) {
        this.generateId = generateId;
        return this;
    }

    public String getImei() {
        return imei;
    }

    public NewRegiterDevice setImei(String imei) {
        this.imei = imei;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public NewRegiterDevice setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public NewRegiterDevice setSign(String sign) {
        this.sign = sign;
        return this;

    }
}
