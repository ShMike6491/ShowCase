package org.example.showcase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform