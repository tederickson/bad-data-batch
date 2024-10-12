package com.demo.bad_data_batch.mapper;

import com.demo.bad_data_batch.domain.MovieDigest;
import com.demo.bad_data_batch.model.Movie;

public class ModelMapper {
    public static MovieDigest toRest(final Movie movie) {
        return MovieDigest.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .year(movie.getYear())
                .build();
    }
}
