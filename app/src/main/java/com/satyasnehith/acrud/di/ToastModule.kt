package com.satyasnehith.acrud.di

import android.content.Context
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToastModule {
    @Singleton
    @Provides
    fun providesToaster(
        @ApplicationContext context: Context,
    ): Toaster = Toaster { text -> Toast.makeText(context, text, Toast.LENGTH_SHORT).show() }
}

fun interface Toaster {
    fun showToast(text: String)
}