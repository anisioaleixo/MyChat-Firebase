package com.anisioaleixo.mychat_firebase;

public class User {
    private String uuid;
    private String userName;
    private String profileUrl;

    public User() {
    }

    public User(String uuid, String userName, String profileUrl) {
        this.uuid = uuid;
        this.userName = userName;
        this.profileUrl = profileUrl;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
