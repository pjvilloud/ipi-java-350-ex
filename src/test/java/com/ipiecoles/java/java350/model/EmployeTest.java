package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

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
    public void testAugmenteEmployeDe10Pourcent() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(2500.00);

        //When
        employe.augmenterSalaire(0.1);

        //Then
        Assertions.assertEquals(2750.00, (double) employe.getSalaire());
    }

    @Test
    public void testAugmenterSalaireSalaireNull(){
        Employe e = new Employe();
        e.setSalaire(0D);
        Integer pourcentage = 5;


        EmployeException employeException = Assertions.assertThrows(EmployeException.class, () -> e.augmenterSalaire(pourcentage));
        Assertions.assertEquals("Le salaire ne peut être égal à 0 !", employeException.getMessage());
    }

    @Test
    public void testAugmenteEmployeDe0Pourcent() throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(1500.00);

        //When
        employe.augmenterSalaire(0);

        Assertions.assertEquals(1500.00, (double) employe.getSalaire());
    }

    @Test
    public void testImpossibleBaisserSalaire() {
        //Given
        Employe employe = new Employe();
        employe.setSalaire(2000.00);

        //When/Then
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> employe.augmenterSalaire(-0.25)
        );
        Assertions.assertEquals("Impossible de diminuer le salaire d'un employé.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "2019, 1.0, 8",
            "2020, 1.0, 10",
            "2021, 0.8, 8",
            "2022, 1.0, 10",
            "2032, 1.0, 11"
    })
    public void testGetNbRtt( int year, double tempsPartiel, int expectedRtt) {
        //Given
        Employe employe = new Employe();
        employe.setTempsPartiel(tempsPartiel);
        LocalDate date = LocalDate.of(year, 1,1);

        //When
        int nombreJoursRtt = employe.getNbRtt(date);

        //Then
        Assertions.assertEquals(expectedRtt, nombreJoursRtt);
    }


}