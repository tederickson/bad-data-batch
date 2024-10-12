package com.demo.bad_data_batch.domain;

import lombok.Builder;

@Builder
public record MovieDigest(Long id, String title, Integer year) {}
