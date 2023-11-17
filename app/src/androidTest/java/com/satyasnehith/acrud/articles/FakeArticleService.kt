package com.satyasnehith.acrud.articles

import com.satyasnehith.acrud.core.model.Article
import com.satyasnehith.acrud.data.FakeData
import com.satyasnehith.acrud.core.network.api.ArticlesService
import com.satyasnehith.acrud.core.network.model.NetworkArticle
import com.satyasnehith.acrud.core.network.model.SuccessRes
import com.satyasnehith.acrud.core.network.model.asNetworkArticle
import org.mockito.Mockito.RETURNS_DEEP_STUBS
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Call
import retrofit2.Response

class FakeArticleService: ArticlesService {
    private val mockArticlesCall: Call<List<NetworkArticle>> = mock<Call<List<NetworkArticle>>>(RETURNS_DEEP_STUBS).apply {
        `when`(execute()).thenReturn(Response.success(FakeData.articles.map(Article::asNetworkArticle)))
    }

    private val mockAddArticlesCall: Call<SuccessRes> = mock<Call<SuccessRes>>(RETURNS_DEEP_STUBS).apply {
        `when`(execute()).thenReturn(Response.success(SuccessRes(("Ok"))))
    }

    override fun getAllArticles(): Call<List<NetworkArticle>> {
        return mockArticlesCall
    }

    override fun addArticle(networkArticle: NetworkArticle): Call<SuccessRes> {
        return mockAddArticlesCall
    }

    override fun getArticle(id: Int): Call<NetworkArticle> {
        TODO("Not yet implemented")
    }

    override fun deleteArticle(id: Int): Call<SuccessRes> {
        TODO("Not yet implemented")
    }
}

