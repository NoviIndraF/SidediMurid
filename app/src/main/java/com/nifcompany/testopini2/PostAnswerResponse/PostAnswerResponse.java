package com.nifcompany.testopini2.PostAnswerResponse;

import com.google.gson.annotations.SerializedName;

public class PostAnswerResponse{

	@SerializedName("data")
	private Data data;

	@SerializedName("status")
	private String status;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"PostAnswerResponse{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}