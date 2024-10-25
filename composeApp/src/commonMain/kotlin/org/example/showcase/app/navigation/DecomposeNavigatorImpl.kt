package org.example.showcase.app.navigation

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import org.example.showcase.common.navigation.INavigator
import org.example.showcase.common.navigation.NavRoute

class DecomposeNavigatorImpl(
    private val navigation: StackNavigation<NavRoute>
) : INavigator {

    override fun navigateTo(route: NavRoute) {
        navigation.pushNew(route)
    }

    override fun navigateUp() {
        navigation.pop()
    }
}