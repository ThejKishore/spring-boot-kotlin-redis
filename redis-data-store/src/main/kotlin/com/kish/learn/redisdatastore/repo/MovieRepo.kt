package com.kish.learn.redisdatastore.repo

import com.kish.learn.redisdatastore.model.Movie
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepo : CrudRepository<Movie,String> {
}