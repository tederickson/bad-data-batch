package com.demo.bad_data_batch.controller;

import com.demo.bad_data_batch.domain.MovieDigest;
import com.demo.bad_data_batch.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Movie Query API")
@RequestMapping("movie")
public class MovieController {
    private final MovieService movieService;

    @Operation(summary = "Get Movie")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Get Movie"), //
            @ApiResponse(responseCode = "404", description = "Movie id not found")})
    @GetMapping(value = "/{id}", produces = "application/json")
    public MovieDigest getMovie(@PathVariable("id") Long id) {
        return movieService.getMovie(id);
    }

    @Operation(summary = "Get all movies for one year")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Get Movies")})
    @GetMapping(value = "/years/{year}", produces = "application/json")
    public PagedModel<MovieDigest> getMovies(@PathVariable("year") Integer year,
                                             Pageable pageable) {
        return movieService.getMovies(year, pageable);
    }
}