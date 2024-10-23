package com.demo.bad_data_batch.domain;

public record DuplicateMovieDigest(
        long movieId,
        String title,
        long duplicateId,
        String duplicateTitle,
        int year) {
}
