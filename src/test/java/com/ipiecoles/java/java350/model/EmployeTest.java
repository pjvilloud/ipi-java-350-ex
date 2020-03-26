package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

public class EmployeTest {

    @Test
    public void testDateEmbaucheValueSameYars() {
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
    public void testDateEmbaucheLastYears() {
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
        Assertions.assertThat(date).isEqualTo(0);
    }

    @ParameterizedTest()
    @CsvSource({
            "1,'M00000',0,1.0,1700.0",
            "1,'M00000',0,0.5,850.0",
            "1,'M00000',1,1.0,1800.0",
            "1000,'M00000',0,1.0,1700.0",
            "1,'E00000',0,1.0,1000.0",
            "1,'E00000',0,0.5,500",
            "1,'E00000',2,1.0,1200.0",
            "2,'E00000',0,1.0,2300.0",
            "2,'E00000',2,0.5,1250.0",
            "2,'M00000',2,0.5,950.0",
    })
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle) {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(performance);
        employe.setMatricule(matricule);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbYearsAnciennete));
        employe.setTempsPartiel(tempsPartiel);

        //When
        Double primeA = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(primeA).isEqualTo(primeAnnuelle);
    }
}
