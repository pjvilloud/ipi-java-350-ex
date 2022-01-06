package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @ParameterizedTest(name = "Matricule {0}, performance {1}, anciennete {2}, temps partiel {3} => prime {4}")
    @CsvSource({
            "'T12345',1,0,1.0,1000.0",
            ",1,0,1.0,1000.0",
            "'T12345',,0,1.0,1000.0",
            "'M12345',1,0,1.0,1700.0",
            "'T12345',2,0,1.0,2300.0",
    })
    public void testGetPrimeAnnuelle(String matricule, Integer performance,
                                     Long nbAnneesAnciennete, Double tempsPartiel,
                                     Double primeAttendue){
        //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setPerformance(performance);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneesAnciennete));
        employe.setTempsPartiel(tempsPartiel);
        //When
        Double prime = employe.getPrimeAnnuelle();
        //Then
        Assertions.assertEquals(primeAttendue,prime);
    }
}