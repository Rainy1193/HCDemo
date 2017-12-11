package com.homecaravan.android.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vankhoadesign on 6/30/16.
 */
public class ClientUser {
    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("FIRST_NAME")
    @Expose
    private String firstName;
    @SerializedName("LAST_NAME")
    @Expose
    private String lastName;
    @SerializedName("EMAIL")
    @Expose
    private String email;
    @SerializedName("PHONE")
    @Expose
    private String phone;
    @SerializedName("PHOTO")
    @Expose
    private String photo;

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return "ClientUser{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
