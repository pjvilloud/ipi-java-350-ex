package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {


    // Minimum pour faire une test unitaire

    // Tests unitaires classiques

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

    @Test
    public void testGetAnneAncienneteNplus2(){

        //Given = Initialisation des données d'entrée

        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));


        //When = Exécution de la méthode à tester

        Integer AnneAnciennete = e.getNombreAnneeAnciennete();


        //Then = Vérifications de ce qu'a fait la méthode

        Assertions.assertEquals(0, AnneAnciennete.intValue());

    }

    // Tests paramétrés

    @ParameterizedTest(name = "Performance {0} Matricule {1} nbAnne {2} tempsPartiel {3} donnePrime {4}")
    @CsvSource({
                "1, 'T12345', 0, 1.0, 1000.0",
                "1, 'T12345', 2, 0.5, 600.0",
                "1, 'T12345', 2, 1.0, 1200.0",
                "2, 'T12345', 0, 1.0, 2300.0",
                "2, 'T12345', 1, 1.0, 2400.0",
                "1, 'M12345', 0, 1.0, 1700.0",
                "1, 'M12345', 5, 1.0, 2200.0",
                "2, 'M12345', 0, 1.0, 1700.0",
                "3, 'M12345', 8, 1.0, 2500.0"
        })
        public void testGetPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle)  {

            //Given = Initialisation des données d'entrée
            Employe e = new Employe();
            e.setPerformance(performance);
            e.setMatricule(matricule);
            e.setDateEmbauche(LocalDate.now().minusYears(nbYearsAnciennete));
            e.setTempsPartiel(tempsPartiel);


            //When = Exécution de la méthode à tester
            Double prime = e.getPrimeAnnuelle();



            //Then = Vérifications de ce qu'a fait la méthode
            Assertions.assertEquals(primeAnnuelle, prime);


        }

        @Test

        public void testAugmenterSalaire(){

            //Given
            Employe e = new Employe();

            //When
            e.augmenterSalaire(0.5);
            Double newSalaire = 2281.83;

            //Then
            Assertions.assertEquals(newSalaire, e.getSalaire());
        }

        @Test

        public void testAugmenterSalaireCero(){

            //Given
            Employe e = new Employe();

            //When
            e.augmenterSalaire(0);
            Double newSalaire = 1521.22;

            //Then
            Assertions.assertEquals(newSalaire, e.getSalaire());
        }

        @Test

        public void testAugmenterSalaireNegative(){
            //Given
            Employe e = new Employe();
            e.setSalaire(1000.0);

            //When
            e.augmenterSalaire(-0.5);
            Double newSalaire = 1000.0;

            //Then
            Assertions.assertEquals(newSalaire, e.getSalaire());
        }

        @Test

        public void testAugmenterSalaireNull(){

            //Given
            Employe e = new Employe();
            e.setSalaire(null);

            //When
            e.augmenterSalaire(0.5);

            //Then
            Assertions.assertNull(e.getSalaire());
        }

    /**
     * - Nombre de jours dans l'année
     * - Nombre de jours travaillés dans l'année en plein temps
     * - Nombre de samedi et dimanche dans l'année
     * - Nombre de jours fériés ne tombant pas le week-end
     * - Nombre de congés payés.
     */

    @ParameterizedTest
    @CsvSource({
            "2016, 1.0, 12",
            "2016, 0.5, 6",
            "2019, 1.0, 8",
            "2019, 0.5, 4",
            "2019, -1.0, 0",
            "2021, 1.0, 11",
            "2021, 0.5, 5.5",
            "2022, 1.0, 10",
            "2022, 0.5, 5",
            "2023, 1.0, 8",
            "2023, 0.5, 4",
            "2032, 1.0, 12",
            "2032, 0.5, 6",
            "2028, 1.0, 8",
    })

    public void testGetNbrRtt(int years, Double tempsPartiel, Integer NbRtt){

        //Given
        Employe e = new Employe();

        //When
        e.setTempsPartiel(tempsPartiel);

        // Then
        Assertions.assertEquals(NbRtt, e.getNbRtt(LocalDate.ofYearDay(years, 1)));
    }
}
