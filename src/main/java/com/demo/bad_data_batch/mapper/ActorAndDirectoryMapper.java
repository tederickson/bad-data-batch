package com.demo.bad_data_batch.mapper;

import com.demo.bad_data_batch.domain.ActorAndDirectorDigest;
import com.demo.bad_data_batch.model.ActorAndDirector;

public class ActorAndDirectoryMapper {
    public static ActorAndDirectorDigest toRest(final ActorAndDirector movie) {
        return ActorAndDirectorDigest.builder()
                .id(movie.getId())
                .movieId(movie.getMovieId())
                .name(movie.getName())
                .role(movie.getRole())
                .build();
    }
}
