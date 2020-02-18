package com.nifcompany.testopini2.PostAnswerResponse;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("student")
	private Student student;

	@SerializedName("report")
	private Report report;

	public void setStudent(Student student){
		this.student = student;
	}

	public Student getStudent(){
		return student;
	}

	public void setReport(Report report){
		this.report = report;
	}

	public Report getReport(){
		return report;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"student = '" + student + '\'' + 
			",report = '" + report + '\'' + 
			"}";
		}
}