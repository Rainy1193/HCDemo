package com.homecaravan.android.consumer.model;


public class ExclusiveAgent {
    private int avatar;
    private String name;
    private String role;
    private String id;
    private boolean select;

    public ExclusiveAgent(int avatar, String name, String role, String id, boolean select) {
        this.avatar = avatar;
        this.name = name;
        this.role = role;
        this.id = id;
        this.select = select;
    }

    public boolean isSelect() {
        return select;
    }

    public int getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getId() {
        return id;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
