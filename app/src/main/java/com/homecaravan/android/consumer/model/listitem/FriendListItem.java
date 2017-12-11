package com.homecaravan.android.consumer.model.listitem;


public class FriendListItem {
    private int id;
    private boolean isPick;
    private String avatar;
    private String name;
    private String phone;
    private String email;
    private int bgAvatar;
    private String shortName;

    public FriendListItem(int id, boolean isPick, String avatar, String name, String shortName, String phone, String email, int bgAvatar) {
        this.id = id;
        this.isPick = isPick;
        this.avatar = avatar;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.bgAvatar = bgAvatar;
        this.shortName = shortName;
    }

    public boolean isPick() {
        return isPick;
    }

    public void setPick(boolean pick) {
        isPick = pick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBgAvatar() {
        return bgAvatar;
    }

    public void setBgAvatar(int bgAvatar) {
        this.bgAvatar = bgAvatar;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
