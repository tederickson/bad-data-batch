package com.demo.bad_data_batch.config;

import com.demo.bad_data_batch.dto.ActorAndDirectorCsv;
import com.demo.bad_data_batch.model.ActorAndDirector;
import com.demo.bad_data_batch.repository.DuplicateMovieRepository;
import com.demo.bad_data_batch.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
@RequiredArgsConstructor
public class ActorAndDirectorProcessor implements ItemProcessor<ActorAndDirectorCsv, ActorAndDirector> {
    final private MovieRepository movieRepository;
    final private DuplicateMovieRepository duplicateMovieRepository;

    @Override
    public ActorAndDirector process(final ActorAndDirectorCsv item) {
        if (!item.isValid()) {
            return null;
        }
        if (movieRepository.findById(item.movieId()).isEmpty()
                && duplicateMovieRepository.findById(item.movieId()).isEmpty()) {
                log.error("Invalid movie Id: {}", item);
                return null;
            }

        return new ActorAndDirector()
                .setId(item.id())
                .setMovieId(item.movieId())
                .setName(item.name())
                .setRole(item.role());
    }
}
