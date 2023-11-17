package com.satyasnehith.acrud.core.model

open class Article(val id: Int, val title: String, val body: String) {
    override fun toString(): String {
        return """[
id: $id
title: $title
body: $body
]""".trimIndent()
    }
}