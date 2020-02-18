package com.nifcompany.testopini2.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelQuestions {

    @SerializedName("question_id")
    @Expose
    private String question_id;

    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("category")
    @Expose
    private String category;

    public ModelQuestions(String question_id, String body, String category) {
        this.question_id = question_id;
        this.body = body;
        this.category = category;
    }

    ArrayList<ModelQuestions> result;

    public ArrayList<ModelQuestions> getResult() {
        return result;
    }

    public void setResult(ArrayList<ModelQuestions> result) {
        this.result = result;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
