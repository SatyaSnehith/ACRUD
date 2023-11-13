package com.satyasnehith.acud.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SuccessRes (
    @Json(name = "message")
    val message: String
)