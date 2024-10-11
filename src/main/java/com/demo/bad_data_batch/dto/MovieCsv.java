package com.demo.bad_data_batch.dto;

import com.demo.bad_data_batch.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record MovieCsv(long id, String title, String year) {
    public boolean isValid() {
        if (id < 1) {
            return false;
        }
        if (ValidationUtil.isNullOrBlank(title)) {
            log.warn("invalid title [{}]", this);
            return false;
        }
        if (ValidationUtil.isNullOrBlank(year)) {
            log.warn("invalid year {}", this);
            return false;
        }
        return true;
    }

}
