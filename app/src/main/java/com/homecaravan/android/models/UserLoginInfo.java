package com.homecaravan.android.models;


/**
 * Created by RAINY on 3/28/2016.
 */
public class UserLoginInfo {

    private static UserLoginInfo loginInfo;
    private boolean changePhotoAccount;
    private boolean changeAgent;
    private boolean changeLogo;
    private String photoAccount;
    private String firstName;
    private String lastName;
    private String mobilePhone;
    private String id;
    private String nameAgent;
    private String phoneAgent;
    private String email;
    private String companyName;
    private String add1;
    private String add2;
    private String city;
    private String state;
    private String zipcode;
    private String preferences;
    private String phoneCompany;
    private String logoCompany;
    private String type;
    private String hasAgent;
    private String agentEmail;
    private String officePhone;
    private String calendar;
    private String password;
    private String deviceToken;
    private String agentCompany;
    private String token;
    private String region;
    private String regionCompany;
    private String timeZone;
    private String idAgent;

    private String titleCompanyAgent;
    private String add1CompanyAgent;
    private String add2CompanyAgent;
    private String cityCompanyAgent;
    private String stateCompanyAgent;
    private String zipCompanyAgent;
    private String photoAgent;

    public static UserLoginInfo getInstance() {
        if (loginInfo == null) {
            loginInfo = new UserLoginInfo();
        }
        return loginInfo;
    }

    private UserLoginInfo() {
    }

    public void setNull() {
        loginInfo = null;
    }

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

    public String getPhotoAccount() {
        return photoAccount;
    }

    public void setPhotoAccount(String photoAccount) {
        this.photoAccount = photoAccount;
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


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhoneCompany() {
        return phoneCompany;
    }

    public void setPhoneCompany(String phoneCompany) {
        this.phoneCompany = phoneCompany;
    }

    public String getLogoCompany() {
        return logoCompany;
    }

    public void setLogoCompany(String logoCompany) {
        this.logoCompany = logoCompany;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHasAgent() {
        return hasAgent;
    }

    public void setHasAgent(String hasAgent) {
        this.hasAgent = hasAgent;
    }

    public String getAgentEmail() {
        return agentEmail;
    }


    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public String getNameAgent() {
        return nameAgent;
    }

    public void setNameAgent(String nameAgent) {
        this.nameAgent = nameAgent;
    }

    public String getPhoneAgent() {
        return phoneAgent;
    }

    public void setPhoneAgent(String phoneAgent) {
        this.phoneAgent = phoneAgent;
    }

    public String getIdAgent() {
        return idAgent;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public void setIdAgent(String idAgent) {
        this.idAgent = idAgent;
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

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionCompany() {
        return regionCompany;
    }

    public void setRegionCompany(String regionCompany) {
        this.regionCompany = regionCompany;
    }

    public boolean isChangePhotoAccount() {
        return changePhotoAccount;
    }

    public boolean isChangeAgent() {
        return changeAgent;
    }

    public boolean isChangeLogo() {
        return changeLogo;
    }

    public void setChangePhotoAccount(boolean changePhotoAccount) {
        this.changePhotoAccount = changePhotoAccount;
    }

    public void setChangeAgent(boolean changeAgent) {
        this.changeAgent = changeAgent;
    }

    public void setChangeLogo(boolean changeLogo) {
        this.changeLogo = changeLogo;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getAgentCompany() {
        return agentCompany;
    }

    public void setAgentCompany(String agentCompany) {
        this.agentCompany = agentCompany;
    }

    public String getTitleCompanyAgent() {
        return titleCompanyAgent;
    }

    public void setTitleCompanyAgent(String titleCompanyAgent) {
        this.titleCompanyAgent = titleCompanyAgent;
    }

    public String getAdd1CompanyAgent() {
        return add1CompanyAgent;
    }

    public void setAdd1CompanyAgent(String add1CompanyAgent) {
        this.add1CompanyAgent = add1CompanyAgent;
    }

    public String getAdd2CompanyAgent() {
        return add2CompanyAgent;
    }

    public void setAdd2CompanyAgent(String add2CompanyAgent) {
        this.add2CompanyAgent = add2CompanyAgent;
    }

    public String getCityCompanyAgent() {
        return cityCompanyAgent;
    }

    public void setCityCompanyAgent(String cityCompanyAgent) {
        this.cityCompanyAgent = cityCompanyAgent;
    }

    public String getStateCompanyAgent() {
        return stateCompanyAgent;
    }

    public void setStateCompanyAgent(String stateCompanyAgent) {
        this.stateCompanyAgent = stateCompanyAgent;
    }

    public String getZipCompanyAgent() {
        return zipCompanyAgent;
    }

    public void setZipCompanyAgent(String zipCompanyAgent) {
        this.zipCompanyAgent = zipCompanyAgent;
    }

    public String getPhotoAgent() {
        return photoAgent;
    }

    public void setPhotoAgent(String photoAgent) {
        this.photoAgent = photoAgent;
    }


    @Override
    public String toString() {
        return "UserLoginInfo{" +
                "changePhotoAccount=" + changePhotoAccount +
                ", changeAgent=" + changeAgent +
                ", changeLogo=" + changeLogo +
                ", photoAccount='" + photoAccount + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", id='" + id + '\'' +
                ", nameAgent='" + nameAgent + '\'' +
                ", phoneAgent='" + phoneAgent + '\'' +
                ", email='" + email + '\'' +
                ", companyName='" + companyName + '\'' +
                ", add1='" + add1 + '\'' +
                ", add2='" + add2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", preferences='" + preferences + '\'' +
                ", phoneCompany='" + phoneCompany + '\'' +
                ", logoCompany='" + logoCompany + '\'' +
                ", type='" + type + '\'' +
                ", hasAgent='" + hasAgent + '\'' +
                ", agentEmail='" + agentEmail + '\'' +
                ", officePhone='" + officePhone + '\'' +
                ", calendar='" + calendar + '\'' +
                ", password='" + password + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", agentCompany='" + agentCompany + '\'' +
                ", token='" + token + '\'' +
                ", region='" + region + '\'' +
                ", regionCompany='" + regionCompany + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", idAgent='" + idAgent + '\'' +
                ", titleCompanyAgent='" + titleCompanyAgent + '\'' +
                ", add1CompanyAgent='" + add1CompanyAgent + '\'' +
                ", add2CompanyAgent='" + add2CompanyAgent + '\'' +
                ", cityCompanyAgent='" + cityCompanyAgent + '\'' +
                ", stateCompanyAgent='" + stateCompanyAgent + '\'' +
                ", zipCompanyAgent='" + zipCompanyAgent + '\'' +
                ", photoAgent='" + photoAgent + '\'' +
                '}';
    }
}
