package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

class EmployeTest {

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheNull() {
        //Given Création employe avec une date embauche à null
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        // When Initiatiolisation d'un nb d'année d'anciennete
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then Vérification que c'est bien à 0
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheTodayPlus2() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheTodayMinus3() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(3));

        // When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(3);
    }


    @ParameterizedTest(name = "Employé matricule {0}, {1} années d ancienneté, {2}, {3} gagnera une prime de {4}")
    @CsvSource({"'T12345', 0, 1.0, 1, 1000.0", "'T12345', 0, 0.5, 1, 500.0"})
    public void testGetPrimeAnnuelle(String matricule, Integer nbAnneeAnciennete, Double tempsPartiel,
                                     Integer performance, Double primeFinale){
        //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneeAnciennete));
        employe.setTempsPartiel(tempsPartiel);
        employe.setPerformance(performance);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(prime).isEqualTo(primeFinale);
    }

    @ParameterizedTest(name = "Employé de matricule {0}, s'appelant {1} {2}, change de taux d'activité de {3} à et gagnera {4} ")
    @CsvSource({"'C01234', John, Doe, 1, 1064.22"})
    public void testModificationEmploye(String matricule, String prenom, String nom, Double tempsPartiel,
                                        Double salaire){

        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setPrenom(prenom);
        employe.setNom(nom);
        employe.setSalaire(salaire);
        employe.setTempsPartiel(tempsPartiel);

        //When
        employe.setTempsPartiel(0.5);

        //Then
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(0.5);
    }

    @Test
    public void testAugmenterSalaireSupZero() throws Exception{
        // Given
        Employe employe = new Employe();
        employe.setSalaire(Entreprise.SALAIRE_BASE);
        Double pourcentage = 15d;

        // When
        employe.augmenterSalaire(pourcentage);

        // Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1749.4);
    }

    @Test
    public void testAugmenterSalaireInfZero() throws Exception{
        // Given
        Employe employe = new Employe();
        employe.setSalaire(Entreprise.SALAIRE_BASE);
        Double pourcentage = -15d;

        // Then
        Throwable throwable = Assertions.catchThrowable(() -> employe.augmenterSalaire(pourcentage));
        Assertions.assertThat(throwable.getMessage()).isEqualTo("Une augmentation négative n'est pas possible !");
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 0.5, '2019-01-21', 4",
            "1, 'T12345', 2, 1, '2020-01-21', 10",
            "2, 'T12345', 0, 1, '2019-01-21', 8",
            "2, 'T12345', 1, 0.5, '2019-01-21', 4"
    })
    public void testGetNbRtt(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, LocalDate d, Integer nbRttFinal){
        // Given
        Employe e = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);
        // When
        Integer nbRtt = e.getNbRtt(d);
        // Then
        Assertions.assertThat(nbRttFinal).isEqualTo(nbRtt);
    }


    @ParameterizedTest
    @CsvSource({
            "2, 'T12345', 1, 1, '2044-11-15', 11", //Vendredi
            "2, 'T12345', 1, 0.5, '2044-11-15', 6", //Vendredi
            "2, 'T12345', 1, 1, '2019-11-15', 8", //Mardi
            "2, 'T12345', 1, 0.5, '2019-11-15', 4", //Mardi
            "2, 'T12345', 1, 1, '2021-11-15', 10", //Vendredi
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
        Assertions.assertThat(nbRttFinal).isEqualTo(nbRtt);
    }
}
