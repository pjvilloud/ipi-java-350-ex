package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {
    //Date d'embauche dans le futur dans un an => 0
    @Test
    public void testNbAnneesAncienneteDateEmbaucheFuture(){
        //Given
        LocalDate dateEmbauche = LocalDate.now().plusYears(1);
        Employe e = new Employe("Doe", "John", "T12345", dateEmbauche, 2000d, 1, 1.0);
        //When
        Integer anciennete = e.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(anciennete).isEqualTo(0);
    }

    //Date d'embauche dans le passé il y a deux ans => 2
    @Test
    public void testNbAnneesAncienneteDateEmbauchePasse(){
        //Given
        LocalDate dateEmbauche = LocalDate.now().minusYears(2);
        Employe e = new Employe("Doe", "John", "T12345", dateEmbauche, 2000d, 1, 1.0);
        //When
        Integer anciennete = e.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(anciennete).isEqualTo(2);
    }

    //Date d'embauche en année en cours => 0
    @Test
    public void testNbAnneesAncienneteDateEmbaucheMaintenant(){
        //Given
        LocalDate dateEmbauche = LocalDate.now();
        Employe e = new Employe("Doe", "John", "T12345", dateEmbauche, 2000d, 1, 1.0);
        //When
        Integer anciennete = e.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(anciennete).isEqualTo(0);
    }

    //Date d'embauche null => 0
    @Test
    public void testNbAnneesAncienneteDateEmbaucheNull(){
        //Given
        LocalDate dateEmbauche = null;
        Employe e = new Employe("Doe", "John", "T12345", dateEmbauche, 2000d, 1, 1.0);
        //When
        Integer anciennete = e.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(anciennete).isEqualTo(0);
    }



    //Date d'embauche dans le futur (2023) => 0
    //Date d'embauche dans le passé (2020) => 2
    //Date d'embauche en année en cours (2022) => 0
    //Date d'embauche null => 0

    @ParameterizedTest(name = "matricule {0}, ancienneté {1}, taux activité {2}, performance {3} => prime {4}")
    @CsvSource({
            "'M12345', 0, 1.0, 1, 1700.0", //(1000 * 1.7 + 0) * 1 = 1700
            "'M12345', 2, 1.0, 1, 1900.0", //(1000 * 1.7 + 2 * 100) * 1 = 1900
    })
//Paramètres : matricule, ancienneté, taux d'activité, performance, prime
    public void testGetPrimeAnnuelle(
            String matricule,
            Integer nbAnneesAnciennete,
            Double tauxActivite,
            Integer performance,
            Double primeCalculee) {
        //Given
        //Manager : matricule M12345 embauché cette année à plein temps
        Employe employe = new Employe("Doe", "John",
                matricule, LocalDate.now().minusYears(nbAnneesAnciennete), 2000d, performance, tauxActivite);
        //When
        Double prime = employe.getPrimeAnnuelle();
        //Then
        Assertions.assertThat(prime).isEqualTo(primeCalculee);
    }




}