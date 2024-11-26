package com.momokddi.common

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.util.pattern.PathPatternParser

@Configuration
class ApiPrefixConfig : WebMvcConfigurer {

    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        configurer.setPatternParser(PathPatternParser())
        configurer.addPathPrefix("/api") { true }  // 모든 컨트롤러에 /api 프리픽스를 추가
    }
}
