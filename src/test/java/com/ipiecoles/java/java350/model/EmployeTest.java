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
    void testAugmenterSalaire(){
        //Given
        Double baseSalaire = Entreprise.SALAIRE_BASE;
        Employe employe = new Employe("Doe", "John", "T0001", LocalDate.now().minusYears(1), baseSalaire, 1, 1d);
        Double pourcentage = 50d;

        //When
        employe.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertEquals(2281.83,employe.getSalaire());
    }

    @Test
    void testAugmenterSalaireZeroPercent(){
        //Given
        Double baseSalaire = Entreprise.SALAIRE_BASE;
        Employe employe = new Employe("Doe", "John", "T0001", LocalDate.now().minusYears(1), baseSalaire, 1, 1d);
        Double percentage = 0d;

        //When
        employe.augmenterSalaire(percentage);

        //Then
        Assertions.assertEquals(baseSalaire,employe.getSalaire());
    }

    @Test
    void testAugmenterSalaireNull(){
        //Given
        Double baseSalaire = null;
        Employe employe = new Employe("Doe", "John", "T0001", LocalDate.now().minusYears(1), baseSalaire, 1, 1d);
        Double percentage = 10d;

        //When
        employe.augmenterSalaire(percentage);

        //Then
        Assertions.assertNull(employe.getSalaire());
    }
    @Test
    void testAugmenterSalaireNegative(){
        //Given
        Double baseSalaire = -10d;
        Employe employe = new Employe("Doe", "John", "T0001", LocalDate.now().minusYears(1), baseSalaire, 1, 1d);
        Double percentage = 10d;

        //When
        employe.augmenterSalaire(percentage);

        //Then
        Assertions.assertEquals(baseSalaire + (baseSalaire*percentage/100),employe.getSalaire());
    }

    @Test
    void testAugmenterSalairePoucentageNegative(){
        //Given
        Double baseSalaire = Entreprise.SALAIRE_BASE;
        Employe employe = new Employe("Doe", "John", "T0001", LocalDate.now().minusYears(1), baseSalaire, 1, 1d);
        Double percentage = -10d;

        //When
        employe.augmenterSalaire(percentage);

        //Then
        Assertions.assertEquals(baseSalaire,employe.getSalaire());
    }
    @ParameterizedTest
    @CsvSource({
            "9,1d,2020",
            "11,1d,2021",
            "11,1d,2022",
            "8,1d,2024",

            "5,0.5d,2020",
            "6,0.5d,2021",
            "6,0.5d,2022",
            "4,0.5d,2024",
    })
    @Test
    void testGetNbRtt(Integer expected,Double tempsPartiel, Integer dateYear){

        //Given
        Employe employe = new Employe("Doe", "John", "T0001", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, tempsPartiel);

        //When
        Integer nRTT = employe.getNbRtt(LocalDate.ofYearDay(dateYear,1));

        //Then
        Assertions.assertEquals(nRTT,expected);

    }

}