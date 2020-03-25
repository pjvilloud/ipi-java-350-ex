package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

public class EmployeTest {

    @Test
    public void testDateEmbaucheValueSameYars() throws Exception {
        //Given
        Employe employe = new Employe();
        LocalDate dateEmbauche = LocalDate.now().with(firstDayOfYear());
        employe.setDateEmbauche(dateEmbauche);
        //When
        Integer date = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(date).isEqualTo(0);
    }

    @Test
    public void testDateEmbaucheLastYears() throws Exception {
        //Given
        Employe employe = new Employe();
        LocalDate dateEmbauche = LocalDate.now().with(firstDayOfYear()).minusDays(1);
        employe.setDateEmbauche(dateEmbauche);
        //When
        Integer date = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(date).isEqualTo(1);
    }

    @Test
    public void testDateEmbauche() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);
        //When
        Integer date = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(date).isEqualTo(null);
    }

    @Test
    public void testDateEmbaucheSuperieurNow() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusDays(1L));
        //When
        Integer date = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(date).isEqualTo(null);
    }
}
