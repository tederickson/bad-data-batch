package com.demo.bad_data_batch.repository;

import com.demo.bad_data_batch.model.DuplicateMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuplicateMovieRepository extends JpaRepository<DuplicateMovie, Long> {
}
