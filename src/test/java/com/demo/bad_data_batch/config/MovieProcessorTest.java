package com.demo.bad_data_batch.config;

import com.demo.bad_data_batch.dto.MovieCsv;
import com.demo.bad_data_batch.model.Movie;
import com.demo.bad_data_batch.repository.DuplicateMovieRepository;
import com.demo.bad_data_batch.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieProcessorTest {
    private final long id = 123L;
    private final MovieCsv movieCsv = new MovieCsv(id, "title", "2001");
    private final int year = Integer.parseInt(movieCsv.year());
    private final String upperTitle = Movie.buildKey(movieCsv.title());

    private MovieProcessor movieProcessor;
    private MovieRepository movieRepository;
    private DuplicateMovieRepository duplicateMovieRepository;


    @BeforeEach
    void setUp() {
        movieRepository = mock(MovieRepository.class);
        duplicateMovieRepository = mock(DuplicateMovieRepository.class);
        movieProcessor = new MovieProcessor(movieRepository, duplicateMovieRepository);
    }

    @Test
    void process_emptyList() {
        Movie movie = movieProcessor.process(movieCsv);

        assertNotNull(movie);
        assertThat(movie.getId(), is(id));
        verify(duplicateMovieRepository, times(0)).save(any());
    }

    @Test
    void process_foundDupe() {
        when(movieRepository.findByUpperTitleAndYear(upperTitle, year))
                .thenReturn(List.of(new Movie().setId(14L)
                                            .setUpperTitle(upperTitle)
                                            .setYear(year)));
        Movie movie = movieProcessor.process(movieCsv);

        assertNull(movie);
        verify(duplicateMovieRepository).save(any());
    }

    @Test
    void process_reran() {
        when(movieRepository.findByUpperTitleAndYear(upperTitle, year))
                .thenReturn(List.of(new Movie().setId(id)
                                            .setUpperTitle(upperTitle)
                                            .setYear(year)));
        Movie movie = movieProcessor.process(movieCsv);

        assertNull(movie);
        verify(duplicateMovieRepository, times(0)).save(any());
    }
}