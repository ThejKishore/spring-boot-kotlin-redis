package com.kish.learn.redisdatastore.resource

import com.kish.learn.redisdatastore.model.Movie
import com.kish.learn.redisdatastore.repo.MovieRepo
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.PastOrPresent

@RestController
@RequestMapping("/movies")
class MovieController (val movieRepo: MovieRepo) {

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    private fun createMovie(@Valid movie: MovieDto): Movie = movieRepo.save(Movie(movie.name,movie.genre,movie.year))

    @GetMapping(value = ["/{id}"])
    @ResponseStatus(HttpStatus.OK)
    private fun getMovieById(@PathVariable id: String): Movie = movieRepo.findById(id).get()

    @PutMapping(value = ["/{id}"])
    @ResponseStatus(HttpStatus.OK)
    private fun updateMovie(@PathVariable id: String, @Validated movie: MovieDto): Movie {
        val movie: Movie = movieRepo.findById(id).orElseThrow { RuntimeException("Unable to find movie for $id id") }
        val updatedMovie = movie.copy(name = movie.name.orEmpty(), genre = movie.genre.orEmpty(), year = movie.year)
        updatedMovie.id = movie.id
        return movieRepo.save(updatedMovie)
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private fun getMovies(): List<Movie> = movieRepo.findAll().toList()

    @DeleteMapping(value = ["/{id}"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private fun deleteMovie(id: String) = movieRepo.deleteById(id)

    data class MovieDto(
            @get:NotBlank val name: String,
            @get:NotBlank val genre: String,
            @get:Min(value = 1900) @PastOrPresent val year: Int
    )
}