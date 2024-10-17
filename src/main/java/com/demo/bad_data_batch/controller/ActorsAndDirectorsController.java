package com.demo.bad_data_batch.controller;

import com.demo.bad_data_batch.domain.ActorAndDirectorDigest;
import com.demo.bad_data_batch.service.ActorsAndDirectorsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Actors and Directors Query API")
@RequestMapping("movie")
public class ActorsAndDirectorsController {
    private final ActorsAndDirectorsService actorsAndDirectorsService;

    @Operation(summary = "Get Movie Cast")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Get Movie Cast")})
    @GetMapping(value = "/movies/{movieId}", produces = "application/json")
    public List<ActorAndDirectorDigest> getCast(@PathVariable("movieId") Long movieId) {
        return actorsAndDirectorsService.getMovieCast(movieId);
    }
}
