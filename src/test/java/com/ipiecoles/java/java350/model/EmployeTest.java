package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class EmployeTest {

    @Test
    void testNombreAnneeAncienneteDateEmbaucheNull() {
        //Given
        Employe employe = new Employe();
//        LocalDate dateEmbauche = LocalDate.of(2017, 3, 14);
        employe.setDateEmbauche(null);

        //When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @Test
    void testNombreAnneeAncienneteDateEmbaucheMinusThreeYears() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(3));

        //When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(employe.getNombreAnneeAnciennete()).isEqualTo(3);
    }
    @Test
    void testNombreAnneeAncienneteDateEmbauchePlusThreeYears() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(3));

        //When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(employe.getNombreAnneeAnciennete()).isEqualTo(0);
    }
}
