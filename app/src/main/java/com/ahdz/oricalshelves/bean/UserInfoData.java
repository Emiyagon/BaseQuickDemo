package com.ahdz.oricalshelves.bean;

public class UserInfoData {

    /**
     * userId : 79425
     * timestamp : 1608019637281
     * nickName : Hello
     * vipLevel : 0
     * phone : 13658795421
     */

    private int userId;
    private long timestamp;
    private String nickName;
    private int vipLevel;
    private String phone;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
