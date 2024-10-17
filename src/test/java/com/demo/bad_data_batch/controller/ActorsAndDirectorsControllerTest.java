package com.demo.bad_data_batch.controller;

import com.demo.bad_data_batch.model.ActorAndDirector;
import com.demo.bad_data_batch.repository.ActorAndDirectorRepository;
import com.demo.bad_data_batch.service.ActorsAndDirectorsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActorsAndDirectorsControllerTest {
    public static final long MOVIE_ID = 123L;
    private ActorsAndDirectorsController actorsAndDirectorsController;
    private ActorAndDirectorRepository actorAndDirectorRepository;

    @BeforeEach
    void setUp() {
        actorAndDirectorRepository = mock(ActorAndDirectorRepository.class);
        ActorsAndDirectorsService actorsAndDirectorsService = new ActorsAndDirectorsService(actorAndDirectorRepository);
        actorsAndDirectorsController = new ActorsAndDirectorsController(actorsAndDirectorsService);
    }

    @Test
    void getCast() {
        assertThat(actorsAndDirectorsController.getCast(MOVIE_ID), hasSize(0));
    }

    @Test
    void getCast_withResults() {
        when(actorAndDirectorRepository.findByMovieIdOrderByRole(MOVIE_ID))
                .thenReturn(List.of(new ActorAndDirector()
                                            .setName("name")
                                            .setRole("role")
                                            .setId(1111L)
                                            .setMovieId(MOVIE_ID)
                ));
        assertThat(actorsAndDirectorsController.getCast(MOVIE_ID), hasSize(1));
    }
}