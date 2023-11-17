package com.satyasnehith.acrud.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ErrorRes (
    @Json(name = "error")
    val error: String
)