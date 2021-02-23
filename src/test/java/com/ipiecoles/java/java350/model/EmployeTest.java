package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    //Embauché en 2020. On est en 2020 : 0 an ancienneté
    public void testNombreAnneeAncienneteNow(){
        //Given
        LocalDate now = LocalDate.now();
        Employe employe = new Employe();
        employe.setDateEmbauche(now);

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(0);
    }

    @Test
    //Embauché en 2019. On est en 2020 : 1 an ancienneté
    public void testNombreAnneeAncienneteNMoins1(){
        //Given
        LocalDate nMoins1 = LocalDate.now().minusYears(1);
        Employe employe = new Employe();
        employe.setDateEmbauche(nMoins1);

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(1);
    }

    @Test
    //Embauché en 2021. On est en 2020 : 0 an ancienneté
    public void testNombreAnneeAncienneteNPlus1(){
        //Given
        LocalDate nPlus1 = LocalDate.now().plusYears(1);
        Employe employe = new Employe();
        employe.setDateEmbauche(nPlus1);

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(0);
    }

    @Test
    //Embauché en 2021. On est en 2020 : 0 an ancienneté
    public void testNombreAnneeAncienneteNull(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(0);
    }

//////////////////////////////////////////////// EVALUATION /////////////////////////////////////////////////////////
    @Test
    //Pourcentage Positif
    public void testAugmenterSalairePourcentagePositif(){
        //Given
        Double pourcentage = 1.2d;
        Employe employe = new Employe();
        Double salaire = employe.getSalaire();

        //When
        employe.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertThat(salaire > 0);
    }

    @Test
    //Pourcentage négatif
    public void testAugmenterSalairePourcentageNegatif(){
        //Given
        Double pourcentage = -1.3d;
        Employe employe = new Employe();
        Double salaire = employe.getSalaire();
        //When
        employe.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertThat(salaire < 0).isEqualTo(false);
    }

    @Test
    //Pourcentage vaut zero
    public void testAugmenterSalaireVautZero(){
        //Given
        Double pourcentage = 0d;
        Employe employe = new Employe();
        Double salaire = employe.getSalaire();

        //When
        employe.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertThat(salaire).isEqualTo(salaire);
    }

    @Test
    //On vérifie si la fonction marche
    public void testAugmenterSalaireAugmenter(){
        //Given
        Double pourcentage = 1.5d;
        Employe employe = new Employe();
        employe.setSalaire(1200d);

        //When
        employe.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertThat(employe.getSalaire()).isGreaterThan(1200.0);
    }

    //Test paramétré pour savoir si le nombre
    @ParameterizedTest
    @CsvSource({
            "'2019-01-01', 8",
            "'2021-01-01', 10",
            "'2022-01-01', 10",
            "'2032-01-01', 11" })
    void testNbRTT(LocalDate date, Integer nbRTTVoulu) {
        Employe employe = new Employe();

        Integer nbRtt = employe.getNbRtt(date);
        Assertions.assertThat(nbRtt).isEqualTo(nbRTTVoulu);
    }
}
