package com.homecaravan.android.models;

import java.io.InputStream;

/**
 * Created by RAINY on 4/5/2016.
 */
public class Account {
    private static Account account;
    private InputStream ensignUse;
    private String codeUse;
    private String regionUse;
    private InputStream ensignCompany;
    private String codeCompany;
    private String regionCompany;
    private String photoUse;
    private String firstName;
    private String lastName;
    private String mobilePhone;
    private String email;
    private String type;
    private String photoCompany;
    private String companyName;
    private String add1;
    private String add2;
    private String city;
    private String state;
    private String zipCode;
    private String timeZone;
    private String phoneCompany;
    private String hasAgent;
    private String emailAgent;
    private String preferences;
    private String calendar;
    private String password;
    private String rePassword;
    private String agree;
    private String idFacebook;
    private String tokenFacebook;
    private String idLinkedIn;
    private String tokenLinkedIn;
    private String avatarFacebook;
    private String avatarLinkedIn;
    private boolean loginFacebook;
    private boolean loginLinkedIn;

    public static Account getInstance() {
        if (account == null) {
            account = new Account();
        }
        return account;
    }

    public static void setNull() {
        account = null;
    }

    private Account() {
    }

    @Override
    public String toString() {
        return photoUse + " " + firstName + " " + lastName + " " + mobilePhone + " " + email + " " + type + " " + photoCompany + " " +
                " " + companyName + " " + add1 + " " + add2 + " " + city + " " + state + " " + zipCode + " " + phoneCompany + " " + hasAgent + " " +
                emailAgent + " " + preferences + " " + calendar + " " + password + " " + rePassword + " " + agree + " " + getIdLinkedIn() + " " + getIdFacebook();
    }

    public String getPhotoUse() {
        return photoUse;
    }

    public void setPhotoUse(String photoUse) {
        this.photoUse = photoUse;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhotoCompany() {
        return photoCompany;
    }

    public void setPhotoCompany(String photoCompany) {
        this.photoCompany = photoCompany;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneCompany() {
        return phoneCompany;
    }

    public void setPhoneCompany(String phoneCompany) {
        this.phoneCompany = phoneCompany;
    }

    public String getHasAgent() {
        return hasAgent;
    }

    public void setHasAgent(String hasAgent) {
        this.hasAgent = hasAgent;
    }

    public String getEmailAgent() {
        return emailAgent;
    }

    public void setEmailAgent(String emailAgent) {
        this.emailAgent = emailAgent;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public InputStream getEnsignUse() {
        return ensignUse;
    }

    public void setEnsignUse(InputStream ensignUse) {
        this.ensignUse = ensignUse;
    }

    public String getCodeUse() {
        return codeUse;
    }

    public void setCodeUse(String codeUse) {
        this.codeUse = codeUse;
    }

    public String getRegionUse() {
        return regionUse;
    }

    public void setRegionUse(String regionUse) {
        this.regionUse = regionUse;
    }

    public InputStream getEnsignCompany() {
        return ensignCompany;
    }

    public void setEnsignCompany(InputStream ensignCompany) {
        this.ensignCompany = ensignCompany;
    }

    public String getCodeCompany() {
        return codeCompany;
    }

    public void setCodeCompany(String codeCompany) {
        this.codeCompany = codeCompany;
    }

    public String getRegionCompany() {
        return regionCompany;
    }

    public void setRegionCompany(String regionCompany) {
        this.regionCompany = regionCompany;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getTokenFacebook() {
        return tokenFacebook;
    }

    public void setTokenFacebook(String tokenFacebook) {
        this.tokenFacebook = tokenFacebook;
    }

    public String getIdLinkedIn() {
        return idLinkedIn;
    }

    public void setIdLinkedIn(String idLinkedIn) {
        this.idLinkedIn = idLinkedIn;
    }

    public String getTokenLinkedIn() {
        return tokenLinkedIn;
    }

    public void setTokenLinkedIn(String tokenLinkedIn) {
        this.tokenLinkedIn = tokenLinkedIn;
    }

    public String getAvatarFacebook() {
        return avatarFacebook;
    }

    public void setAvatarFacebook(String avatarFacebook) {
        this.avatarFacebook = avatarFacebook;
    }

    public String getAvatarLinkedIn() {
        return avatarLinkedIn;
    }

    public void setAvatarLinkedIn(String avatarLinkedIn) {
        this.avatarLinkedIn = avatarLinkedIn;
    }

    public boolean isLoginFacebook() {
        return loginFacebook;
    }

    public void setLoginFacebook(boolean loginFacebook) {
        this.loginFacebook = loginFacebook;
    }

    public boolean isLoginLinkedIn() {
        return loginLinkedIn;
    }

    public void setLoginLinkedIn(boolean loginLinkedIn) {
        this.loginLinkedIn = loginLinkedIn;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
