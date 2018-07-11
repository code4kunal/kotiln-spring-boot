package com.example.kotlinstarterdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinStarterDemoApplication

fun main(args: Array<String>) {
    runApplication<KotlinStarterDemoApplication>(*args)
}
