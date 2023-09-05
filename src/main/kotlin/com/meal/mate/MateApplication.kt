package com.meal.mate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class MateApplication

fun main(args: Array<String>) {
	runApplication<MateApplication>(*args)
}
