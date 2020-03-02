package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void testGetNbAnneesAnciennete() {

        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When = Exécution de la méthode à tester
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @Test
    public void testGetNbAnneesAncienneteTodayPlus2() {

        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));

        //When = Exécution de la méthode à tester
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @Test
    public void testGetNbAnneesAncienneteTodayMinus3() {

        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(3));

        //When = Exécution de la méthode à tester
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(3);
    }

    @Test
    public void testGetPrimeAnnuelle(){

        Employe employe = new Employe();
        employe.setMatricule("T12345");
        employe.setDateEmbauche(LocalDate.now());
        employe.setTempsPartiel(1d);
        employe.setPerformance((Entreprise.PERFORMANCE_BASE));

        //When
        Double primeAnnuelle = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(primeAnnuelle).isEqualTo(1000.0);

    }

    @ParameterizedTest(name = "Employe matricule {0}, {1} année(s) d ancienneté, {2}, {3} gagnera une prime de {4} €.")
    @CsvSource({
            "'T12345',0,1.0,1,1000.0",
            "'T12345',0,0.5,1,500.0"
    })
    public void testGetPrimeAnnuelle(String matricule, Integer nbAnneesAnciennete,Double tempsPartiel, Integer performance, Double primeFinale){
        //Given
        Employe employe = new Employe();
        employe.setMatricule("T12345");
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneesAnciennete));
        employe.setTempsPartiel(tempsPartiel);
        employe.setPerformance(performance);

        //When
        Double primeAnnuelle = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(primeAnnuelle).isEqualTo(primeFinale);
    }
}
