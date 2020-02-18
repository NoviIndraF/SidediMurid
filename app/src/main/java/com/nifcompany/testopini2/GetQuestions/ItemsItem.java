package com.nifcompany.testopini2.GetQuestions;

import com.google.gson.annotations.SerializedName;

public class ItemsItem{

	@SerializedName("body")
	private String body;

	@SerializedName("category")
	private String category;

	@SerializedName("question_id")
	private int questionId;

	public ItemsItem(int questionId, String body, String category) {
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getBody(){
		return body;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	public void setQuestionId(int questionId){
		this.questionId = questionId;
	}

	public int getQuestionId(){
		return questionId;
	}

	@Override
 	public String toString(){
		return 
			"ItemsItem{" + 
			"body = '" + body + '\'' + 
			",category = '" + category + '\'' + 
			",question_id = '" + questionId + '\'' + 
			"}";
		}
}