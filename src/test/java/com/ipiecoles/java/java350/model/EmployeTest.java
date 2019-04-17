package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test; // pour JUnit5 (pas le même pour le 4)
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void testAugmenterSalaireDoubleNegatif() {
            // Given
        Employe e = new Employe();
        e.setSalaire(Entreprise.SALAIRE_BASE);
        double pourcentage = -0.5;

        try {
            // When
            e.augmenterSalaire(pourcentage);
            Assertions.fail("Devrait lancer une exception : pourcentage négatif");
        }
        catch (EmployeException employeException) {
            // Then
            Assertions.assertEquals("Le pourcentage donné : " + pourcentage + " ne peut être inférieur à 0, il est illégal de diminuer un salaire.", employeException.getMessage());
        }
    }

    @Test
    public void testAugmenterSalaireDoubleZero() {
        // Given
        Employe e = new Employe();
        e.setSalaire(Entreprise.SALAIRE_BASE);
        double pourcentage = 0.0;

        try {
            // When
            e.augmenterSalaire(pourcentage);
            Assertions.fail("Devrait lancer une exception : pourcentage = 0" );
        } catch (EmployeException employeException) {
            // Then
            Assertions.assertEquals("Le pourcentage d'augmentation est égal à 0 : aucune augmentation de salaire n'est effective", employeException.getMessage());
        }
    }

    @Test
    public void testAugmenterSalaireNull() {
        // Given
        Employe e = new Employe();
        e.setSalaire(null);

        try {
            // When
            e.augmenterSalaire(0.5);
            Assertions.fail("Devrait lancer une exception : Salaire null");
        } catch (EmployeException employeException) {
            // Then
            Assertions.assertEquals("Le salaire de l'employe n'est pas initialisé, il doit être renseigné avant d'être augmenté", employeException.getMessage());
        }
    }


    @Test
    public void testAugmenterSalaire50Pourcent() throws EmployeException {
        // Given
        Employe e = new Employe();
        e.setSalaire(Entreprise.SALAIRE_BASE);
        // When
        e.augmenterSalaire(0.5);
        // Then
        Assertions.assertEquals(2281.83, (double)e.getSalaire());
    }

    @Test
    public void testAugmenterSalaire200Pourcent() throws EmployeException {
        // Given
        Employe e = new Employe();
        e.setSalaire(Entreprise.SALAIRE_BASE);
        // When
        e.augmenterSalaire(2.0);
        // Then
        Assertions.assertEquals(4563.66, (double)e.getSalaire());
    }

    @Test
    public void testNombreAnneesAncienneteDateEmbaucheNull() {

        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        //When
        Integer nbAnneesAnciennete = e.getNombreAnneeAnciennete();


        //Then
        Assertions.assertEquals(0, (int)nbAnneesAnciennete);
    }

    @Test
    public void testNombreAnneesAncienneteDateEmbaucheNow() {
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());

        // When
        Integer nbAnneAnciennete = e.getNombreAnneeAnciennete();

        // Then
        Assertions.assertEquals(0, (int)nbAnneAnciennete);
    }

    @Test
    public void testNombreAnneesAncienneteMinus2(){
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2));

        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        Assertions.assertEquals(2, (int)nbAnneeAnciennete);
    }

    @Test
    public void testNombreAnneesAncienneteDateEmbaucheAfterNow() {
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(1));

        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        Assertions.assertEquals(0, (int)nbAnneeAnciennete);
    }

    @ParameterizedTest(name = "pour l'année {0} : nombre de jours de RTT {1}, à temps de travail : {2}")
    @CsvSource( {
            "2019, 8, 1.0",
            "2021, 11, 1.0",
            "2022, 10, 1.0",
            "2032, 12, 1.0",
            "2021, 6, 0.5",
            "2032, 6, 0.5",
            "2040, 10, 1.0",
            "2044, 10, 1.0",
            "2028, 8, 1.0",
            "2023, 8, 1.0"
    })
    void testgetNbRtt(int date, int nbRttAttendus, Double tempsPartiel) {
        // Given
        Employe employe = new Employe();
        employe.setTempsPartiel(tempsPartiel);
        LocalDate annee = LocalDate.of(date,1,1);
        // When
        int nbJoursRtt = employe.getNbRtt(annee);
        // Then
        Assertions.assertEquals(nbRttAttendus, nbJoursRtt);
    }


    @ParameterizedTest(name = "pour cette année {0} : nombre de jours de RTT {1}, à temps de travail : {2}")
    @CsvSource( {
            "2019, 8, 1.0"
    })
    void testgetNbRttNow(int date, int nbRttAttendus, Double tempsPartiel) {
        // Given
        Employe employe = new Employe();
        employe.setTempsPartiel(tempsPartiel);
        // When
        int nbJoursRtt = employe.getNbRtt();
        // Then
        Assertions.assertEquals(nbRttAttendus, nbJoursRtt);
    }




    @ParameterizedTest(name = "pour employé marticule {1}, perf {0}, ancienneté {2}, temps partiel {3} : prime annuelle {4}")
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0"
    })
    void testGetPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle ){
        // Given
        Employe e = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        // When
        Double primeATester = e.getPrimeAnnuelle();

        // Then
        Assertions.assertEquals(primeAnnuelle, primeATester);

    }
}
