package com.ahdz.oricalshelves.bean;

import java.util.List;

public class Projects {
    /**
     * id : 36
     * icon : https://aihong.oss-cn-hangzhou.aliyuncs.com/temp/20201109/164950110003.png
     * name : 山桃借
     * keywords : ["20岁以上"]
     * loanRange : 1000-20万
     * loanTerm : 1-36月
     * interestRate : 日息0.040%
     * recommands : 急速下款
     * applyers : 1929人已申请
     * needPay : 0
     */

    private int id;
    private String icon;
    private String name;
    private String loanRange;
    private String loanTerm;
    private String interestRate;
    private String recommands;
    private String applyers;
    private int needPay;
    private List<String> keywords;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoanRange() {
        return loanRange;
    }

    public void setLoanRange(String loanRange) {
        this.loanRange = loanRange;
    }

    public String getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(String loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getRecommands() {
        return recommands;
    }

    public void setRecommands(String recommands) {
        this.recommands = recommands;
    }

    public String getApplyers() {
        return applyers;
    }

    public void setApplyers(String applyers) {
        this.applyers = applyers;
    }

    public int getNeedPay() {
        return needPay;
    }

    public void setNeedPay(int needPay) {
        this.needPay = needPay;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
