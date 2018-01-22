package com.homecaravan.android.consumer.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Anh Dao on 9/11/2017.
 */

public class MessageItem extends RealmObject {

//    [{"id":"5a24bf01ea6fa81cada2cdfa",
//            "createdDatetime":1512357633205,
//            "modifiedDatetime":1512357633205,
//            "content":"new notification",
//            "view":{"id":"595083a10a975a05589b12ab","name":"Nhi Tran",
//            "photo":"http://consumer.homecaravan.net/uploads/account_avatar/20171023183019.jpg",
//            "content":"new notification","createdDatetime":1512357633205,
//            "type":"TEXT"},"type":"TEXT","status":"NORMAL",
//            "thread":{"id":"59fbef8b0a975a08f8c97e80",
//            "createdDatetime":1512370258569,"modifiedDatetime":1512370258569,
//            "participants":[],"mappings":[]}},
//    {"id":"5a13dfe20a975a08f8c98376","user":"595083a10a975a05589b12ab","createdDatetime":1509683083000,
//            "modifiedDatetime":1511280740000,"createdBy":"595083a10a975a05589b12ab",
//            "modifiedBy":"595083a10a975a05589b12ab","content":"3123123213\r\n",
//            "view":{"name":"Nhi Tran","photo":"http://consumer.homecaravan.net/uploads/account_avatar/20171023183019.jpg",
//            "content":"3123123213\r\n","createdDatetime":1509683083000,"type":"TEXT"},
//        "type":"TEXT","status":"NORMAL","thread":{"id":"59fbef8b0a975a08f8c97e80","createdDatetime":1512370258570,
//            "modifiedDatetime":1512370258570,"participants":[],"mappings":[]}}]

    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_INLINE = "INLINE";
    public static final String TYPE_IMAGE = "IMAGE";
    public static final String TYPE_FILE = "FILE";
    public static final String TYPE_DELETE = "DELETE";

    public static final String STATUS_NORMAL = "NORMAL";
    public static final String STATUS_DELETED = "DELETED";


    public static final String TYPE_FROM_ITEM = "from_item";
    public static final String TYPE_ITEM = "item";
    public static final String TYPE_TIME_LINE = "timeline";

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
    @SerializedName("user")
    private String user;
    @Expose
    @SerializedName("data")
    private String data;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("view")
    private MessageThreadView messageThreadView;
    @Expose
    @SerializedName("type")
    private String type; //TEXT - IMAGE
    @Expose
    @SerializedName("status")
    private String status; // NORMAL - DELETED
    @Expose
    @SerializedName("thread")
    private MessageThread messageThread;
    @Expose
    @SerializedName("command")
    private String command;
    @Expose
    @SerializedName("key")
    private String key;
    private byte[] tempImage;
    private String image;
    private boolean hasAppearedOnce;
    private String dateFormat;

    private String date; //timeLine
    private String typeMessage; //item - from item - timeLine

    public MessageItem() {
    }

    public MessageItem(MessageItem item) {
        this.id = item.getId();
        this.createdDatetime = item.getCreatedDatetime();
        this.modifiedDatetime = item.getModifiedDatetime();
        this.createdBy = item.getCreatedBy();
        this.modifiedBy = item.getModifiedBy();
        this.user = item.getUser();
        this.data = item.getData();
        this.content = item.getContent();
        this.messageThreadView = item.getMessageThreadView();
        this.type = item.getType();
        this.status = item.getStatus();
        this.messageThread = item.getMessageThread();
        this.command = item.getCommand();
        this.key = item.getKey();
        this.image = item.getImage();
        this.dateFormat = item.getDateFormat();
        this.date = item.getDate();
        this.typeMessage = item.getTypeMessage();
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageThreadView getMessageThreadView() {
        return messageThreadView;
    }

    public void setMessageThreadView(MessageThreadView messageThreadView) {
        this.messageThreadView = messageThreadView;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MessageThread getMessageThread() {
        return messageThread;
    }

    public void setMessageThread(MessageThread messageThread) {
        this.messageThread = messageThread;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getTempImage() {
        return tempImage;
    }

    public void setTempImage(byte[] tempImage) {
        this.tempImage = tempImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isHasAppearedOnce() {
        return hasAppearedOnce;
    }

    public void setHasAppearedOnce(boolean hasAppearedOnce) {
        this.hasAppearedOnce = hasAppearedOnce;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }
}
