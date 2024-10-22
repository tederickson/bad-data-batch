package com.demo.bad_data_batch.controller;

import com.demo.bad_data_batch.domain.MovieDigest;
import com.demo.bad_data_batch.exception.InvalidRequestException;
import com.demo.bad_data_batch.exception.NotFoundException;
import com.demo.bad_data_batch.model.Movie;
import com.demo.bad_data_batch.repository.MovieRepository;
import com.demo.bad_data_batch.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MovieControllerTest {
    private MovieController movieController;
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        movieRepository = mock(MovieRepository.class);
        MovieService movieService = new MovieService(movieRepository);
        movieController = new MovieController(movieService);
    }

    @Test
    void getMovie_notFound() {
        assertThrows(NotFoundException.class, () -> movieController.getMovie(123L));
    }

    @Test
    void getMovie() {
        Long movieId = 123L;
        Movie movie = new Movie().setId(movieId);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        MovieDigest movieDigest = movieController.getMovie(movieId);
        assertNotNull(movieDigest);
        assertThat(movieDigest.id(), is(movieId));
    }

    @Test
    void getMoviesByYear() {
        when(movieRepository.findByYearOrderByTitle(anyInt(), any()))
                .thenReturn(new PageImpl<>(List.of(),
                                           PageRequest.of(3, 2),
                                           0));
        assertThat(movieController.getMoviesByYear(2000, 3, 4).getContent(), hasSize(0));
    }

    @Test
    void getMoviesByYear_withInvalidYear() {
        assertThrows(InvalidRequestException.class, () -> movieController.getMoviesByYear(300, 3, 4));
    }

    @Test
    void getMoviesByTitle() {
        assertThat(movieController.getMoviesByTitle("Bob"), hasSize(0));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void getMoviesByTitle_withInvalidTitle(final String title) {
        assertThrows(InvalidRequestException.class, () -> movieController.getMoviesByTitle(title));
    }
}