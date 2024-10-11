package com.demo.bad_data_batch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {
    @Id
    private Long id;
    private String title;
    private String year;

    public boolean isValid() {
        if (id < 1) {
            return false;
        }
        if (StringUtils.isBlank(title)) {
            log.warn("invalid title [{}]", this);
            return false;
        }
        if ("NULL".equals(title)) {
            log.warn("missing title {}", this);
            return false;
        }
        if ("NULL".equals(year)) {
            log.warn("missing year {}", this);
            return false;
        }
        if (year.length() != 4) {
            log.warn("invalid year {}", this);
            return false;
        }
        return true;
    }

}
