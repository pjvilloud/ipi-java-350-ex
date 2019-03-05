package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeTest {


    // Minimum pour faire une test unitaire

    @Test //Junit 4 : org.junit.Test, Junit 5 : org.junit.jupiter.api.Test
    public void testGetNombreAnneeAncienneteNull(){

            //Given = Initialisation des données d'entrée

            Employe e = new Employe();
            e.setDateEmbauche(null);


            //When = Exécution de la méthode à tester

            Integer nbAnneAncienete = e.getNombreAnneeAnciennete();


            //Then = Vérifications de ce qu'a fait la méthode

            Assertions.assertEquals(0, (int)nbAnneAncienete);

        }

    @Test
    public void testGetAnneAncienneteDateNow(){

            //Given = Initialisation des données d'entrée

            Employe e = new Employe();
            e.setDateEmbauche(LocalDate.now());


            //When = Exécution de la méthode à tester

            Integer nbAnneAnciennete = e.getNombreAnneeAnciennete();


            //Then = Vérifications de ce qu'a fait la méthode

            Assertions.assertEquals(0, nbAnneAnciennete.intValue());

        }

    @Test
        public void testGetAnneAncienneteNminus2(){

            //Given = Initialisation des données d'entrée

            Employe e = new Employe();
            e.setDateEmbauche(LocalDate.now().minusYears(2L));


            //When = Exécution de la méthode à tester

            Integer nbAnneAnciennete = e.getNombreAnneeAnciennete();


            //Then = Vérifications de ce qu'a fait la méthode

            Assertions.assertEquals(2, nbAnneAnciennete.intValue());

        }
    }
