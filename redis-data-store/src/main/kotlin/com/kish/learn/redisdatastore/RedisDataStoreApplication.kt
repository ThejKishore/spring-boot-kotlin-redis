package com.kish.learn.redisdatastore

import com.kish.learn.redisdatastore.model.Movie
import com.kish.learn.redisdatastore.repo.MovieRepo
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
@EnableRedisRepositories
class RedisDataStoreApplication{

	@Bean
	fun init(movieRepo: MovieRepo):CommandLineRunner{
		return CommandLineRunner {
			println(" works fine")
			val movie=movieRepo.save(Movie("Kotlin","learning",2020))
			println("data saved movie :   ${movie.id.orEmpty()}")
			movieRepo.findById(movie.id.orEmpty())
		}
	}
}

fun main(args: Array<String>) {
	runApplication<RedisDataStoreApplication>(*args)
}
