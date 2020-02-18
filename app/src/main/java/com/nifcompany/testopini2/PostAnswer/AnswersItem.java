package com.nifcompany.testopini2.PostAnswer;

import com.google.gson.annotations.SerializedName;

public class AnswersItem{

	@SerializedName("question_id")
	private int question_id;

	@SerializedName("value")
	private int value;

	public void setQuestionId(int question_id){
		this.question_id = question_id;
	}

	public int getQuestionId(){
		return question_id;
	}

	public void setValue(int value){
		this.value = value;
	}

	public int getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"AnswersItem{" + 
			"question_id = '" + question_id + '\'' +
			",value = '" + value + '\'' + 
			"}";
		}
}