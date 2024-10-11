package com.demo.bad_data_batch.config;

import com.demo.bad_data_batch.model.Movie;
import com.demo.bad_data_batch.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MovieProcessor implements ItemProcessor<Movie, Movie> {
    final private MovieRepository movieRepository;

    @Override
    public Movie process(final Movie movie) {
        if (!movie.isValid()) {
            return null;
        }

        List<Movie> movies = movieRepository.findByTitleAndYear(movie.getTitle(), movie.getYear());
        if (!movies.isEmpty()) {
            log.info("Duplicate movie: {} matches {}", movies.getFirst(), movie);
            return null;
        }

        return movie;
    }
}
