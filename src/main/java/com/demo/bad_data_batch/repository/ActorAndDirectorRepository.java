package com.demo.bad_data_batch.repository;

import com.demo.bad_data_batch.model.ActorAndDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorAndDirectorRepository extends JpaRepository<ActorAndDirector, Long> {
}
