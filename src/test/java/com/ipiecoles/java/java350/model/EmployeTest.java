package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeTest {

    //cas 1 : DateNow = 05/01/2022 | dateEmbauche = 05/01/2023 | nbAnneeAnciennete expected = 0
    @Test
    public void testGetSenorityWithFutureHiringDate(){
        LocalDate dateEmbauche = LocalDate.now().plusYears(3);

        Employe employe = new Employe("Doe", "John", "T12345", dateEmbauche, Entreprise.SALAIRE_BASE, 4, 1d);

        Integer nbYearOfSenority = employe.getNombreAnneeAnciennete();

        Assertions.assertThat(nbYearOfSenority).isZero();
    }

    //cas 2 : DateNow = 05/01/2022 | dateEmbauche = 05/01/2022 | nbAnneeAnciennete expected = 0
    @Test
    public void testGetSenorityWithHiringDate(){
        LocalDate dateEmbauche = LocalDate.now();

        Employe employe = new Employe("Doe", "John", "T12345", dateEmbauche, Entreprise.SALAIRE_BASE, 4, 1d);

        Integer nbYearOfSenority = employe.getNombreAnneeAnciennete();

        Assertions.assertThat(nbYearOfSenority).isZero();
    }

    //cas 3 : DateNow = 05/01/2022 | dateEmbauche = 05/01/2021 | nbAnneeAnciennete expected = 1
    @Test
    public void testGetSenorityWithPastHiringDate(){
        LocalDate dateEmbauche = LocalDate.now().minusYears(1);

        Employe employe = new Employe("Doe", "John", "T12345", dateEmbauche, Entreprise.SALAIRE_BASE, 4, 1d);

        Integer nbYearOfSenority = employe.getNombreAnneeAnciennete();

        Assertions.assertThat(nbYearOfSenority).isEqualTo(1);
    }

    //cas 4: DateNow = now | dateEmbauche = null | nbAnneeAnciennete expected = 0
    @Test
    public void testGetSenorityWithNullHiringDate(){
        LocalDate dateEmbauche = null;

        Employe employe = new Employe("Doe", "John", "T12345", dateEmbauche, Entreprise.SALAIRE_BASE, 4, 1d);

        Integer nbYearOfSenority = employe.getNombreAnneeAnciennete();

        Assertions.assertThat(nbYearOfSenority).isZero();
    }

    @Test
    public void testGetAnnualBonus(){
        LocalDate dateEmbauche = null;

        Employe employe = new Employe("Doe", "John", "T12345", dateEmbauche, Entreprise.SALAIRE_BASE, 4, 1d);

        Double d = employe.getPrimeAnnuelle();

        //Bonus + performances bonus + senority bonus
        //1000 + 0 + 0 => 1000
        Assertions.assertThat(d).isEqualTo(4300d);
    }
}
