package com.demo.bad_data_batch.mapper;

import com.demo.bad_data_batch.domain.DuplicateMovieDigest;
import com.demo.bad_data_batch.model.DuplicateMovie;

public class DuplicateModelMapper {
    public static DuplicateMovieDigest toRest(final DuplicateMovie movie) {
        return new DuplicateMovieDigest(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getId(),
                movie.getDuplicateTitle(),
                movie.getYear());
    }
}
