package com.demo.bad_data_batch.mapper;

import com.demo.bad_data_batch.domain.MovieDigest;
import com.demo.bad_data_batch.model.Movie;

public class ModelMapper {
    public static MovieDigest toRest(final Movie movie) {
        return new MovieDigest(movie.getId(), movie.getTitle(), movie.getYear());
    }
}
