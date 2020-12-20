package com.kish.learn.redisdatastore.repo

import com.kish.learn.redisdatastore.model.Actor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ActorRepo : CrudRepository<Actor,String> {
}