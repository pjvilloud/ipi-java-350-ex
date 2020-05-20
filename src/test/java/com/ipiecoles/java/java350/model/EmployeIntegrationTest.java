package com.ipiecoles.java.java350.model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class EmployeIntegrationTest {
    @Test
    void testGetNbRttEmployeAvecDeuxAnsAnciennete() {
        // Given
        Employe employe = new Employe("nom", "prenom", "matricule", LocalDate.now().minusYears(2), 1500d, 1, 1d);
        LocalDate date = LocalDate.of(2020, 1, 1);

        // When
        Integer nbRtt = employe.getNbRtt();

        // Then
        Assertions.assertEquals(8, nbRtt);
    }
}
