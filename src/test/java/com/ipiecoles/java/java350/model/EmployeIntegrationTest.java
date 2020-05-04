package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeIntegrationTest {

    @Test
    void testGetNbRttEmploye_2AnsAnciennete_Succes(){
        // 0 - Arrange
        Employe employe = new Employe("nom", "prenom", "matricule", LocalDate.now().minusYears(2), 1500d, 1, 1d);
        LocalDate localDate = LocalDate.of(2020, 01, 01);

        // 1 - Act
        Integer nbRtt = employe.getNbRtt();

        // 2 - Assert
        Assertions.assertThat(nbRtt).isEqualTo(8);
    }
}
