package com.example.healthmonitoring_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform