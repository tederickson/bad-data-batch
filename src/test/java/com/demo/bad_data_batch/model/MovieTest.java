package com.demo.bad_data_batch.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTest {

    @ParameterizedTest
    @ValueSource(strings = {"If It's Tuesday, This Must Be Belgium",
            " If  It's  Tuesday, This Must Be  Belgium ",
            "If It's TUESDAY, This must Be Belgium"})
    void buildKey(final String text) {
        assertEquals("IFIT'STUESDAY,THISMUSTBEBELGIUM", Movie.buildKey(text));
    }

    @Test
    void verifySpanish() {
        assertEquals("¡AMÍLALEGIÓN!", Movie.buildKey(" ¡A mí   la Legión! "));
    }
}