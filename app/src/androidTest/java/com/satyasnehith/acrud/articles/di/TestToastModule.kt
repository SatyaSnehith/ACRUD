package com.satyasnehith.acrud.articles.di

import com.satyasnehith.acrud.di.ToastModule
import com.satyasnehith.acrud.di.Toaster
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ToastModule::class],
)
object OverrideToastModule {
    @Singleton
    @Provides
    fun providesToaster(): Toaster = FakeToaster
}

object FakeToaster : Toaster {
    val toasts = mutableListOf<String> ()
    override fun showToast(text: String) {
        toasts.add(text)
    }
}