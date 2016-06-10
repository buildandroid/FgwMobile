package com.example.fgwoa.fgwmobile;



import config.Result;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by wangjingyuan on 16/6/3.
 */
public interface RestApi {
    @FormUrlEncoded
    @POST
    Call<Result> sysLogin(@Url String url, @Field("authToken") String params);

    @FormUrlEncoded
    @POST
    Call<Result> sysLogout(@Url String url, @Field("token") String params);

    @FormUrlEncoded
    @POST
    Call<Result> gwQuery(@Url String url, @Field("token") String token, @Field("category") String category,
                         @Field("status") String status, @Field("pageNum") String num,
                         @Field("pageSize") String size, @Field("keyword") String keyword
    );

    @FormUrlEncoded
    @POST
    Call<Result> gwView(@Url String url, @Field("token") String token, @Field("barcode") String barcode);

    @FormUrlEncoded
    @POST
    Call<Result> gwAttachDown(@Url String url, @Field("token") String token, @Field("ROWGUID") String rowguid);
}
