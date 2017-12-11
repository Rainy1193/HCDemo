package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 4/12/2016.
 */
public class ResponseUpdate {
	@SerializedName("success")
	@Expose
	private String success;
	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("data")
	@Expose
	private User data;
	@SerializedName("code")
	@Expose
	private int code;

	public String isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	public User getData() {
		return data;
	}
}
