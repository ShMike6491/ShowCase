package org.example.showcase.di

import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

val iosModule = module {
    single { Darwin.create() }
}