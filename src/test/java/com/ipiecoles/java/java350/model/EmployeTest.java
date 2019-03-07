package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    @Test
    void getNombreAnneeAncienneteNow() {
        //given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());
        //when
        Integer nbAnnee = e.getNombreAnneeAnciennete();
        //then
        Assertions.assertEquals( 0, (int)nbAnnee);
    }
    @Test
    void getNombreAnneeAncienneteMinus2() {
        //given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));
        //when
        Integer nbAnnee = e.getNombreAnneeAnciennete();
        //then
        Assertions.assertEquals( 2, (int)nbAnnee);
    }
    @Test
    void getNombreAnneeAncienneteNull() {
        //given
        Employe e = new Employe();
        e.setDateEmbauche(null);
        //when
        Integer nbAnnee = e.getNombreAnneeAnciennete();
        //then
        Assertions.assertEquals( 0, (int)nbAnnee);
    }
    @Test
    void getNombreAnneeAnciennetePlus2() {
        //given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));
        //when
        Integer nbAnnee = e.getNombreAnneeAnciennete();
        //then
        Assertions.assertEquals( 0, (int)nbAnnee);
    }

    @ParameterizedTest()
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "1, 'T12345', 2, 0.5, 600.0",
    })
    void testGetPrimeAnnuelle(Integer performance, String matricule, Long anciennete, Double tempsPartiel, Double primeAnnuelle){
        //given
        Double prime = 0d;
        Employe e = new Employe("boby", "le grain de riz", matricule, LocalDate.now().minusYears(anciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);
        //when
        prime = e.getPrimeAnnuelle();
        //then
        Assertions.assertEquals(primeAnnuelle, prime);
    }

    //#region Evaluation
    @Test
    void augmenterSalaireTestTauxPositif(){
        //given
        Double augmentation = 0.2;
        Employe e = new Employe();
        e.setSalaire(1500.20);
        //when
        e.augmenterSalaire(augmentation);
        //then
        Assertions.assertEquals( 1800.24, (double)e.getSalaire());
    }

    @Test
    void augmenterSalaireTestTauxZero(){
        //given
        Double augmentation = 0d;
        Employe e = new Employe();
        e.setSalaire(1500.20);
        //when
        e.augmenterSalaire(augmentation);
        //then
        Assertions.assertEquals( 1500.20, (double)e.getSalaire());
    }

    @Test
    void augmenterSalaireTestTauxNull(){
        //given
        Double augmentation = null;
        Employe e = new Employe();
        e.setSalaire(1500.20);
        //when then
        try {
            e.augmenterSalaire(augmentation);
            Assertions.fail("aurais du lever une exeption");
        }
        catch (IllegalArgumentException ex){
            Assertions.assertEquals("coeficient > 0", ex.getMessage());
        }
    }

    @Test
    void augmenterSalaireTestTauxNegatif(){
        //given
        Double augmentation = -0.5;
        Employe e = new Employe();
        e.setSalaire(1500.20);
        //when then
        try {
            e.augmenterSalaire(augmentation);
            Assertions.fail("aurais du lever une exeption");
        }
        catch (IllegalArgumentException ex){
            Assertions.assertEquals("coeficient > 0", ex.getMessage());
        }
    }

    @ParameterizedTest()
    @CsvSource({
            "2019-10-10, 8",
            "2021-04-05, 10",
            "2022-03-09, 10",
            "2032-01-27, 11",
    })
    void testgetNbRtt(LocalDate date, Integer nbRtt){
        //given
        Employe emp = new Employe("boby", "le grain de riz", "M12345", LocalDate.now().minusYears(2), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0);
        Integer nbRttSalarie = 0;
        //when
        nbRttSalarie = emp.getNbRtt(date);
        //then
        Assertions.assertEquals(nbRtt, nbRttSalarie);
    }
    //#endregion
}