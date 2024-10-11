package com.demo.bad_data_batch.repository;

import com.demo.bad_data_batch.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleAndYear(final String title, final String year);
}
