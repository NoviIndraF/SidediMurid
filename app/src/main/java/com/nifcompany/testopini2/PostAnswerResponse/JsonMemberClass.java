package com.nifcompany.testopini2.PostAnswerResponse;

import com.google.gson.annotations.SerializedName;

public class JsonMemberClass{

	@SerializedName("name_class")
	private String nameClass;

	@SerializedName("path_img_header")
	private String pathImgHeader;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("code_ref_class")
	private String codeRefClass;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	public void setNameClass(String nameClass){
		this.nameClass = nameClass;
	}

	public String getNameClass(){
		return nameClass;
	}

	public void setPathImgHeader(String pathImgHeader){
		this.pathImgHeader = pathImgHeader;
	}

	public String getPathImgHeader(){
		return pathImgHeader;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setCodeRefClass(String codeRefClass){
		this.codeRefClass = codeRefClass;
	}

	public String getCodeRefClass(){
		return codeRefClass;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"JsonMemberClass{" + 
			"name_class = '" + nameClass + '\'' + 
			",path_img_header = '" + pathImgHeader + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",code_ref_class = '" + codeRefClass + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}