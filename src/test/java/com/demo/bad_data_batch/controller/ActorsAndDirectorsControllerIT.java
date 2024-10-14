package com.demo.bad_data_batch.controller;

import com.demo.bad_data_batch.client.MovieClient;
import com.demo.bad_data_batch.domain.ActorAndDirectorDigest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActorsAndDirectorsControllerIT {

    private static final long TIME_MACHINE = 283166L;

    @LocalServerPort
    private int port;

    private MovieClient client;

    @BeforeEach
    void setUp() {
        client = new MovieClient("localhost", port);
    }

    @Test
    void getCast() {
        List<ActorAndDirectorDigest> cast = client.getCast(TIME_MACHINE);
        assertThat(cast, hasSize(8));

        List<String> roles = cast.stream().map(ActorAndDirectorDigest::role).distinct().toList();
        assertThat(roles, hasSize(6));
    }

    @Test
    void getCastWithInvalidMovie() {
        assertThat(client.getCast(-1L), hasSize(0));
    }
}