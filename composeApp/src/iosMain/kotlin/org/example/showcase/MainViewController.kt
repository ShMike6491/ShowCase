package org.example.showcase

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.example.showcase.app.di.DependencyInjection
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.example.showcase.app.ui.App
import org.example.showcase.di.iosModule

fun MainViewController() = ComposeUIViewController(
    configure = { DependencyInjection.initKoin(iosModule) }
) {
    val root = remember {
        RootComponent(DefaultComponentContext(LifecycleRegistry()))
    }

    App(root)
}