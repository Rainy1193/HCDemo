package com.homecaravan.android.consumer.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Anh Dao on 9/7/2017.
 */

public class MessageThread extends RealmObject {
//
//    {"id":"59fbef8b0a975a08f8c97e80",
//            "name":"1600 Pennsylvania Ave Washington, DC 20500|Thu, Nov 9th 15:30pm",
//            "participants":["59f686b30a975a06fea5fbf2", "595083a10a975a05589b12ab"],
//        "partUsers":[{"id":"595083a10a975a05589b12ab",
//            "name":"Nhi Tran",
//            "avatar":"http://consumer.homecaravan.net/uploads/account_avatar/20171023183019.jpg"},
//        {"id":"59f686b30a975a06fea5fbf2","name":"Loan Tran","avatar":"http://consumer.homecaravan.net/uploads/account_avatar/20171023183214.jpg"}],
//        "view":{"id":"595083a10a975a05589b12ab","name":"Nhi Tran","photo":"http://consumer.homecaravan.net/uploads/account_avatar/20171023183019.jpg",
//            "content":"new notification","createdDatetime":1512357633205,"type":"TEXT"},
//        "mappings":[{"id":"59f686b30a975a06fea5fbf2","time":0,"new":true},
//        {"id":"595083a10a975a05589b12ab","time":1512357654935,"new":false}],
//        "createdDatetime":1509683080000,"modifiedDatetime":1512357633205,
//            "createdBy":"595083a10a975a05589b12ab"}
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("createdDatetime")
    private String createdDatetime;
    @Expose
    @SerializedName("modifiedDatetime")
    private String modifiedDatetime;
    @Expose
    @SerializedName("createdBy")
    private String createdBy;
    @Expose
    @SerializedName("modifiedBy")
    private String modifiedBy;
    @Expose
    @SerializedName("data")
    private String data;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("participants")
    private RealmList<String> participants;
    @Expose
    @SerializedName("view")
    private MessageThreadView messageThreadView;
    private RealmList<MessageUserData> userInThread;

    public MessageThread() {

    }

    public MessageThread(String id, String createdDatetime, String modifiedDatetime) {
        this.id = id;
        this.createdDatetime = createdDatetime;
        this.modifiedDatetime = modifiedDatetime;
    }

    public MessageThread(String id, String createdDatetime, String modifiedDatetime, String modifiedBy,
                         String createdBy, String data, String name, RealmList<String> participants,
                         MessageThreadView messageThreadView) {
        this.id = id;
        this.createdDatetime = createdDatetime;
        this.modifiedDatetime = modifiedDatetime;
        this.createdBy = createdBy;
        this.data = data;
        this.name = name;
        this.participants = participants;
        this.messageThreadView = messageThreadView;
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + modifiedDatetime + " " + messageThreadView.getPhoto() + " " + messageThreadView.getContent();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getModifiedDatetime() {
        return modifiedDatetime;
    }

    public void setModifiedDatetime(String modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(RealmList<String> participants) {
        this.participants = participants;
    }

    public MessageThreadView getMessageThreadView() {
        return messageThreadView;
    }

    public void setMessageThreadView(MessageThreadView messageThreadView) {
        this.messageThreadView = messageThreadView;
    }

    public RealmList<MessageUserData> getUserInThread() {
        return userInThread;
    }

    public void setUserInThread(RealmList<MessageUserData> userInThread) {
        this.userInThread = userInThread;
    }
}
