package com.satyasnehith.acrud.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.satyasnehith.acrud.core.database.dao.ArticleDao
import com.satyasnehith.acrud.core.database.model.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = true
)
abstract class Database: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}