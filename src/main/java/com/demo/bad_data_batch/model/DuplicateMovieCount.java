package com.demo.bad_data_batch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DuplicateMovieCount implements IDuplicateMovieCount {
    private Long movieKey;
    private Long movieCount;
}
