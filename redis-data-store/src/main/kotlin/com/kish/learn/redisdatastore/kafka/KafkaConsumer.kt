package com.kish.learn.redisdatastore.kafka


import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {

    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["simple-message-topic"], groupId = "simple-kotlin-consumer")
    fun processMessage(message: ConsumerRecord<String,String>) {
        logger.info("got message.key : {} , message.value {} ", message.key()  ,message.value())
    }
}