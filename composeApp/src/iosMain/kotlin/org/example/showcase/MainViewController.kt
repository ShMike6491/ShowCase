package org.example.showcase

import androidx.compose.ui.window.ComposeUIViewController
import org.example.showcase.app.di.DependencyInjection
import org.example.showcase.di.iosModule

fun MainViewController() = ComposeUIViewController(
    configure = { DependencyInjection.initKoin(iosModule) }
) {
    App()
}