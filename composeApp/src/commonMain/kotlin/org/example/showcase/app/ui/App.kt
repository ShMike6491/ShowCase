package org.example.showcase.app.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.showcase.app.navigation.RootComponent
import org.example.showcase.store.ui.details.DetailScreen
import org.example.showcase.store.ui.feed.FeedScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(component: RootComponent) {
    MaterialTheme {
        val childStack by component.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(fade()) //slide(), etc.
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.Feed -> FeedScreen(instance.component)
                is RootComponent.Child.Details -> DetailScreen(instance.component)
            }
        }
    }
}