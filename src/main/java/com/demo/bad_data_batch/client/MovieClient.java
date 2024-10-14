package com.demo.bad_data_batch.client;

import com.demo.bad_data_batch.domain.ActorAndDirectorDigest;
import com.demo.bad_data_batch.domain.MovieDigest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.RestClient;

import java.util.List;

public class MovieClient {
    private final RestClient restClient;

    public MovieClient(String server, int port) {
        restClient = RestClient.builder()
                .baseUrl("http://" + server + ":" + port)
                .build();
    }

    public MovieDigest getMovie(Long id) {
        return restClient.get()
                .uri("/movie/{id}", id)
                .retrieve()
                .body(MovieDigest.class);
    }

    public List<MovieDigest> getMoviesByYear(Integer year, Pageable pageable) {
        return restClient.get()
                .uri("/movie/years/{year}?page={pageNumber}&size={pageSize}",
                     year,
                     pageable.getPageNumber(),
                     pageable.getPageSize()
                )
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<MovieDigest> getMoviesByTitle(String title) {
        return restClient.get()
                .uri("/movie/titles/{title}", title)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<ActorAndDirectorDigest> getCast(Long movieId) {
        return restClient.get()
                .uri("/cast/movies/{movieId}", movieId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
