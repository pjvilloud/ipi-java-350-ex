package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    //cas 1 : DateNow = 05/01/2022 | dateEmbauche = 05/01/2023 | nbAnneeAnciennete expected = 0
    @Test
    public void testGetSenorityWithFutureHiringDate(){
        LocalDate dateEmbauche = LocalDate.now().plusYears(3);

        Employe employe = new Employe("Doe", "John", "T12345", dateEmbauche, Entreprise.SALAIRE_BASE, 4, 1d);

        Integer nbYearOfSenority = employe.getNombreAnneeAnciennete();

        Assertions.assertThat(nbYearOfSenority).isZero();
    }

    //cas 2 : DateNow = 05/01/2022 | dateEmbauche = 05/01/2022 | nbAnneeAnciennete expected = 0
    @Test
    public void testGetSenorityWithHiringDate(){
        LocalDate dateEmbauche = LocalDate.now();

        Employe employe = new Employe("Doe", "John", "T12345", dateEmbauche, Entreprise.SALAIRE_BASE, 4, 1d);

        Integer nbYearOfSenority = employe.getNombreAnneeAnciennete();

        Assertions.assertThat(nbYearOfSenority).isZero();
    }

    //cas 3 : DateNow = 05/01/2022 | dateEmbauche = 05/01/2021 | nbAnneeAnciennete expected = 1
    @Test
    public void testGetSenorityWithPastHiringDate(){
        LocalDate dateEmbauche = LocalDate.now().minusYears(1);

        Employe employe = new Employe("Doe", "John", "T12345", dateEmbauche, Entreprise.SALAIRE_BASE, 4, 1d);

        Integer nbYearOfSenority = employe.getNombreAnneeAnciennete();

        Assertions.assertThat(nbYearOfSenority).isEqualTo(1);
    }

    //cas 4: DateNow = now | dateEmbauche = null | nbAnneeAnciennete expected = 0
    @Test
    public void testGetSenorityWithNullHiringDate(){
        LocalDate dateEmbauche = null;

        Employe employe = new Employe("Doe", "John", "T12345", dateEmbauche, Entreprise.SALAIRE_BASE, 4, 1d);

        Integer nbYearOfSenority = employe.getNombreAnneeAnciennete();

        Assertions.assertThat(nbYearOfSenority).isZero();
    }

    @Test
    public void testGetAnnualBonus(){
        LocalDate dateEmbauche = null;

        Employe employe = new Employe("Doe", "John", "T12345", dateEmbauche, Entreprise.SALAIRE_BASE, 4, 1d);

        Double d = employe.getPrimeAnnuelle();

        //Bonus + performances bonus + senority bonus
        //1000 + 0 + 0 => 1000
        Assertions.assertThat(d).isEqualTo(4300d);
    }

    @Test
    public void testAugmenterSalairePayRise(){
        //augmenterSalaire able us to do a pay rise to any employee
        //those pay rise are made in percentage

        // Given
        Employe employe = new Employe();
        employe.setSalaire(2000d);

        // When
        employe.augmenterSalaire(20);

        // Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(2400d);
    }

    @Test
    public void testAugmenterSalairePayDecrease(){
        // Given
        Employe employe = new Employe();
        employe.setSalaire(2000d);

        // When
        employe.augmenterSalaire(-20);

        // Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1600d);
    }

    @Test
    public void testAugmenterSalairePayRiseToANegativeSalary(){
        // Given
        Employe employe = new Employe();
        employe.setSalaire(0d);

        // When
        employe.augmenterSalaire(50);

        // Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(0d);
    }

    @ParameterizedTest(name = "Le {0}, pour un temps partiel de {1}, le nombre de RTT sera de => {2}")
    @CsvSource({
            "2019-01-10, 1.0, 9",
            "2021-02-21, 0.5, 6",
            "2022-03-31, 1.0, 11",
            "2032-04-02, 1.0, 10",
            "2035-05-19, 1.0, 10",
    })
    void testNbRTTManyDatePartTime(LocalDate dateAnnee, Double tempsPartiel, Integer result) {
        // Given
        Employe employe = new Employe();
        employe.setTempsPartiel(tempsPartiel);
        // When
        Integer nbRtt = employe.getNbRtt(dateAnnee);

        // Then
        Assertions.assertThat(nbRtt).isEqualTo(result);
    }
}
