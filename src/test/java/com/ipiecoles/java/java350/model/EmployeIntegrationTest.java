package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

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
        Assertions.assertThat(nbRtt).isEqualTo(8);
    }
}
