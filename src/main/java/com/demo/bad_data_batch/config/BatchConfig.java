package com.demo.bad_data_batch.config;

import com.demo.bad_data_batch.model.Movie;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final MovieRepository movieRepository;

    @Bean
    public FlatFileItemReader<Movie> movieReader() {
        return new FlatFileItemReaderBuilder<Movie>()
                .name("movieItemReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("data/movies.csv"))
                .delimited()
                .names("id", "title", "year")
                .targetType(Movie.class)
                .build();
    }

    @Bean
    public MovieProcessor movieProcessor() {
        return new MovieProcessor(movieRepository);
    }

    @Bean
    public ItemWriter<Movie> movieWriter() {
        return movieRepository::saveAll;
    }


    @Bean
    public Job importMoviesJob(final JobRepository jobRepository,
                               final Step step1) {
        return new JobBuilder("importMoviesJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(final JobRepository jobRepository,
                      final PlatformTransactionManager transactionManager,
                      final MovieProcessor processor) {
        return new StepBuilder("step1", jobRepository)
                .<Movie, Movie>chunk(3, transactionManager)
                .reader(movieReader())
                .processor(processor)
                .writer(movieWriter())
                .build();
    }
}
