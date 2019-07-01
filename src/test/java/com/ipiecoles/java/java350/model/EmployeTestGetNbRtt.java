package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTestGetNbRtt {

    @ParameterizedTest()
    @CsvSource({
            "2019, 8",
            "2021, 10",
            "2032, 11"
    })
    void getNbRtt(int year, int expected) {
        //Given
        Employe e = new Employe();
        e.setSalaire(0.0);
        e.setDateEmbauche(LocalDate.now());

        //When
        Integer res = e.getNbRtt(LocalDate.now());
        //Then
        //Object wish;
        assertEquals(java.util.Optional.of(expected), res);
    }
}