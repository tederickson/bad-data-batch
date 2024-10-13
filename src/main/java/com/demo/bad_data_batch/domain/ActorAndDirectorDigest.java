package com.demo.bad_data_batch.domain;

import lombok.Builder;

@Builder
public record ActorAndDirectorDigest(long id, long movieId, String name, String role) {
}
