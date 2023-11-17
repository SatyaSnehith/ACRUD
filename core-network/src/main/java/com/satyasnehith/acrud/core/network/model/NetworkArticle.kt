package com.satyasnehith.acrud.core.network.model

import com.satyasnehith.acrud.core.model.Article
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class NetworkArticle (
    @Json(name = "id")
    val id: Int?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "body")
    val body: String?,
)

fun NetworkArticle.asExternalModule() = Article(
    id = id ?: -1,
    title = title.orEmpty(),
    body = body.orEmpty()
)

fun Article.asNetworkArticle() = NetworkArticle(
    id = id,
    title = title,
    body = body
)