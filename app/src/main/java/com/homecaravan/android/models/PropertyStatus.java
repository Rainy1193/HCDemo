package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vankhoadesign on 7/14/16.
 */
public class PropertyStatus implements Serializable {
        @SerializedName("ID")
        @Expose
        private String id;
        @SerializedName("STATUS")
        @Expose
        private String status;
        @SerializedName("INACTIVE")
        @Expose
        private String inactive;
        @SerializedName("CREATE_TIME")
        @Expose
        private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getInactive() {
        return inactive;
    }

    public void setInactive(String inactive) {
        this.inactive = inactive;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
