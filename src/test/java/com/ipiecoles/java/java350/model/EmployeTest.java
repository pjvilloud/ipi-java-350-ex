package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.internal.matchers.Null;

import java.io.Console;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    @ParameterizedTest(name = "Arrivee il y a {0} ans : {1}")
    @CsvSource({
            "0, 0",
            "-5, 0",
            "8, 8"
    })
    void getNombreAnneeAnciennete(int dateOffset, int result) {
        // Given
        Employe emp = new Employe("SMITH", "John", "EMP01", LocalDate.now().minusYears(dateOffset), 5000.0, 1, 1.0);

        //When
        int res = emp.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(res).isEqualTo(result);
    }

    @Test
    void getNombreAnneeAncienneteEmbaucheNull() {
        // Given
        Employe emp = new Employe("SMITH", "John", "EMP01", null, 5000.0, 1, 1.0);

        try{
            //When
            int res = emp.getNombreAnneeAnciennete();
        }catch(Exception e){
            //Then
            Assertions.assertThat(e).isInstanceOf(NullPointerException.class);
        }
    }

    @ParameterizedTest(name = "Augmentation de {1}% sur {0} euros : {2}")
    @CsvSource({
            "1000.0, 8.0, 1080.0",
            "2500.0, 100.0, 5000.0",
            "2000.0, 10.0, 2200.0",
            "1500.0, 0.1, 1501.5",
            "1000.0, 0, 1000.0"
    })
    void testAugmenterSalaire(double salaireBase, double pourcentage, double salaireAugmente){
        //Given
        Employe employe = new Employe();
        employe.setSalaire(salaireBase);

        //When
        employe.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(salaireAugmente);
    }


    @ParameterizedTest(name = "Augmentation de {1}% sur {0} euros : {2}")
    @CsvSource({
            "100"
    })
    void testAugmenterSalaireNull(double pourcentage){
        //Given
        Employe employe = new Employe();
        employe.setSalaire(null);

        try{
            //When
            employe.augmenterSalaire(pourcentage);
        }catch(Exception e){
            //Then
            Assertions.assertThat(e).isInstanceOf(NullPointerException.class);
        }
    }



    @ParameterizedTest(name = "L'employé {1} a {2} année d'ancienneté, une performance de {3} travaillant sur un temps de {4}: prime de {5}")
    @CsvSource({
            "M987,  5,  2,  0.5,   1100",
            "C178,  6,  4,  1,     4900",
            "M521,  0,  2,  0.9,   1530",
            "C586,  0,  4,  0.2,   860",
            "M542,  5,  0,  0.8,   1760",
            "C050,  6,  0,  1,     900"
    })
    void testGetPrimeAnnuelle(String matricule, int nbAnciennete, int perf, double tpsPartiel, double prime){
        //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnciennete));
        employe.setPerformance(perf);
        employe.setTempsPartiel(tpsPartiel);

        //When
        double primeValue = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(prime).isEqualTo(primeValue);
    }

    @ParameterizedTest(name = "L'employé {1} a {2} année d'ancienneté, une performance de {3} travaillant sur un temps de {4}: prime de {5}")
    @CsvSource({
            "545, 5, 2, 1, 2200",
    })
    void testGetPrimeAnnuelleBadMatricule(String matricule, int nbAnciennete, int perf, double tpsPartiel, double prime){
        //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnciennete));
        employe.setPerformance(perf);
        employe.setTempsPartiel(tpsPartiel);

        try{
            //When
            double primeValue = employe.getPrimeAnnuelle();
        }catch(Exception e){
            //Then
            Assertions.assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }


    @ParameterizedTest(name = "L'employé {1} a {2} année d'ancienneté, une performance de {3} : prime de {4}")
    @CsvSource({
            ", 5, 2, 2200"
    })
    void testGetPrimeAnnuelleMatriculeNull(String matricule, int nbAnciennete, int perf, double tpsPartiel, double prime){
        //Given
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbAnciennete));
        employe.setPerformance(perf);
        employe.setTempsPartiel(tpsPartiel);

        try{
            //When
            double primeValue = employe.getPrimeAnnuelle();
        }catch(Exception e){
            //Then
            Assertions.assertThat(e).isInstanceOf(NullPointerException.class);
        }
    }


}