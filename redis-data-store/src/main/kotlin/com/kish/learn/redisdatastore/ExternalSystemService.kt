package com.kish.learn.redisdatastore

import com.kish.learn.redisdatastore.model.ExternalSystems
import com.kish.learn.redisdatastore.model.SystemStatus
import com.kish.learn.redisdatastore.repo.ExternalSystemRepo
import org.springframework.stereotype.Service

@Service
class ExternalSystemService(val externalSystemRepo:ExternalSystemRepo) {

    fun update(externalSystems: ExternalSystems){
        val externalSystemsRetrieve = externalSystemRepo.findById(externalSystems.key).get()
        val ringCount = calculateRingCount(externalSystemsRetrieve, externalSystems)
        val status: SystemStatus = systemStatus(ringCount)
        val updateExternalSystems = externalSystemsRetrieve.copy(incomingRequest = externalSystemsRetrieve.incomingRequest + 1,
                ring = ringCount,
                successCount = externalSystemsRetrieve.successCount+externalSystems.successCount,
                failureCount = externalSystemsRetrieve.failureCount+externalSystems.failureCount,
                status =  status
        )
        externalSystemRepo.save(updateExternalSystems);
    }

    private fun calculateRingCount(externalSystemsRetrieve: ExternalSystems, externalSystems: ExternalSystems): Int {
        var ringCount = externalSystemsRetrieve.ring
        if (ringCount < 5 && externalSystems.successCount > 0)
            ++ringCount

        if (externalSystems.failureCount > 0 && ringCount >= 1)
            --ringCount
        return ringCount
    }

    private fun systemStatus(ringCount: Int): SystemStatus {
        var status: SystemStatus
        if (ringCount < 3) {
            status = SystemStatus.DOWN
        } else if (ringCount == 3) {
            status = SystemStatus.AMBER
        } else {
            status = SystemStatus.UP
        }
        return status
    }

    fun save(externalSystems: ExternalSystems) = externalSystemRepo.save(externalSystems)
    fun findById(key:String) = externalSystemRepo.findById(key)
    fun deleteAll() =externalSystemRepo.deleteAll()
}