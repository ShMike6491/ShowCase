package org.example.showcase.app.di

import org.example.showcase.store.di.storeFeatureModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

object DependencyInjection {

    private val modules = listOf(appModule, storeFeatureModule)

    fun initKoin(vararg platformModules: Module, appDeclaration: KoinAppDeclaration = {}) {
        startKoin {
            appDeclaration()
            modules(
                platformModules
                    .asList()
                    .plus(modules)
            )
        }
    }

    fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
        startKoin {
            appDeclaration()
            modules(modules)
        }
    }
}
