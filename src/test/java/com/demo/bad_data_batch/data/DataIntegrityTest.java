package com.demo.bad_data_batch.data;

import com.demo.bad_data_batch.dto.ActorAndDirectorCsv;
import com.demo.bad_data_batch.dto.MovieCsv;
import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DataIntegrityTest {
    private final static String ACTORS_AND_DIRECTORS = "data/actors_and_directors.csv";
    private final static String MOVIES = "data/movies.csv";

    @Test
    void ableToParseActorsAndDirector() throws Exception {
        int validCount = 0;
        int invalidCount = 0;

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(ACTORS_AND_DIRECTORS)) {
            assertNotNull(inputStream);
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                 CSVReader reader = new CSVReader(bufferedReader)) {

                reader.readNext();

                String[] row;
                while ((row = reader.readNext()) != null) {
                    // "id", "movieId", "name", "role"
                    assertThat(row.length, is(4));

                    int column = 0;
                    int id = Integer.parseInt(row[column++]);
                    int movieId = Integer.parseInt(row[column++]);
                    assertThat(movieId, greaterThan(0));

                    String name = row[column++];
                    // a null entry is converted to "NULL"
                    // Some names are blank

                    String role = row[column];
                    assertFalse(StringUtils.isBlank(role));

                    ActorAndDirectorCsv item = new ActorAndDirectorCsv(id, movieId, name, role);

                    if (item.isValid()) {
                        validCount++;
                    }
                    else {
                        invalidCount++;
                    }
                }
            }
        }
        assertThat(invalidCount, is(20));
        assertThat(validCount + invalidCount, is(844_338));
    }

    @Test
    void ableToParseMovies() throws Exception {
        int validCount = 0;
        int invalidCount = 0;

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(MOVIES)) {
            assertNotNull(inputStream);
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                 CSVReader reader = new CSVReader(bufferedReader)) {

                reader.readNext();

                String[] row;
                while ((row = reader.readNext()) != null) {
                    // "id", "title", "year"
                    assertThat(row.length, is(3));

                    int column = 0;
                    int id = Integer.parseInt(row[column++]);
                    assertThat(id, greaterThan(0));

                    String title = row[column++];
                    assertFalse(StringUtils.isBlank(title));

                    String year = row[column];
                    assertFalse(StringUtils.isBlank(year));

                    MovieCsv movieCsv = new MovieCsv(id, title, year);

                    if (movieCsv.isValid()) {
                        validCount++;
                    }
                    else {
                        invalidCount++;
                    }
                }
            }
        }
        assertThat(invalidCount, is(75));
        assertThat(validCount + invalidCount, is(184_784));
    }
}
