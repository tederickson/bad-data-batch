package com.demo.bad_data_batch.config;

import com.demo.bad_data_batch.dto.ActorAndDirectorCsv;
import com.demo.bad_data_batch.dto.MovieCsv;
import com.demo.bad_data_batch.model.ActorAndDirector;
import com.demo.bad_data_batch.model.Movie;
import com.demo.bad_data_batch.repository.ActorAndDirectorRepository;
import com.demo.bad_data_batch.repository.DuplicateMovieRepository;
import com.demo.bad_data_batch.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final MovieRepository movieRepository;
    private final ActorAndDirectorRepository actorAndDirectorRepository;
    private final DuplicateMovieRepository duplicateMovieRepository;

    @Bean
    public FlatFileItemReader<MovieCsv> movieReader() {
        return new FlatFileItemReaderBuilder<MovieCsv>()
                .name("movieItemReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("data/movies.csv"))
                .delimited()
                .names("id", "title", "year")
                .targetType(MovieCsv.class)
                .build();
    }

    @Bean
    public MovieProcessor movieProcessor() {
        return new MovieProcessor(movieRepository, duplicateMovieRepository);
    }

    @Bean
    public ItemWriter<Movie> movieWriter() {
        return movieRepository::saveAll;
    }

    @Bean
    public FlatFileItemReader<ActorAndDirectorCsv> actorAndDirectorReader() {
        return new FlatFileItemReaderBuilder<ActorAndDirectorCsv>()
                .name("actorAndDirectorItemReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("data/actors_and_directors.csv"))
                .delimited()
                .names("id", "movieId", "name", "role")
                .targetType(ActorAndDirectorCsv.class)
                .build();
    }

    @Bean
    public ActorAndDirectorProcessor actorAndDirectorProcessor() {
        return new ActorAndDirectorProcessor(movieRepository);
    }

    @Bean
    public ItemWriter<ActorAndDirector> actorAndDirectorWriter() {
        return actorAndDirectorRepository::saveAll;
    }

    @Bean
    public Job importMoviesJob(final JobRepository jobRepository,
                               @Qualifier("step1") final Step step1,
                               @Qualifier("step2") final Step step2) {
        return new JobBuilder("importMoviesJob", jobRepository)
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    public Step step1(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<MovieCsv, Movie>chunk(10, transactionManager)
                .reader(movieReader())
                .processor(movieProcessor())
                .writer(movieWriter())
                .build();
    }

    @Bean
    public Step step2(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository)
                .<ActorAndDirectorCsv, ActorAndDirector>chunk(10, transactionManager)
                .reader(actorAndDirectorReader())
                .processor(actorAndDirectorProcessor())
                .writer(actorAndDirectorWriter())
                .build();
    }
}
