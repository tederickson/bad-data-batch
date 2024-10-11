package com.demo.bad_data_batch.mapper;

import com.demo.bad_data_batch.model.ActorAndDirector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActorAndDirectorMapper {
    public static ActorAndDirector createActorAndDirector(final String line) {
        //        try {
        //            CSVReader reader = new CSVReader(new StringReader(line));
        //            String[] row = reader.readNext();
        //
        //            if (row.length == 3) {
        //                return new ActorAndDirector(Integer.parseInt(row[0]), row[1], row[2]);
        //            }
        //            log.info("Unable to parse [{}]", line);
        //        } catch (IOException | CsvValidationException | NumberFormatException e) {
        //            log.info(line, e);
        //        }
        return new ActorAndDirector(null, -1, "", "");
    }
}
