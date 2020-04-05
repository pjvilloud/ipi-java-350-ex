package com.ipiecoles.java.java350.model;
import com.ipiecoles.java.java350.service.EmployeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {
    private EmployeService employeService;

    //Coverage sur le methode des RTT
    @Test
    public void getNbRtt(){
        //Given
        Employe employe = new Employe();
        //When
        Integer nbRTT = employe.getNbRtt();
        //Then
        Assertions.assertThat(nbRTT).isEqualTo(9);
    }

    //Coverage sur la méthode des congés
    @Test
    public void getNbConges(){
        //Given
        Employe employe = new Employe();
        //When
        Integer nbConges = employe.getNbConges();
        //Then
        Assertions.assertThat(nbConges).isEqualTo(25);
    }

    @Test
    public void getNombreAnneeAnciennete(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(employe.getDateEmbauche());

        //when
        Integer nbAnneeEmploye = employe.getNombreAnneeAnciennete();

        //then
        Assertions.assertThat(nbAnneeEmploye).isEqualTo(0);
    }


    @Test
    public void testAnneeAncinneteNow(){
        //Given Envoie de la class Employe
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When  recupéreration du nombre d'année d'ancienneté
        Integer nbAnneEmploye = employe.getNombreAnneeAnciennete();

        //Then : test du nombre d'année d'ancienneté
        Assertions.assertThat(nbAnneEmploye).isEqualTo(0);
    }

    @Test
    public void getNombreAnneeAncienneteNminus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(anneeAnciennete).isEqualTo(2);
    }

    @Test
    public void getNombreAnneeAncienneteNull(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(anneeAnciennete).isEqualTo(0);
    }

    @Test
    public void getNombreAnneeAncienneteNplus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(anneeAnciennete).isEqualTo(0);
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
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbAnneeAnciennete, Double tempsPartiel, Double primeCalculee){
        //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setTempsPartiel(tempsPartiel);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneeAnciennete));
        employe.setPerformance(performance);
        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(prime).isEqualTo(primeCalculee);

    }

    @Test
    public void testNbAnneeAncienneteNowPlus3(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(3));

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    public void testNbAnneeAncienneteNull(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    /**
     * Partie Evaluation
     */

    /** Test unitaire sur la méthode augmenter salaire **/
    @Test
    public void getAugmenterSalaire(){

    }

}
