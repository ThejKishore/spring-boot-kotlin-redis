package com.kish.learn.redisdatastore.model

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.index.Indexed

@RedisHash("ExternalSystems")
data class ExternalSystems(
        val id:Long,
        @Id
        val key:String,
        @Indexed
        val app:String,
        val incomingRequest:Long,
        val successCount:Long,
        val failureCount:Long,
        var ring:Int=5,
        var status: SystemStatus=SystemStatus.DOWN
)

enum class SystemStatus{
        UP,AMBER,DOWN
}

