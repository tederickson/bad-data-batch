package com.demo.bad_data_batch.dto;

import com.demo.bad_data_batch.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record ActorAndDirectorCsv(long movieId, String name, String role) {
    public boolean isValid() {
        if (movieId < 1) {
            return false;
        }
        if (ValidationUtil.isNullOrBlank(name)) {
            log.warn("invalid name [{}]", this);
            return false;
        }
        if (ValidationUtil.isNullOrBlank(role)) {
            log.warn("missing role {}", this);
            return false;
        }

        return true;
    }
}
