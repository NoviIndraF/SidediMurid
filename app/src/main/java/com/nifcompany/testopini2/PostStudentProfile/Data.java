package com.nifcompany.testopini2.PostStudentProfile;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("NISN")
	private String nISN;

	@SerializedName("gender")
	private String gender;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("class_id")
	private int classId;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("age")
	private String age;

	@SerializedName("religion")
	private String religion;

	public void setNISN(String nISN){
		this.nISN = nISN;
	}

	public String getNISN(){
		return nISN;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setClassId(int classId){
		this.classId = classId;
	}

	public int getClassId(){
		return classId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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

	public void setAge(String age){
		this.age = age;
	}

	public String getAge(){
		return age;
	}

	public void setReligion(String religion){
		this.religion = religion;
	}

	public String getReligion(){
		return religion;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"nISN = '" + nISN + '\'' + 
			",gender = '" + gender + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",class_id = '" + classId + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",age = '" + age + '\'' + 
			",religion = '" + religion + '\'' + 
			"}";
		}
}