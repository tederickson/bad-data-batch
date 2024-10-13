package com.demo.bad_data_batch.service;

import com.demo.bad_data_batch.domain.ActorAndDirectorDigest;
import com.demo.bad_data_batch.mapper.ActorAndDirectoryMapper;
import com.demo.bad_data_batch.repository.ActorAndDirectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActorsAndDirectorsService {
    private final ActorAndDirectorRepository actorAndDirectorRepository;

    public List<ActorAndDirectorDigest> getMovieCast(final Long movieId) {
        return actorAndDirectorRepository.findByMovieIdOrderByRole(movieId).stream()
                .map(ActorAndDirectoryMapper::toRest)
                .toList();
    }
}
