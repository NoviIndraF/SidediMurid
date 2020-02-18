package com.nifcompany.testopini2;

import com.nifcompany.testopini2.GetQuestions.CallQuestion;
import com.nifcompany.testopini2.PostAnswer.PostAnswer;
import com.nifcompany.testopini2.PostAnswerResponse.PostAnswerResponse;
import com.nifcompany.testopini2.PostLoginCode.PostLoginCode;
import com.nifcompany.testopini2.PostStudentProfile.PostStudentProfile;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService{

    @GET("questions")
    Call<CallQuestion> ambilQuestions();

    @POST("class/join")
    @FormUrlEncoded
    Call<PostLoginCode> studentCode(
            @Field("code") String code
    );

    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"
    })

    @POST("student")
    @FormUrlEncoded
    Call<PostStudentProfile> studentProfil(
            @Field("name") String name,
            @Field("NISN") String NISN,
            @Field("gender") String gender,
            @Field("religion") String religion,
            @Field("age") Integer age,
            @Field("class_id") Integer class_id
    );

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("answer")
    Call<PostAnswerResponse> studentAnswer(
            @Body PostAnswer answer
    );

}
