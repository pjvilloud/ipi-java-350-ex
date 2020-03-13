package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.Assert;
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
    public void passageTempsPartiel(){
        // given
        Employe e = new Employe();
        e.setTempsPartiel(1.0);

        // when
        e.passeTempsPartiel();

        // Then
        Assertions.assertEquals(0.5, e.getTempsPartiel());
    }

    // une augmentation de salaire de 1%
    @Test
    public void augmenterSalaire1Pourcent() throws EmployeException {
        // GIVEN
        // un employe basique avec un salaire de 1500
        Employe e = new Employe();
        e.setSalaire(1500.0);
        // WHEN
        e.augmenterSalaire(1);
        // THEN
        Assertions.assertEquals(1515.0,e.getSalaire());
    }

    // Une augmentation de 100%
    @Test
    public void augmenterSalaire100Pourcent() throws EmployeException {
        // given
        // un employe basique avec un salaire de 1500
        Employe e = new Employe();
        e.setSalaire(1500.0);
        // when
        e.augmenterSalaire(100);
        // then
        Assertions.assertEquals(3000.0,e.getSalaire());
    }

    // Une augmentation de plus de 100% throw une erreur, et le salaire ne bouge pas
    @Test
    public void augmenterSalaire150Pourcent() throws EmployeException {
        // given
        // un employe basique avec un salaire de 1500
        Employe e = new Employe();
        e.setSalaire(1500.0);
        // when
        Assertions.assertThrows(EmployeException.class, () ->
            e.augmenterSalaire(150)
        );
        // then
        Assertions.assertEquals(1500.0,e.getSalaire());
    }

    // Une "augmentation" d'un pourcentage négatif -> = baisse de salaire, autorisé.
    @Test
    public void augmenterSalaireMinus50Pourcent() throws EmployeException {

        // given
        // un employe basique avec un salaire de 1500
        Employe e = new Employe();
        e.setSalaire(1500.0);
        // when
                e.augmenterSalaire(-50);
        // then
        Assertions.assertEquals(750.0,e.getSalaire());
    }
    // Une "augmentation" de 0% -> retourne un message d'erreur
    @Test
    public void augmenterSalaire0Pourcent() throws EmployeException {
        // given
        // un employe basique avec un salaire de 1500
        Employe e = new Employe();
        e.setSalaire(1500.0);
        // when
        Assertions.assertThrows(EmployeException.class, () ->
                e.augmenterSalaire(0)
        );
        // then
        Assertions.assertEquals(1500.0,e.getSalaire());
    }
}