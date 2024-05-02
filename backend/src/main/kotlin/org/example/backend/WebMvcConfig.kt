package org.example.backend

import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

class WebMvcConfig: WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/get_repos")
            .allowedOrigins("http://localhost:63342")
        super.addCorsMappings(registry)
    }
}