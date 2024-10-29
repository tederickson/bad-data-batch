package com.demo.bad_data_batch.domain;

import lombok.Data;

import java.util.List;

@Data
public class DuplicateMoviePageDigest {
    private Long count;
    private MovieDigest originalMovie;
    private List<ActorAndDirectorDigest> cast;
    private List<DuplicateMovieDigest> duplicates;
}
