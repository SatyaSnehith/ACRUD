package com.satyasnehith.acrud.data

import androidx.compose.runtime.toMutableStateList
import com.satyasnehith.acrud.core.database.FakeData

object FakeData {
    val articles = FakeData.articles.toMutableStateList()
}

