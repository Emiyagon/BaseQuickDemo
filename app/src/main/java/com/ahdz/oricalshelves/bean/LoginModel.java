package com.ahdz.oricalshelves.bean;

public class LoginModel {

        private String token;
        private long timestamp;
        private String nickname;
        public int userId;
        public int nu;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNu() {
        return nu;
    }

    public void setNu(int nu) {
        this.nu = nu;
    }

    public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

    @Override
    public String toString() {
        return "LoginModel{" +
                "token='" + token + '\'' +
                ", timestamp=" + timestamp +
                ", nickname='" + nickname + '\'' +
                ", userId=" + userId +
                ", nu=" + nu +
                '}';
    }
}
