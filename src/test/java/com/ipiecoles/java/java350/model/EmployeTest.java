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

    @Test
    public void testAugmenterSalaire() throws Exception {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(Entreprise.SALAIRE_BASE);
        Double pourcentage = 15d;

        //When
        employe.augmenterSalaire(pourcentage);

        //Then
        org.junit.jupiter.api.Assertions.assertEquals(1749.40,employe.getSalaire());

    }

    @Test
    public void testAugmenterSalairePourcentantNegatif() throws Exception {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(Entreprise.SALAIRE_BASE);
        Double pourcentage = -15d;

        //When
//        employe.augmenterSalaire(pourcentage);

        //Then
        Throwable throwable = Assertions.catchThrowable(() -> employe.augmenterSalaire(pourcentage));
        org.junit.jupiter.api.Assertions.assertEquals("Diminution du salaire impossible",throwable.getMessage());

    }

    @Test
    public void testAugmenterSalairePourcentantZero() throws Exception {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(Entreprise.SALAIRE_BASE);
        Double pourcentage = 0d;

        //When
//        employe.augmenterSalaire(pourcentage);

        //Then
        Throwable throwable = Assertions.catchThrowable(() -> employe.augmenterSalaire(pourcentage));
        org.junit.jupiter.api.Assertions.assertEquals("Augmentation par 0 impossible",throwable.getMessage());

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
    public void testGetNbRtt(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, LocalDate d, Integer nbRttFinal) {
        // Given
        Employe e = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);
        // When
        Integer nbRtt = e.getNbRtt(d);
        // Then
        org.junit.jupiter.api.Assertions.assertEquals(nbRttFinal, nbRtt);
    }

    @ParameterizedTest(name = "Employé matricule {0}, {1} années d ancienneté, {2}, {3} gagnera une prime de {4}")
    @CsvSource({"'T12345', 0, 1.0, 1, 1000.0",
            "'T12345', 0, 0.5, 1, 500.0"})
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

}