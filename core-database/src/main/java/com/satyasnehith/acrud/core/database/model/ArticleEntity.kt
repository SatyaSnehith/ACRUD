package com.satyasnehith.acrud.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.satyasnehith.acrud.core.model.Article

@Entity(tableName = "articles")
class ArticleEntity (
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "body")
    var body: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

fun ArticleEntity.asExternalModule() = Article(
    id = id,
    title = title,
    body = body
)

fun List<ArticleEntity>.asExternalModule() = map(ArticleEntity::asExternalModule)

fun Article.asArticleEntity() = ArticleEntity(
    title = title,
    body = body
).apply {
    this@asArticleEntity.id?.let {
        id = it
    }
}