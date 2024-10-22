package com.demo.bad_data_batch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class DuplicateMovie {
    @Id
    private long id;
    private long movieId;
}
