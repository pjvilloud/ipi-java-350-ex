package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {
    @Test
    public void testGetNombreAnneeAncienneteNow() {
        //Given
        Employe employe = new Employe();
        LocalDate dateEmbauche = LocalDate.now();
        employe.setDateEmbauche(dateEmbauche);

        //When
        Integer nbAnnee = employe.getNombreAnneeAnciennete();

        //Then
        //nbAnnee = 0
        Assertions.assertThat(nbAnnee).isEqualTo(0);
    }

    @Test
    public void testGetNombreAnneeAncienneteNull() {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        //When
        Integer nbAnnee = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnee).isEqualTo(0);
    }

    @Test
    public void testGetNombreAnneeAncienneteMoins2() {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2));

        //When
        Integer nbAnnee = e.getNombreAnneeAnciennete();

        //Then
        //nbAnnee = 0
        Assertions.assertThat(nbAnnee).isEqualTo(2);
    }


    @Test
    public void testGetNombreAnneeAnciennetePlus2() {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2));

        //When
        Integer nbAnnee = e.getNombreAnneeAnciennete();

        //Then
        //nbAnnee = 0
        Assertions.assertThat(nbAnnee).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'M12345', 0, 1.0, 1700.0",
            "1, 'M12345', 1, 1.0, 1800.0",
            "1, 'T12345', 2, 1.0, 1200.0",
            "1, 'T12345', 2, 0.5, 600.0",
            "2, 'T12345', 0, 1.0, 2300.0",
            "2, 'T12345', 3, 1.0, 2600.0"
    })
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        //Given
        Employe employe = new Employe();
        employe.setPerformance(performance);
        employe.setMatricule(matricule);
        employe.setTempsPartiel(tempsPartiel);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbYearsAnciennete));

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(prime).isEqualTo(primeAnnuelle);

    }
}
