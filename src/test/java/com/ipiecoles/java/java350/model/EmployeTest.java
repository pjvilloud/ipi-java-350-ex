package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    @Test
    public void test_getNombreAnneeAnciennete_whenDateEmbaucheNull_error(){
        //Given = Initialisation des donnée d'entrée
        Employe theEmploye = new Employe();
        theEmploye.setDateEmbauche(null);
        Integer nbAnnesAnciennete = theEmploye.getNombreAnneeAnciennete();

        //Then = Vérification de ce que fait la méthode
        Assertions.assertEquals(0,nbAnnesAnciennete);
    }

    @Test
    public void test_getNombreAnneeAnciennete_whenDateEmbaucheFutur_error(){
        //Given = Initialisation des donnée d'entrée
        Employe theEmploye = new Employe();
        theEmploye.setDateEmbauche(LocalDate.now().plusYears(1));
        Integer nbAnnesAnciennete = theEmploye.getNombreAnneeAnciennete();

        //Then = Vérification de ce que fait la méthode
        Assertions.assertEquals(0,nbAnnesAnciennete);
    }

    @Test
    public void test_getNombreAnneeAnciennete_whenDateEmbaucheToday_error(){
        //Given = Initialisation des donnée d'entrée
        Employe theEmploye = new Employe();
        theEmploye.setDateEmbauche(LocalDate.now());
        Integer nbAnnesAnciennete = theEmploye.getNombreAnneeAnciennete();

        //Then = Vérification de ce que fait la méthode
        Assertions.assertEquals(0,nbAnnesAnciennete);
    }

    @Test
    public void test_getNombreAnneeAnciennete_whenDateEmbauchePast_success(){
        //Given = Initialisation des donnée d'entrée
        Employe theEmploye = new Employe();
        theEmploye.setDateEmbauche(LocalDate.now().minusYears(1));
        Integer nbAnnesAnciennete = theEmploye.getNombreAnneeAnciennete();

        //Then = Vérification de ce que fait la méthode
        Assertions.assertEquals(1,nbAnnesAnciennete);
    }
}