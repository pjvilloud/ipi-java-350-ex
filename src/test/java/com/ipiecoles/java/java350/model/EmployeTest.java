package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void testGetNombreAnneeAncienneteDateEmbaucheNull(){
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        Assertions.assertEquals(0, (int)nbAnneeAnciennete);
    }

    @Test
    public void testGetNombreAnneeAncienneteIs0(){
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        Assertions.assertEquals(0, (int)nbAnneeAnciennete);

    }

    @Test
    public void testGetNombreAnneeAncienneteIs2(){
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        Assertions.assertEquals(2, (int)nbAnneeAnciennete);
    }

    @Test
    public void testGetNombreAnneeAncienneteIsNegative(){
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        Assertions.assertEquals(0, (int)nbAnneeAnciennete);

    }

    @ParameterizedTest(name = "immat {0} est valide : {1}")
    @CsvSource({
            "'0', M, 0, 1.0, 1700.0"
    })
    void testGetPrimeAnuelle(
            Integer performence,
            String matricule,
            Integer nombreAnneeAnciennete,
            Double tempsPartiel,
            Double primeAnnuelle
    ) {
        //Given
        Employe e = new Employe();
        e.setPerformance(performence);
        e.setMatricule(matricule);
        e.setDateEmbauche(LocalDate.now().minusYears(nombreAnneeAnciennete));
        e.setTempsPartiel(tempsPartiel);

        //When
        Double prime = e.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(primeAnnuelle, prime);
    }

}
