package com.jeommechu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class JeommechuApplication

fun main(args: Array<String>) {
	runApplication<JeommechuApplication>(*args)
}
