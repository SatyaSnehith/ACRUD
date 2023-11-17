package com.satyasnehith.acrud.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.satyasnehith.acrud.core.database.model.ArticleEntity

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<ArticleEntity>

    @Query("SELECT * FROM articles WHERE id IN (:id)")
    suspend fun getArticle(id: Int): ArticleEntity?

    @Update
    suspend fun updateArticle(articleEntity: ArticleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg articleEntity: ArticleEntity)

    @Query("DELETE FROM articles WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM articles")
    suspend fun deleteAll()
}