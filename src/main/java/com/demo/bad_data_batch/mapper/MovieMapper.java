package com.demo.bad_data_batch.mapper;

import com.demo.bad_data_batch.model.Movie;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MovieMapper {
    public static Movie createMovie(final String line) {
        //        try {
        //            CSVReader reader = new CSVReader(new StringReader(line));
        //            String[] row = reader.readNext();
        //
        //            if (row.length == 3) {
        //                return new Movie(Integer.parseInt(row[0]), row[1], row[2]);
        //            }
        //            log.warn("Unable to create Movie [{}]", line);
        //        } catch (IOException | CsvValidationException | NumberFormatException e) {
        //            log.warn(line, e);
        //        }
        return new Movie(-1L, "", "");
    }
}
