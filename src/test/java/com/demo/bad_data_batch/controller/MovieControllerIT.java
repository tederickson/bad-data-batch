package com.demo.bad_data_batch.controller;

import com.demo.bad_data_batch.client.MovieClient;
import com.demo.bad_data_batch.domain.MovieDigest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieControllerIT {

    @LocalServerPort
    private int port;

    private MovieClient client;

    @BeforeEach
    void setUp() {
        client = new MovieClient("localhost", port);
    }

    @Test
    void getMovie() {
        MovieDigest movieDigest = client.getMovie(1L);

        assertThat(movieDigest.id(), is(1L));
        assertThat(movieDigest.year(), is(2010));
        assertThat(movieDigest.title(), is("Stonehenge Apocalypse"));
    }

    @Test
    void getMoviesByYear() {
        Pageable pageable = PageRequest.of(7, 21);
        var movies = client.getMoviesByYear(2000, pageable);

        assertThat(movies, hasSize(21));
        assertThat(movies.stream().map(MovieDigest::year).distinct().toList(), hasSize(1));
    }

    @Test
    void getMoviesByTitle() {
        var movies = client.getMoviesByTitle("avatar");
        assertThat(movies, hasSize(2));
        assertThat(movies.stream().map(MovieDigest::year).toList(), containsInAnyOrder(2009, 2006));
    }
}