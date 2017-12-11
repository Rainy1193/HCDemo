package com.homecaravan.android.consumer.model.listitem;

public class ParticipantSearchItem {
    private String id;
    private String firstName;
    private String lastName;
    private String avatar;
    private String role = "admin";
    private String weight;
    private String email;
    private String phone;
    private String pnUid;
    private String pnTid;
    private boolean pick;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPnUid() {
        return pnUid;
    }

    public void setPnUid(String pnUid) {
        this.pnUid = pnUid;
    }

    public String getPnTid() {
        return pnTid;
    }

    public void setPnTid(String pnTid) {
        this.pnTid = pnTid;
    }

    public boolean isPick() {
        return pick;
    }

    public void setPick(boolean pick) {
        this.pick = pick;
    }
}
