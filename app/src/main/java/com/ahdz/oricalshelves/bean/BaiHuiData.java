package com.ahdz.oricalshelves.bean;

import java.util.List;

public class BaiHuiData {

    /**
     * appName : 小山随心借
     * tips : ["日息0.020%起","最快10分钟放款"]
     * quota : 88888
     */

    private String appName;
    private int quota;
    private List<String> tips;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public List<String> getTips() {
        return tips;
    }

    public void setTips(List<String> tips) {
        this.tips = tips;
    }
}
