package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.ipiecoles.java.java350.model.Employe.*;

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

    @Test // exemple correction
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


}
