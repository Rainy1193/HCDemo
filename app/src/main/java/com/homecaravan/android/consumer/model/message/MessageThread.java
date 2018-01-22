package com.homecaravan.android.consumer.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Anh Dao on 9/7/2017.
 */

public class MessageThread extends RealmObject {
    public static final String TYPE_SINGLE = "SINGLE";
    public static final String TYPE_GROUP = "GROUP";

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
    @SerializedName("partUsers")
    private RealmList<MessageUserData> userInThread;
    @Expose
    @SerializedName("view")
    private MessageThreadView messageThreadView;
    @Expose
    @SerializedName("mappings")
    private RealmList<Mapping> mappings;
    @Expose
    @SerializedName("type")
    private String type; // SINGLE - GROUP

    private RealmList<MessageItem> messageItem;

    public MessageThread() {
    }

    public MessageThread(MessageThread thread) {
        this.id = thread.getId();
        this.createdDatetime = thread.getCreatedDatetime();
        this.modifiedDatetime = thread.getModifiedDatetime();
        this.createdBy = thread.getCreatedBy();
        this.modifiedBy = thread.getModifiedBy();
        this.data = thread.getData();
        this.name = thread.getName();
        this.participants = thread.getParticipants();
        this.userInThread = thread.getUserInThread();
        this.messageThreadView = thread.getMessageThreadView();
        this.mappings = thread.getMappings();
        this.type = thread.getType();
        this.messageItem = thread.getMessageItem();
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

    public RealmList<MessageUserData> getUserInThread() {
        return userInThread;
    }

    public void setUserInThread(RealmList<MessageUserData> userInThread) {
        this.userInThread = userInThread;
    }

    public MessageThreadView getMessageThreadView() {
        return messageThreadView;
    }

    public void setMessageThreadView(MessageThreadView messageThreadView) {
        this.messageThreadView = messageThreadView;
    }

    public RealmList<Mapping> getMappings() {
        return mappings;
    }

    public void setMappings(RealmList<Mapping> mappings) {
        this.mappings = mappings;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RealmList<MessageItem> getMessageItem() {
        return messageItem;
    }

    public void setMessageItem(RealmList<MessageItem> messageItem) {
        this.messageItem = messageItem;
    }
}
