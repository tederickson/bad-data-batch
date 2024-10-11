package com.demo.bad_data_batch.config;

import com.demo.bad_data_batch.domain.ActorAndDirectorCsv;
import com.demo.bad_data_batch.model.ActorAndDirector;
import com.demo.bad_data_batch.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@RequiredArgsConstructor
public class ActorAndDirectorProcessor implements ItemProcessor<ActorAndDirectorCsv, ActorAndDirector> {
    final private MovieRepository movieRepository;

    @Override
    public ActorAndDirector process(final ActorAndDirectorCsv item) {
        if (!item.isValid()) {
            return null;
        }
        if (movieRepository.findById(item.movieId()).isEmpty()) {
            // log.info("Missing movie: {} ", item);  Too many log messages
            return null;
        }

        return new ActorAndDirector()
                .setMovieId(item.movieId())
                .setName(item.name())
                .setRole(item.role());
    }
}
