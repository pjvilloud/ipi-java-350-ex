package com.ipiecoles.java.java350.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    @ParameterizedTest(name = "GetAnnee")
    @CsvSource({
            "0, 0",
            "-2, 2",
            "3, 0"
    })
    public void testGetAnneeAnciennete(Integer change, Integer expected){
        //Given
        Employe emp = new Employe();
        emp.setDateEmbauche(LocalDate.now().plusYears(change));

        //When
        Integer res = emp.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(expected, res);
    }

    @ParameterizedTest
    @CsvSource({
            "'C12345', 1.0, 0, 1, 1000.0",
            "'C12345', 0.5, 0, 1, 500.0",
            "'M12345', 1.0, 0, 1, 1700.0",
            "'C12345', 1.0, 0, 2, 2300.0"
    })
    public void testGetPrimeAnnuelle(String matricule, Double tempsPartiel, Integer nbAnneeAnciennete, Integer performance, Double primeCalculee){
        //Given
        Employe emp = new Employe();
        emp.setMatricule(matricule);
        emp.setTempsPartiel(tempsPartiel);
        emp.setDateEmbauche(LocalDate.now().minusYears(nbAnneeAnciennete));
        emp.setPerformance(performance);

        Double prime = emp.getPrimeAnnuelle();

        Assertions.assertEquals(primeCalculee, prime);
    }

    @ParameterizedTest
    @CsvSource({
            ".5",
            "-.5",
            "1.5"
    })
    public void testAugmenterSalaire(Double percent)
    {
        Employe emp = new Employe();
        emp.setSalaire(1000.0);
        emp.augmenterSalaire(percent);
        Double calc = 1000.0 * (1 + percent);

        Assertions.assertEquals(emp.getSalaire(), calc);
    }
}
