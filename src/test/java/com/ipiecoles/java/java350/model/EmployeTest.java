package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.ipiecoles.java.java350.model.Employe.*;

public class EmployeTest {

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
    public void testGetPrimeAnnuelle(){





    }



}
