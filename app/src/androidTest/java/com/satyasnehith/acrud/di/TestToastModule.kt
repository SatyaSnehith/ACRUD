package com.satyasnehith.acrud.di

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