package com.demo.bad_data_batch.service;

import com.demo.bad_data_batch.domain.MovieDigest;
import com.demo.bad_data_batch.exception.InvalidRequestException;
import com.demo.bad_data_batch.exception.NotFoundException;
import com.demo.bad_data_batch.mapper.ModelMapper;
import com.demo.bad_data_batch.model.Movie;
import com.demo.bad_data_batch.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieDigest getMovie(final Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException("Movie " + id));
        return ModelMapper.toRest(movie);
    }

    public Page<MovieDigest> getMoviesByYear(final Integer year, final Pageable pageable) {
        if (year < 1000) {
            throw new InvalidRequestException("Invalid year " + year);
        }
        Page<Movie> page = movieRepository.findByYearOrderByTitle(year, pageable);

        long totalElements = page.getTotalElements();
        List<MovieDigest> content = page.getContent().stream()
                .map(ModelMapper::toRest)
                .toList();

        return new PageImpl<>(content, pageable, totalElements);
    }

    public List<MovieDigest> getMoviesByTitle(final String title) {
        final String titleKey = Movie.buildKey(title);
        if (titleKey.isEmpty()) {throw new InvalidRequestException("Invalid title [" + title + "]");}

        return movieRepository.findByUpperTitle(titleKey).stream()
                .map(ModelMapper::toRest)
                .toList();
    }
}
