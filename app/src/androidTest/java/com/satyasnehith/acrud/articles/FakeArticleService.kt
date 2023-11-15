package com.satyasnehith.acrud.articles

import androidx.test.espresso.core.internal.deps.guava.base.Joiner.on
import com.satyasnehith.acrud.data.FakeData
import com.satyasnehith.acud.core.network.api.ArticlesService
import com.satyasnehith.acud.core.network.model.Article
import com.satyasnehith.acud.core.network.model.SuccessRes
import org.mockito.Mock
import org.mockito.Mockito.RETURNS_DEEP_STUBS
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Call
import retrofit2.Response


class FakeArticleService: ArticlesService {
    private val mockArticlesCall: Call<List<Article>> = mock<Call<List<Article>>>(RETURNS_DEEP_STUBS).apply {
        `when`(execute()).thenReturn(Response.success(FakeData.articles))
    }

    private val mockAddArticlesCall: Call<SuccessRes> = mock<Call<SuccessRes>>(RETURNS_DEEP_STUBS).apply {
        `when`(execute()).thenReturn(Response.success(SuccessRes(("Ok"))))
    }

    override fun getAllArticles(): Call<List<Article>> {
        return mockArticlesCall
    }

    override fun addArticle(article: Article): Call<SuccessRes> {
        return mockAddArticlesCall
    }
}

