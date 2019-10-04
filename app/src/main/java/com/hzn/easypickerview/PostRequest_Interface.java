package com.hzn.easypickerview;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostRequest_Interface {
    @POST("live?access_key=1a7f7e4a6c9fcab8b5640ed0fcf2e384")
    Call<Translation> getCall();
}
