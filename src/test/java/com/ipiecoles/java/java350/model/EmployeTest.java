package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;


public class EmployeTest {

    /////////////////////////////////////////Test Unitaire
    @Test // un test basique
    public void testGetNombreAnneeAncienneteAvecDateEmbaucheNull(){
        // Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer duree = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(duree).isNull();
    }

    @Test // un test basique
    public void testGetNombreAnneeAncienneteAvecDateEmbaucheInférieurNow(){
        // Given
        Employe employe = new Employe("Doe", "John", "T12345", LocalDate.now().minusYears(6), 1500d, 1, 1.0);

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(anneeAnciennete).isEqualTo(6);
    }


    @Test // un test basique
    public void testGetNombreAnneeAncienneteAvecDateEmbaucheSupérieurNow(){
        // Given
        Employe employe = new Employe("Doe", "John", "T12345", LocalDate.now().plusYears(6), 1500d, 1, 1.0);

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(anneeAnciennete).isNull();
    }


    @Test
    public void testGetNbAnneeAncienneteDateEmbaucheNow(){
        //Given
        Employe employe = new Employe("Doe", "John", "T12345",
                LocalDate.now(), 1500d, 1, 1.0);
        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(anneeAnciennete).isEqualTo(0);
    }


    //////////////////////////////////////Tests préparés
    //Pour la méthode getPrimeAnnuelle()
    //1) les entrées
    //date d'embauche (car la prime n'est pas une entrée en tant que tel)
    //indice manager
    //indice performance
    //temps partiel (% d'activité)

    //2) scénario faisant varier les entrées
    //employé simple sans ancienneté
    //employé simple avec ancienneté
    //employé avec performance
    //employé est un manager/technicien sont gérés de la même manière
    //employé est à temps partiel

    //3) écrire un TU simple qui teste un des scénarios

    //4) dupliquer et transformer le scénario en test préparé testant tous les scénarios


    @Test //version TU simple
    public void testGetPrimeAnnuellePourUnEmployeATempsPartiel(){
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());
        employe.setTempsPartiel(0.5);

        Double prime = employe.getPrimeAnnuelle();

        //primme annuelle de base = 1000d
        Assertions.assertThat(prime).isEqualTo(500);

    }

    @Test // exemple correction de TU
    public void testGetPrimeAnnuelle(){
        //Given
        Integer performance = 1; //voir dans la classe correspondant
        String matricule = "T12345";
        Double tauxActivite = 1.0;
        Long nbAnneeAnciennete = 0L;

        Employe employe = new Employe("Doe", "John", matricule,
                LocalDate.now().minusYears(nbAnneeAnciennete), 1500d, performance, tauxActivite);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Double primeAttendue = 1000D; //dans notre cas, on va la prime de base donc 1000 -> vu dans la classe
        Assertions.assertThat(prime).isEqualTo(primeAttendue);

    }

//    ////version Test paramétré
//    @ParameterizedTest
//    @CsvSource({
//            "1, 'T12345', 1.0, 0"
//    })
//    public void testGetPrimeAnnuelleVersionTestPrepare(
//            Integer performance, String matricule, Double tauxActivite, Long nbAnneeAnciennete){
//
//        //Given, When, Then
//        Employe employe = new Employe("Doe", "John", matricule,
//                LocalDate.now().minusYears(nbAnneeAnciennete), 1500d, performance, tauxActivite);
//        Double primeAttendue = 1000D;
//
//        Assertions.assertThat(employe.getPrimeAnnuelle()).isEqualTo(primeAttendue);
//    }


    ////version Test paramétré Corrigé ==> cas normaux
    @ParameterizedTest(name = "Perf {0}, matricule {1}, txActivite {2}, anciennete {3}, => prime {4}") //permet d'avoir une version nommée du test
    @CsvSource({
            "1, 'T12345', 1.0, 0, 1000",
            "1, 'T12345', 0.5, 0, 500",  //un temps partiel
            "2, 'T12345', 1.0, 0, 2300", //une meilleure performance
            "1, 'T12345', 1.0, 2, 1200",  //avec 2 ans d'anciennetée
            "1, 'M12345', 1.0, 0, 1700"   //on test un manager
    })
    public void testGetPrimeAnnuelleVersionTestPrepareCasNormaux(
            Integer performance, String matricule, Double tauxActivite, Long nbAnneeAnciennete, Double primeAttendue){

        //Given, When, Then
        Employe employe = new Employe("Doe", "John", matricule,
                LocalDate.now().minusYears(nbAnneeAnciennete), 1500d, performance, tauxActivite);

        Double prime = employe.getPrimeAnnuelle();

        Assertions.assertThat(prime).isEqualTo(primeAttendue);
    }

    ////Un test limite
    //Les tests limites doivent être plutôt géré en TU
    @Test
    public void testGetPrimeAnnuelleMatriculeNull(){

        //Given, When, Then
        Employe employe = new Employe("Doe", "John", null,
                LocalDate.now().minusYears(0), 1500d, 1, 1.0);

        Double prime = employe.getPrimeAnnuelle();

        Assertions.assertThat(prime).isEqualTo(1000);
    }



}
