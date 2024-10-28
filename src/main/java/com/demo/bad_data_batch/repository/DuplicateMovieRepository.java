package com.demo.bad_data_batch.repository;

import com.demo.bad_data_batch.model.DuplicateMovie;
import com.demo.bad_data_batch.model.IDuplicateMovieCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DuplicateMovieRepository extends JpaRepository<DuplicateMovie, Long>,
        PagingAndSortingRepository<DuplicateMovie, Long> {

    @Query("""
            Select dupe from DuplicateMovie dupe
            Where dupe.title != dupe.duplicateTitle
            Order by dupe.title
            """)
    Page<DuplicateMovie> findMismatchedTitles(final Pageable pageable);

    @Query("""
            SELECT d.movieId as movieKey, count(*) as movieCount
            FROM  DuplicateMovie AS d
            GROUP BY d.movieId
            ORDER BY count(*) DESC, d.movieId
            """)
    Page<IDuplicateMovieCount> findDuplicateTitles(final Pageable pageable);

    List<DuplicateMovie> findByMovieId(final Long movieId);
}
