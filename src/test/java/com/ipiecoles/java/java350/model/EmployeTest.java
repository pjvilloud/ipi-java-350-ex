package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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
}
