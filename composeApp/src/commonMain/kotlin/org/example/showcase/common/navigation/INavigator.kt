package org.example.showcase.common.navigation

interface INavigator {

    fun navigateTo(route: NavRoute)
    fun navigateUp()
}