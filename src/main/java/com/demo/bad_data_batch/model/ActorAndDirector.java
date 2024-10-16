package com.demo.bad_data_batch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "ACTOR_AND_DIRECTOR")
public class ActorAndDirector {
    @Id
    private long id;
    private long movieId;
    private String name;
    private String role;
}
