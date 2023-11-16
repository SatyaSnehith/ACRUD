package com.satyasnehith.acud.core.network.api

import com.satyasnehith.acud.core.network.model.Article
import com.satyasnehith.acud.core.network.model.SuccessRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ArticlesService {
    @GET("articles")
    fun getAllArticles(): Call<List<Article>>

    @POST("add")
    fun addArticle(@Body article: Article): Call<SuccessRes>

    @GET("view")
    fun getArticle(@Query("id") id: Int): Call<Article>

    @GET("delete")
    fun deleteArticle(@Query("id") id: Int): Call<SuccessRes>
}