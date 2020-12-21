package com.kish.learn.redisdatastore.kafka

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(private val kafkaTemplate: KafkaTemplate<String, Any>) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun send(message: String) {
        logger.debug("Inside send msg {}",message)
        kafkaTemplate.send("simple-message-topic", message)
        logger.debug(" sent msg {} successfully",message)
    }

}