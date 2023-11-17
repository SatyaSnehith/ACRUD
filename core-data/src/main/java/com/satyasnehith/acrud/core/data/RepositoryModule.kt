package com.satyasnehith.acrud.core.data

import com.satyasnehith.acrud.core.database.dao.ArticleDao
import com.satyasnehith.acrud.core.network.api.ArticlesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providesArticlesRepository(
        articleDao: ArticleDao,
        articlesService: ArticlesService
    ): ArticlesRepository {
        return ArticlesRepository(articleDao, articlesService)
    }
}
