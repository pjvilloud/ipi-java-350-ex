package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test //Junit 4 : org.junit.Test, Junit 5 : org.junit.jupiter.api.Test
    public void testGetNombreAnneeAncienneteDtEmbaucheNull(){

        //Given = Initialisation des données d'entrée);
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When = Exécution de la méthode à tester
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheTodayPlus2y(){

        //Given = Initialisation des données d'entrée);
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));


        //When = Exécution de la méthode à tester
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheTodayMinus3y(){

        //Given = Initialisation des données d'entrée);
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(3));

        //When = Exécution de la méthode à tester
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(3);
    }

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheEqualsToday(){

        //Given = Initialisation des données d'entrée);
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When = Exécution de la méthode à tester
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @ParameterizedTest(name = "Employe matricule {0}, {1} années d ancienneté, {2}, {3} gagnera une prime de {4}")
    @CsvSource({
        "T12345, 0, 1.0, 1, 1000",
        "T12345, 0, 0.5, 1, 500"
    })
    public void testGetPrimeAnnuelleNull(String matricule, Integer nbAnneeAnciennete, Double tempsPartiel, Integer performance, Double primeFinale){

        //Given = Initialisation des données d'entrée);
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setPerformance(performance);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneeAnciennete));
        employe.setTempsPartiel(tempsPartiel);

        //When = Exécution de la méthode à tester
        Double prime = employe.getPrimeAnnuelle();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(prime).isEqualTo(primeFinale);
    }

    @Test
    public void testAugmentationSalaire10() throws Exception {

        //Given
        Employe employe = new Employe();
        employe.setSalaire(1000d);

        //When
        employe.augmenterSalaire(10);

        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1100d);

    }

    @Test
    public void testAugmentationSalaire0() throws Exception {

        //Given
        Employe employe = new Employe();
        employe.setSalaire(1000d);

        //When
        employe.augmenterSalaire(0);

        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1000d);

    }

    @Test
    public void testAugmentationSalaireValeurNegative(){

        //Given
        Employe employe = new Employe();
        employe.setSalaire(1000d);

        //When
        Throwable exception = Assertions.catchThrowable(() ->
                employe.augmenterSalaire(-20));

        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1000d);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Diminution de salaire impossible");
    }

    @ParameterizedTest(name = "L employé de matricule {0}, travaillant au taux {2} aura  {3} jours de RTT cette année")
    @CsvSource({
            "T12345, 1.0, 10",
            "T12345, 0.5, 5"

    })
    public void testGetNbrRTTSansDate(String matricule, Double tempsPartiel, Integer nbRTT) {

        //given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setPerformance(Entreprise.PERFORMANCE_BASE);
        employe.setTempsPartiel(tempsPartiel);

        //when
        Integer NombreJourRTT = employe.getNbRtt();

        //then
        Assertions.assertThat(NombreJourRTT).isEqualTo(nbRTT);
    }


    @ParameterizedTest(name = "L employé de matricule {0}, travaillant au taux {2} aura  {3} jours de RTT cette année")
    @CsvSource({
            "2019-01-21, T12345, 1.0, 8",
            "2020-05-16, T12345, 0.5, 5",
            "2021-03-11, M19587, 1.0, 10",
            "2022-07-02, C54879, 0.5, 5",
            "2032-12-02, T19875, 1.0, 11",
            "2044-10-10, C85166, 0.5, 5"
    })
    public void testGetNbrRTTLeapYear(LocalDate date, String matricule, Double tempsPartiel, Integer nbRTT) {

        LocalDate localDate = date;

        //given
        Employe employe = new Employe();
            employe.setMatricule(matricule);
            employe.setPerformance(Entreprise.PERFORMANCE_BASE);
            employe.setTempsPartiel(tempsPartiel);

        //when
        Integer NombreJourRTT = employe.getNbRtt(localDate);

        //then
            Assertions.assertThat(NombreJourRTT).isEqualTo(nbRTT);
    }

}
