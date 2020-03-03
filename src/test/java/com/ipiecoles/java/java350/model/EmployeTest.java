package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheNull() {
        //Cas où la date d'acienneté est null
        //Given = Initialisation des données d'entrée
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When = Exécution de la méthode à tester
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then = Vérifications de ce qu'a fait la méthode
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheNowPlus2y() {
        //Cas où la date d'embauche est > à auj + 2 ans
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));

        //When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheNowMinus3y() {
        //Cas où la date d'embauche est > à auj - 3 ans
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(3));

        //When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(3);
    }

    @Test
    public void testGetNombreAnneeAncienneteDtEmbaucheNow() {
        //Cas où la date d'embauche est = auj
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When
        Integer nbAnneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneeAnciennete).isEqualTo(0);
    }

    @ParameterizedTest(name = "Employé matricule {0}, {1} années d ancienneté, {2}, {3} gagnera une prime de {4}")
    @CsvSource({
            "'T12345', 0, 1.0, 1, 1000.0",
            "'T12345', 2, 0.5, 1, 600.0",
            "'T12345', 2, 1.0, 1, 1200.0",
            "'T12345', 0, 1.0, 1, 2300.0",
            "'T12345', 1, 1.0, 1, 2400.0",
            "'M12345', 0, 1.0, 1, 1700.0",
            "'M12345', 5, 1.0, 1, 2200.0",
            "'M12345', 0, 1.0, 1, 1700.0",
            "'M12345', 8, 1.0, 1, 2500.0"
    })
    void testGetPrimeAnnuelle(String matricule, Integer nbAnneeAnciennete, Double tempPartiel, Integer performance, Double primeFinale) {
        //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneeAnciennete));
        employe.setTempsPartiel(tempPartiel);
        employe.setPerformance(performance);

        //When
        Double prime = employe.getPrimeAnnuelle();
        //Then
        Assertions.assertThat(prime).isEqualTo(primeFinale);
    }

    @Test
    void testGetPrimeAnnuelle() {
        //Given
        Employe employe = new Employe();
        employe.setMatricule("T12345");
        employe.setDateEmbauche(LocalDate.now());
        employe.setTempsPartiel(1d);
        employe.setPerformance(Entreprise.PERFORMANCE_BASE);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(prime).isEqualTo(Entreprise.primeAnnuelleBase());
    }
}
