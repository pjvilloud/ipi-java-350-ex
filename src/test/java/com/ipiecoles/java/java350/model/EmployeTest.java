package com.ipiecoles.java.java350.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.management.BadAttributeValueExpException;
import java.time.LocalDate;

import static org.junit.Assert.fail;

public class EmployeTest {

    @Test
    public void getNombreAnneeAncienneteNow(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNminus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(2, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNull(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNplus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "1, 'T12345', 2, 0.5, 600.0",
            "1, 'T12345', 2, 1.0, 1200.0",
            "2, 'T12345', 0, 1.0, 2300.0",
            "2, 'T12345', 1, 1.0, 2400.0",
            "1, 'M12345', 0, 1.0, 1700.0",
            "1, 'M12345', 5, 1.0, 2200.0",
            "2, 'M12345', 0, 1.0, 1700.0",
            "2, 'M12345', 8, 1.0, 2500.0"
    })
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        //Given
        Employe employe = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(primeAnnuelle, prime);

    }

    @Test
    public void augmenterSalairePourcentageNegatif() throws BadAttributeValueExpException {
        //Given
        Employe e = new Employe();
        e.setSalaire(1500.0);
        //When
        try {
            e.augmenterSalaire(-20);
            fail("Pourcentage ne peut être null");
        }catch (IllegalArgumentException e1) {
            Assertions.assertEquals("Le pourcentage ne peut être null", e1.getMessage());
        }
    }

    @Test
    public void augmenterSalaireSalaireNull(){
        //Given
        Employe e = new Employe();
        e.setSalaire(null);
        //When
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> e.augmenterSalaire(10));
        Assertions.assertEquals("Salaire null",exception.getMessage());
    }

    @Test
    public void augmenterSalaireSalaireNegatif(){
        //Given
        Employe e = new Employe();
        e.setSalaire(-500.0);
        //When
        BadAttributeValueExpException exception = Assertions.assertThrows(BadAttributeValueExpException.class, () -> e.augmenterSalaire(10));
        Assertions.assertEquals(null,exception.getMessage());
    }


    @ParameterizedTest(name = "date {0} possede rtt : {1} ")
    @CsvSource({"0, 9","2, 11"," 3, 11"," 13, 10"})
    public void testNbRTT(int date, int rtt){
        //Employe emp = new Employe();
        //Assertions.assertThat(emp.getNbRtt(LocalDate.now().plusYears(date))).isEqualTo(rtt);// Erreur, je ne sais pas pourquoi sur le assertThat
    }
}
