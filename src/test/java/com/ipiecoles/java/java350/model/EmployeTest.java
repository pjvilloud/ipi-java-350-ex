package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeTest {

    // J'annote ma fonction avec jupiter API
    @Test // clic droit sur le test en bas et run pour lancer un test
    public void testS1(){
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));

        //When = Exécution de la méthode à tester
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnnees).isEqualTo(2);
    }

    @Test // clic droit sur le test en bas et run pour lancer un test
    public void testNbAnneeAncienneteNowPlus3(){
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(3));

        //When = Exécution de la méthode à tester
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test // clic droit sur le test en bas et run pour lancer un test
    public void testNbAnneeAncienneteNull(){
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(null);


        //When = Exécution de la méthode à tester
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }


}
