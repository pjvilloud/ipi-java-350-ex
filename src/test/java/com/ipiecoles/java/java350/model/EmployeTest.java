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

    // Tests méthode augmenterSalaire
    // Cas augmentation de 0%
    @Test
    public void augmenterSalaireDeZero(){
        //Given
        Employe e = new Employe();
        e.setSalaire(1000.0);

        //When
        e.augmenterSalaire(0.0);

        //Then
        Assertions.assertEquals(1000.0, e.getSalaire().doubleValue());
    }

    // Cas augmentation négative (diminution)
    @Test
    public void augmenterSalaireDiminution(){
        //Given
        Employe e = new Employe();
        e.setSalaire(1000.0);

        //When
        e.augmenterSalaire(-0.1);

        //Then
        Assertions.assertEquals(900.0, e.getSalaire().doubleValue());
    }

    // Cas augmentation standard (augmentation réaliste)
    @Test
    public void augmenterSalaire(){
        //Given
        Employe e = new Employe();
        e.setSalaire(1000.0);

        //When
        e.augmenterSalaire(0.1);

        //Then
        Assertions.assertEquals(1100.0, e.getSalaire().doubleValue());
    }

    // Cas augmentation de moins de 100% (limite basse)
    @Test
    public void augmenterSalaireMoinsCent(){
        //Given
        Employe e = new Employe();
        e.setSalaire(1000.0);

        //When
        e.augmenterSalaire(-2.0);

        //Then
        Assertions.assertEquals(0.0, e.getSalaire().doubleValue());
    }

    // Cas salaire à 0
    @Test
    public void augmenterSalaireZero(){
        //Given
        Employe e = new Employe();
        e.setSalaire(0.0);

        //When
        e.augmenterSalaire(0.1);

        //Then
        Assertions.assertEquals(0.0, e.getSalaire().doubleValue());
    }

    // Cas salaire négatif
    @Test
    public void augmenterSalaireNeg(){
        //Given
        Employe e = new Employe();
        e.setSalaire(-1000.0);

        //When
        e.augmenterSalaire(0.1);

        //Then
        Assertions.assertEquals(0.0, e.getSalaire().doubleValue());
    }


    // Tests fonction getNbRtt, en utilisant comme condition des temps pleins et partiels, et des années bissextiles ou non
    @ParameterizedTest
    @CsvSource({
            "2016, 0.5, 0",
            "2019, 1.0, 8",
            "2021, 0.5, 5",
            "2022, 1.0, 10",
            "2032, 0.5, 6"
    })
    public void getNbRtt(Integer annee, Double tempsPartiel, Integer nbRttAttendu){
        //Given
        Employe employe = new Employe();
        employe.setTempsPartiel(tempsPartiel);
        LocalDate date = LocalDate.of(annee, 1, 1);

        //When
        Integer nbRttCalcule = employe.getNbRtt(date);

        //Then
        Assertions.assertEquals(nbRttAttendu, nbRttCalcule);

    }
}