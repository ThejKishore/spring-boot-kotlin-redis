package com.kish.learn.redisdatastore.repo

import com.kish.learn.redisdatastore.model.ExternalSystems
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ExternalSystemRepo : CrudRepository<ExternalSystems,String> {
}