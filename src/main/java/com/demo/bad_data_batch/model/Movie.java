package com.demo.bad_data_batch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Accessors(chain = true)
@Data
@Entity
public class Movie {
    @Id
    private Long id;
    private String upperTitle;
    private String title;
    private Integer year;

    static public String buildKey(final String text) {
        if (text == null) {
            return "";
        }

        char[] characters = text.toCharArray();
        char[] letters = new char[characters.length];

        int index = 0;
        for (char character : characters) {
            if (!Character.isWhitespace(character)) {
                letters[index++] = Character.toUpperCase(character);
            }
        }

        return String.copyValueOf(letters, 0, index);
    }
}
