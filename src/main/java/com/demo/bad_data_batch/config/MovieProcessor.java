package com.demo.bad_data_batch.config;

import com.demo.bad_data_batch.dto.MovieCsv;
import com.demo.bad_data_batch.model.Movie;
import com.demo.bad_data_batch.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MovieProcessor implements ItemProcessor<MovieCsv, Movie> {
    final private MovieRepository movieRepository;

    @Override
    public Movie process(final MovieCsv movie) {
        if (!movie.isValid()) {
            return null;
        }

        final int year = Integer.parseInt(movie.year());
        final String upperTitle = movie.title().toUpperCase().trim();
        List<Movie> movies = movieRepository.findByUpperTitleAndYear(upperTitle, year);

        if (!movies.isEmpty()) {
            log.info("Duplicate movie: {} matches {}", movies.getFirst(), movie);
            return null;
        }

        return new Movie()
                .setId(movie.id())
                .setUpperTitle(upperTitle)
                .setTitle(movie.title())
                .setYear(year);
    }
}
