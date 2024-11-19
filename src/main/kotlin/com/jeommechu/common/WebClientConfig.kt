package com.jeommechu.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory



@Configuration
class WebClientConfig {
    @Bean
    fun webClient(builder: WebClient.Builder): WebClient {
        val factory = DefaultUriBuilderFactory()
        factory.encodingMode = DefaultUriBuilderFactory.EncodingMode.NONE

        return builder
            .defaultHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
            .uriBuilderFactory(factory)
            .build()
    }
}