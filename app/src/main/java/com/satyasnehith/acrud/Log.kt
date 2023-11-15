package com.satyasnehith.acrud

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

@Composable
fun LogLifecycle(name: String) {
    val lifecycleObserver = LocalLifecycleOwner.current
    DisposableEffect(lifecycleObserver) {
        val observer = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                Timber.tag("LifeCycle").d("$name onCreate")
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                Timber.tag("LifeCycle").d("$name onDestroy")
            }

            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                Timber.tag("LifeCycle").d("$name onPause")
            }

            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                Timber.tag("LifeCycle").d("$name onResume")
            }

            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                Timber.tag("LifeCycle").d("$name onStart")
            }

            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                Timber.tag("LifeCycle").d("$name onStop")
            }
        }
        lifecycleObserver.lifecycle.addObserver(observer)
        onDispose {
            lifecycleObserver.lifecycle.removeObserver(observer)
        }
    }

}