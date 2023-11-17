package com.satyasnehith.acrud.core.database.di

import android.content.Context
import androidx.room.Room
import com.satyasnehith.acrud.core.database.Database
import com.satyasnehith.acrud.core.database.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java, "articles-database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesArticlesDao(database: Database): ArticleDao {
        return database.articleDao()
    }
}