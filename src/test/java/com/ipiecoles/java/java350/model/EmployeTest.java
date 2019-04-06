package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void getNombreAnneeAncienneteNow(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNminus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(2, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNull(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNplus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "1, 'T12345', 2, 0.5, 600.0",
            "1, 'T12345', 2, 1.0, 1200.0",
            "2, 'T12345', 0, 1.0, 2300.0",
            "2, 'T12345', 1, 1.0, 2400.0",
            "1, 'M12345', 0, 1.0, 1700.0",
            "1, 'M12345', 5, 1.0, 2200.0",
            "2, 'M12345', 0, 1.0, 1700.0",
            "2, 'M12345', 8, 1.0, 2500.0"
    })
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        //Given
        Employe employe = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(primeAnnuelle, prime);

    }

    @Test
    public void getPrimeAnnuelleWithNoMat(){
        //Given
        Employe employe = new Employe("Doe", "John", null, LocalDate.now().minusYears(2), Entreprise.SALAIRE_BASE, 1, 1.0);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(1200.0, prime.doubleValue());

    }

    @Test
    public void getPrimeAnnuelleWithNoPerf(){
        //Given
        Employe employe = new Employe("Doe", "John", "M28394", LocalDate.now().minusYears(5), Entreprise.SALAIRE_BASE, null, 1.0);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(2200.0, prime.doubleValue());

    }

    @Test
    public void testAugmenterSalaire(){
        //Given
        Employe employe = new Employe();

        //Whe
        employe.augmenterSalaire(0.5);
        Double newSal = 2281.83;

        //Then
        Assertions.assertEquals(newSal, employe.getSalaire());
    }

    @Test
    public void testAugmenterSalaireByZero(){
        //Given
        Employe employe = new Employe();

        //When
        employe.augmenterSalaire(0);
        Double newSal = 1521.22;

        //Then
        Assertions.assertEquals(newSal, employe.getSalaire());
    }

    @Test
    public void testAugmenterSalaireByNegation(){
        //Given
        Employe employe = new Employe();

        //When
        employe.augmenterSalaire(-0.5);
        Double newSal = 1521.22;

        //Then
        Assertions.assertEquals(newSal, employe.getSalaire());
    }

    @Test
    public void testAugmenterSalaireNull(){
        //Given
        Employe employe = new Employe();
        employe.setSalaire(null);

        //When
        employe.augmenterSalaire(0.5);

        //Then
        Assertions.assertNull(employe.getSalaire());
    }

    /*
    Nombre de jours dans l'année - Nombre de jours travaillés dans l'année en plein temps
- Nombre de samedi et dimanche dans l'année - Nombre de jours fériés ne tombant pas le week-end
- Nombre de congés payés
     */
    @ParameterizedTest
    @CsvSource({
            "2016, 1.0, 12",
            "2016, 0.5, 6",
            "2019, 1.0, 8",
            "2019, 0.5, 4",
            "2019, -1.0, 0",
            "2021, 1.0, 11",
            "2021, 0.5, 5.5",
            "2022, 1.0, 10",
            "2022, 0.5, 5",
            "2023, 1.0, 8",
            "2023, 0.5, 4",
            "2032, 1.0, 12",
            "2032, 0.5, 6",
            "2028, 1.0, 8",
            "2028, 0.5, 4",
            "2028, 1.5, 12"
    })
    public void testGetNbrRtt(int years, Double tempsPartiel, Double NbRtt){
        //Given
        Employe employe = new Employe();

        //When
        employe.setTempsPartiel(tempsPartiel);

        //Then
        Assertions.assertEquals(NbRtt, employe.getNbRtt(LocalDate.ofYearDay(years, 1)));
    }
}