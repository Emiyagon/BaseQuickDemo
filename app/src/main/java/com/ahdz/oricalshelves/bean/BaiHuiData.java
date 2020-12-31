package com.ahdz.oricalshelves.bean;

import java.util.List;

public class BaiHuiData {

    /**
     * appName : s
     * tips : ["日息0.020%起","最快10分钟放款"]
     * quota : 88888
     */

    private String appName;
    private int quota;
    private List<String> tips;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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
