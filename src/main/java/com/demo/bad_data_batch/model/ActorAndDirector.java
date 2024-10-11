package com.demo.bad_data_batch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "ACTOR_AND_DIRECTOR")
public class ActorAndDirector {
    @Id
    private Integer id;

    private int movieId;
    private String name;
    private String role;

    public boolean isValid() {
        if (movieId < 1) {
            return false;
        }
        if (StringUtils.isBlank(name)) {
            log.warn("invalid name [{}]", this);
            return false;
        }
        if (role == null) {
            log.warn("null role [{}]", this);
            return false;
        }

        return true;
    }
}
