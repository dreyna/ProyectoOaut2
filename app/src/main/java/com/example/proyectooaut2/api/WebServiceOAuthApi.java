package com.example.proyectooaut2.api;


import com.example.proyectooaut2.model.Token;
import com.example.proyectooaut2.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WebServiceOAuthApi {

    @GET("/api/users")
    Call<List<User>> obtenerUsuarios();

    @POST("/api/create_user")
    Call<Void> crearUsuario(@Body User user);

    @FormUrlEncoded
    @POST("/oauth/token")
    Call<Token> obtenerToken(
            @Header("Authorization") String authorization,
            @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grantType
    );

    @FormUrlEncoded
    @POST("/oauth/token")
    Call<Token> obtenerTokenconRefreshToken(
            @Header("Authorization") String authorization,
            @Field("refresh_token") String refreshToken,
            @Field("grant_type") String grantType
    );


}
