package com.satyasnehith.acrud.core.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.satyasnehith.acrud.core.database.dao.ArticleDao
import com.satyasnehith.acrud.core.database.model.asArticleEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import com.google.common.truth.Truth.assertThat
import com.satyasnehith.acrud.core.database.model.ArticleEntity
import com.satyasnehith.acrud.core.model.Article
import kotlinx.coroutines.test.runTest

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var articleDao: ArticleDao
    private lateinit var db: Database

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            Database::class.java
        ).build()
        articleDao = db.articleDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun addArticleAndReadInList() = runTest {
        val article = FakeData.articles[0].asArticleEntity()
        articleDao.insert(article)
        val dbArticle = articleDao.getAllArticles().first()
        assertArticlesAreSame(article, dbArticle)
    }

    @Test
    @Throws(Exception::class)
    fun addArticlesAndReadInList() = runTest {
        val allArticles = FakeData.articles.map(Article::asArticleEntity).toTypedArray()
        articleDao.insert(*allArticles)
        val dbAllArticles = articleDao.getAllArticles()
        assertThat(allArticles.size).isEqualTo(dbAllArticles.size)
        for (i in allArticles.indices)
            assertThat(dbAllArticles.find {
                val article = allArticles[i]
                it.title == article.title && it.body == article.body
            }).isNotNull()
    }

    @Test
    @Throws(Exception::class)
    fun updateArticleAndReadInList() = runTest {
        val allArticles = FakeData.articles.map(Article::asArticleEntity).toTypedArray()
        articleDao.insert(*allArticles)
        val dbArticle = articleDao.getAllArticles().random()
        dbArticle.title = "updated title"
        dbArticle.body = "updated body"
        articleDao.updateArticle(dbArticle)
        val updatedArticle = articleDao.getArticle(dbArticle.id)
        assertThat(updatedArticle).isNotNull()
        assertArticlesAreSame(dbArticle, updatedArticle!!)
    }

    @Test
    @Throws(Exception::class)
    fun deleteArticleAndReadInList() = runTest {
        val allArticles = FakeData.articles.map(Article::asArticleEntity).toTypedArray()
        articleDao.insert(*allArticles)
        val dbArticle = articleDao.getAllArticles().random()
        articleDao.delete(dbArticle.id)
        assertThat(articleDao.getArticle(dbArticle.id)).isNull()
    }

    private fun logArticle(article: ArticleEntity) {
        Log.d("logArticle: ", "id: ${article.id}, title: ${article.title}, body: ${article.body}")
    }

    private fun assertArticlesAreSame(article1: ArticleEntity, article2: ArticleEntity) {
        assertThat(article1.title).isEqualTo(article2.title)
        assertThat(article1.body).isEqualTo(article2.body)

    }
}