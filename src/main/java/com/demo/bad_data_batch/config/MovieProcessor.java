package com.demo.bad_data_batch.config;

import com.demo.bad_data_batch.dto.MovieCsv;
import com.demo.bad_data_batch.model.DuplicateMovie;
import com.demo.bad_data_batch.model.Movie;
import com.demo.bad_data_batch.repository.DuplicateMovieRepository;
import com.demo.bad_data_batch.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

@RequiredArgsConstructor
public class MovieProcessor implements ItemProcessor<MovieCsv, Movie> {
    final private MovieRepository movieRepository;
    final private DuplicateMovieRepository duplicateMovieRepository;

    @Override
    public Movie process(final MovieCsv movie) {
        if (!movie.isValid()) {
            return null;
        }

        final int year = Integer.parseInt(movie.year());
        final String upperTitle = Movie.buildKey(movie.title());
        List<Movie> movies = movieRepository.findByUpperTitleAndYear(upperTitle, year);

        if (!movies.isEmpty()) {
            if (movies.stream()
                    .map(Movie::getId)
                    .filter(n -> movie.id() == n)
                    .findAny().isEmpty()) {
                Movie original = movies.getFirst();
                DuplicateMovie duplicateMovie = new DuplicateMovie();

                duplicateMovie.setId(movie.id());
                duplicateMovie.setDuplicateTitle(movie.title());

                duplicateMovie.setMovieId(original.getId());
                duplicateMovie.setTitle(original.getTitle());
                duplicateMovie.setYear(original.getYear());

                duplicateMovieRepository.save(duplicateMovie);
            }
            return null;
        }

        return new Movie()
                .setId(movie.id())
                .setUpperTitle(upperTitle)
                .setTitle(movie.title())
                .setYear(year);
    }
}
