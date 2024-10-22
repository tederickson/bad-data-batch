package com.demo.bad_data_batch.client;

import com.demo.bad_data_batch.domain.ActorAndDirectorDigest;
import com.demo.bad_data_batch.domain.MovieDigest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.RestClient;

import java.util.List;

public class MovieClient {
    private final RestClient restClient;

    public MovieClient(final String server, final int port) {
        restClient = RestClient.builder()
                .baseUrl("http://" + server + ":" + port)
                .build();
    }

    public MovieDigest getMovie(final Long id) {
        return restClient.get()
                .uri("/movie/{id}", id)
                .retrieve()
                .body(MovieDigest.class);
    }

    public Page<MovieDigest> getMoviesByYear(final Integer year, final Pageable pageable) {
        return restClient.get()
                .uri("/movie/years/{year}?pageNumber={pageNumber}&pageSize={pageSize}",
                     year,
                     pageable.getPageNumber(),
                     pageable.getPageSize()
                )
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<MovieDigest> getMoviesByTitle(final String title) {
        return restClient.get()
                .uri("/movie/titles/{title}", title)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<ActorAndDirectorDigest> getCast(final Long movieId) {
        return restClient.get()
                .uri("/cast/movies/{movieId}", movieId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
