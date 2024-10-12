package com.demo.bad_data_batch.service;

import com.demo.bad_data_batch.domain.MovieDigest;
import com.demo.bad_data_batch.exception.InvalidRequestException;
import com.demo.bad_data_batch.exception.NotFoundException;
import com.demo.bad_data_batch.mapper.ModelMapper;
import com.demo.bad_data_batch.model.Movie;
import com.demo.bad_data_batch.repository.ActorAndDirectorRepository;
import com.demo.bad_data_batch.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorAndDirectorRepository actorAndDirectorRepository;

    public MovieDigest getMovie(final Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException("Movie " + id));
        return ModelMapper.toRest(movie);
    }

    public List<MovieDigest> getMovies(Integer year) {
        if (year < 1000) {
            throw new InvalidRequestException("Invalid year " + year);
        }
        return movieRepository.findByYear(year).stream()
                .map(ModelMapper::toRest)
                .toList();
    }
}
