package com.homecaravan.android.consumer.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anh Dao on 9/20/2017.
 */

public class UploadObject {
    @Expose
    @SerializedName("fileName")
    private String fileName;
    @Expose
    @SerializedName("fileUrl")
    private String fileUrl;
    @Expose
    @SerializedName("size")
    private String size;
    @Expose
    @SerializedName("type")
    private String type;

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "UploadObject{" +
                "fileName='" + fileName + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", size='" + size + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
