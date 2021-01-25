package com.kish.learn.redisdatastore

import com.kish.learn.redisdatastore.model.ExternalSystems
import com.kish.learn.redisdatastore.model.SystemStatus
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
	fun init(movieRepo: MovieRepo,externalSystemService: ExternalSystemService):CommandLineRunner{
		return CommandLineRunner {
			println(" works fine")
			///
			externalSystemService.deleteAll()
			externalSystemService.save(ExternalSystems(1,"server1","xxx",0,0,0,5,SystemStatus.UP))
			externalSystemService.save(ExternalSystems(2,"server2","xxx",0,0,0, 5,SystemStatus.UP))
			externalSystemService.save(ExternalSystems(3,"server3","xxx",0,0,0, 5,SystemStatus.UP))
			externalSystemService.save(ExternalSystems(4,"server4","xxx",0,0,0, 5,SystemStatus.UP))

			val externalSystems =externalSystemService.findById("server1").get()
			println(" before Updating "+externalSystems)
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,0,1))
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,0,1))
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,0,1))
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,0,1))
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,0,1))
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,0,1))

			println("After few failures "+externalSystemService.findById("server1").get())
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,1,0))
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,1,0))
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,1,0))
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,1,0))
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,1,0))
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,1,0))
			println("After few sucesss "+externalSystemService.findById("server1").get())
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,0,1))
			externalSystemService.update(ExternalSystems(1,"server1","xxx",1,0,1))

			println("After few failures "+externalSystemService.findById("server1").get())

		}
	}
}

fun main(args: Array<String>) {
	runApplication<RedisDataStoreApplication>(*args)
}
