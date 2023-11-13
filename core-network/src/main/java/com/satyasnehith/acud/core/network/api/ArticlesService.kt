package com.satyasnehith.acud.core.network.api

import com.satyasnehith.acud.core.network.model.Article
import com.satyasnehith.acud.core.network.model.SuccessRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ArticlesService {
    @GET("articles")
    fun getAllArticles(): Call<List<Article>>

    @POST("add")
    fun addArticle(@Body article: Article): Call<SuccessRes>
}