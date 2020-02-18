package com.nifcompany.testopini2.PostAnswer;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PostAnswer{

	@SerializedName("answers")
	private List<AnswersItem> answers;

	@SerializedName("student_id")
	private int student_id;

	public void setAnswers(List<AnswersItem> answers){
		this.answers = answers;
	}

	public List<AnswersItem> getAnswers(){
		return answers;
	}

	public void setStudentId(int student_id){
		this.student_id = student_id;
	}

	public int getStudentId(){
		return student_id;
	}

	@Override
 	public String toString(){
		return 
			"PostAnswer{" + 
			"answers = '" + answers + '\'' + 
			",student_id = '" + student_id + '\'' +
			"}";
		}
}