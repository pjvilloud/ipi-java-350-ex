package com.ipiecoles.java.java350.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    /**
     * Pour pouvoir gérer le résultat du salaire avec des chiffres apres la virgule,
     * je devrai importer une bibliothèque me permettant d'utiliser un arrondi plus
     * précis que Math.round(...). Afin de gagner du temps je vais volontairement gérer
     * ces cas en arrondi entier.
     *
     * @throws Exception
     */
    @Test
    public void augmenterSalaire() throws Exception {
        // Given
        Employe e = new Employe();
        e.setSalaire(1521.22d);

        // When
        e.augmenterSalaire(11);

        // Then
        Assertions.assertEquals(Math.round(1688.5542d), e.getSalaire());
    }

    @Test
    public void augmenterSalaire_MauvaiseValeur() throws Exception {
        // Given
        Employe e = new Employe();
        e.setSalaire(1500d);

        // When / Then
        Throwable exception = org.assertj.core.api.Assertions.catchThrowable(() ->
                e.augmenterSalaire(-20));
        Assertions.assertEquals("Une mauvaise valeur a été saisie.", exception.getMessage());
        Assertions.assertEquals(1500d, e.getSalaire());
    }

    @ParameterizedTest
    @CsvSource({
            "2, 'T12345', 1, 1, '2044-11-15', 8", //Vendredi
            "2, 'T12345', 1, 0.5, '2044-11-15', 4", //Vendredi
            "2, 'T12345', 1, 1, '2019-11-15', 8", //Mardi
            "2, 'T12345', 1, 0.5, '2019-11-15', 4", //Mardi
            "2, 'T12345', 1, 1, '2021-11-15', 9", //Vendredi
            "2, 'T12345', 1, 0.5, '2021-11-15', 5", //Vendredi
            "2, 'T12345', 1, 1, '2022-11-15', 10", //Samedi
            "2, 'T12345', 1, 0.5, '2022-11-15', 5", //Samedi
            "2, 'T12345', 1, 1, '2032-11-15', 11", //Jeudi
            "2, 'T12345', 1, 0.5, '2032-11-15', 6", //Jeudi
    })
    public void getNbRtt(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, LocalDate d, Integer nbRttFinal) {
        // Given
        Employe e = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);
        // When
        Integer nbRtt = e.getNbRtt(d);
        // Then
        Assertions.assertEquals(nbRttFinal, nbRtt);
    }

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

}