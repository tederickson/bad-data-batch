package com.demo.bad_data_batch.repository;

import com.demo.bad_data_batch.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> , PagingAndSortingRepository<Movie, Long> {
    List<Movie> findByUpperTitleAndYear(final String upperTitle, final int year);

    List<Movie> findByUpperTitle(final String upperTitle);

    Page<Movie> findByYear(final int year, final Pageable pageable);
}
