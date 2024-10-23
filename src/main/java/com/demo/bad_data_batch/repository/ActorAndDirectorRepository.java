package com.demo.bad_data_batch.repository;

import com.demo.bad_data_batch.model.ActorAndDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorAndDirectorRepository extends JpaRepository<ActorAndDirector, Long> {
    List<ActorAndDirector> findByMovieIdOrderByRole(final long movieId);

    @Query("Select distinct(role) from ActorAndDirector" )
    List<String> findByDistinctRole();
}
