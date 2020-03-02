package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;



public class EmployeTest {

    @Autowired
    private Employe employe;

    @Test
    public void getNombreAnneeAncienneteTest () {
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));

        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        Assertions.assertThat(anneeAnciennete).isEqualTo(2);
    }

    @Test
    public void getNombreAnneeAncienneteFutureEmbaucheTest () {
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));

        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        Assertions.assertThat(anneeAnciennete).isEqualTo(0);
    }

    @Test
    public void IsNullNombreAnneeAncienneteTest () {
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        Assertions.assertThat(anneeAnciennete).isEqualTo(0);

    }

    @Test
    public void NombreAnneeAncienneteEstMaintenantTest () {
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        Assertions.assertThat(anneeAnciennete).isEqualTo(0);
    }


    @Test
    public void getPrimeAnnuellePourManagerTest () {
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));
        employe.setMatricule("Mtest");
        employe.setPerformance(1);
        employe.setTempsPartiel(1.0);

        Double primeAnciennete = Entreprise.PRIME_ANCIENNETE * employe.getNombreAnneeAnciennete();
        Double Prime = employe.getPrimeAnnuelle();

        Double compare = Entreprise.primeAnnuelleBase() * Entreprise.INDICE_PRIME_MANAGER + primeAnciennete;

        Assertions.assertThat(Prime).isEqualTo(compare);
    }

    @ParameterizedTest(name = "Matricule : {0}, performance : {1}, tempsPartiel : {2}, Le resultat devrait être : {3}")
    @CsvSource({
            "'Mtest', 1, 1.0, 1900d",
            "'test', 1, 1.0, 1200d",
            ", 1, 1.0, 1200d",
            "'test', , 1.0, 1200d",
            "'test', 0, 1.0, 500d",
            "'test', 2, 1.0, 2500d",
            "'Mtest', 1, 0, 0d"
    })
    public void getPrimeAnnuellePourManagerParamTest (String matricule, Integer performance, Double tempsPartiel, Double result) {
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));
        employe.setMatricule(matricule);
        employe.setPerformance(performance);
        employe.setTempsPartiel(tempsPartiel);

        Double Prime = employe.getPrimeAnnuelle();

        Assertions.assertThat(Prime).isEqualTo(result);
    }

}
