package com.satyasnehith.acrud.core.data

import com.satyasnehith.acrud.core.database.dao.ArticleDao
import com.satyasnehith.acrud.core.database.model.asArticleEntity
import com.satyasnehith.acrud.core.database.model.asExternalModule
import com.satyasnehith.acrud.core.model.Article
import com.satyasnehith.acrud.core.network.api.ArticlesService
import timber.log.Timber

class ArticlesRepository(
    private val articleDao: ArticleDao,
    private val articlesService: ArticlesService
) {
    suspend fun getAllArticles(): Result<List<Article>> {
        return getResult {
            articleDao.getAllArticles().asExternalModule().onEach {
                Timber.d(it.toString())
            }
        }
    }

    suspend fun getArticle(id: Int): Result<Article> {
        return getResult {
            articleDao.getArticle(id)!!.asExternalModule().apply {
                Timber.d(toString())
            }
        }
    }

    suspend fun addArticle(article: Article): Result<Unit> {
        return getResult {
            articleDao.insert(article.asArticleEntity())
        }
    }

    suspend fun deleteArticle(id: Int): Result<Unit> {
        Timber.d("DELETE ARTICLE :$id")
        return getResult {
            articleDao.delete(id)
        }
    }

    suspend fun updateArticle(article: Article): Result<Unit> {
        Timber.tag("Update").d(article.toString())
        return getResult {
            articleDao.updateArticle(article.asArticleEntity())
        }
    }

     private suspend fun <T> getResult(block: suspend () -> T): Result<T> {
        return try {
            Result.success(block())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}