package com.kish.learn.redisdatastore.resource


import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.kish.learn.redisdatastore.model.Actor
import com.kish.learn.redisdatastore.model.Movie
import com.kish.learn.redisdatastore.repo.ActorRepo
import com.kish.learn.redisdatastore.repo.MovieRepo
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Past

@RestController
@RequestMapping("/actors")
class ActorController(val actorRepo: ActorRepo,val movieRepo: MovieRepo) {

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    private fun createActor(@Validated actor: ActorDto): Actor = actorRepo.save(Actor(actor.firstName,actor.firstName,actor.birthDate))

    @GetMapping(value = ["/{id}"])
    @ResponseStatus(HttpStatus.OK)
    private fun getActorById(@PathVariable id: String): Actor = actorRepo.findById(id).get()

    @PutMapping(value = ["/{id}"])
    @ResponseStatus(HttpStatus.OK)
    private fun updateActor(@PathVariable id: String, @Validated actor: ActorDto): Actor {
        val actor1 = actorRepo.findById(id).get();
        val actor2=actor1.copy(firstName = actor.firstName.orEmpty(),lastName = actor.lastName.orEmpty())
        return actorRepo.save(actor2)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private fun getActors(): List<Actor> = actorRepo.findAll().toList()

    @DeleteMapping(value = ["/{id}"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private fun deleteActor(id: String) = actorRepo.deleteById(id)

    @PatchMapping(value = ["/{actorId}/link/{movieId}"])
    @ResponseStatus(HttpStatus.OK)
    private fun addActorToMovie(@PathVariable actorId: String, @PathVariable movieId: String): Movie {
        val movie = movieRepo.findById(movieId).get()
        val actor= actorRepo.findById(actorId).get()
        (movie.actors as ArrayList).add(actor)
        return movieRepo.save(movie)
    }

    data class ActorDto(
            @get:NotBlank val firstName: String,
            @get:NotBlank val lastName: String,
            @field:DateTimeFormat(pattern = "yyyy-MM-dd")
            @field:JsonDeserialize(using = LocalDateDeserializer::class)
            @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
            @get:Past val birthDate: LocalDate
    )
}