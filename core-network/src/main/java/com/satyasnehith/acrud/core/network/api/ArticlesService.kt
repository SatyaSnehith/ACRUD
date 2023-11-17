package com.satyasnehith.acrud.core.network.api

import com.satyasnehith.acrud.core.network.model.NetworkArticle
import com.satyasnehith.acrud.core.network.model.SuccessRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ArticlesService {
    @GET("articles")
    fun getAllArticles(): Call<List<NetworkArticle>>

    @POST("add")
    fun addArticle(@Body networkArticle: NetworkArticle): Call<SuccessRes>

    @GET("view")
    fun getArticle(@Query("id") id: Int): Call<NetworkArticle>

    @GET("delete")
    fun deleteArticle(@Query("id") id: Int): Call<SuccessRes>
}