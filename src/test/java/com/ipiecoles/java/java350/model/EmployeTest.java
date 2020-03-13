package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    public void getNombreAnneeAncienneteNow(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNminus2(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(2, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNull(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNplus2(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2L));

        //When
        Integer anneeAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    /**
     * AugmentationSalaire +10%
     * @throws EmployeException
     */
    @Test
    public void augmenterSalairePourcentage10() throws EmployeException {
        // GIVEN
        Employe employe = new Employe();
        employe.setSalaire(1100.0);
        // WHEN
        employe.augmenterSalaire(10);
        // THEN
        Assertions.assertEquals(1210.0,employe.getSalaire());
    }

    /**
     * AugmentaitionSalaire +100%
     * @throws EmployeException
     */
    @Test
    public void augmenterSalairePourcentageMaximum() throws EmployeException {
        // GIVEN
        Employe employe = new Employe();
        employe.setSalaire(1100.0);
        // WHEN
        employe.augmenterSalaire(100);
        // THEN
        Assertions.assertEquals(2200.0,employe.getSalaire());
    }

    /**
     * Gestion Exception Salaire trop élévé
     * @throws EmployeException
     */
    @Test
    public void augmenterSalaireError() throws EmployeException {
        // GIVEN
        Employe employe = new Employe();
        employe.setSalaire(1100.0);
        // WHEN
        Assertions.assertThrows(EmployeException.class, () ->
                employe.augmenterSalaire(110)
        );
        // THEN
        Assertions.assertEquals(1100.0,employe.getSalaire());
    }

    /**
     * Gestion exception - Pourcentage Negatif
     * @throws EmployeException
     */
    @Test
    public void augmenterSalaireNegatif() throws EmployeException {
        // GIVEN
        Employe employe = new Employe();
        employe.setSalaire(1100.0);
        // WHEN
        employe.augmenterSalaire(-10);
        // THEN
        Assertions.assertEquals(990.0,employe.getSalaire());
    }

    /**
     * Gestion exception - Pourcentage Null
     * @throws EmployeException
     */
    @Test
    public void augmenterSalaire0Pourcent() throws EmployeException {
        // GIVEN
        Employe employe = new Employe();
        employe.setSalaire(1100.0);
        // WHEN
        Assertions.assertThrows(EmployeException.class, () ->
                employe.augmenterSalaire(0));
        // THEN
        Assertions.assertEquals(1100.0,employe.getSalaire());
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

    /**
     * NbreRttTest
     * @param dateString
     */
    @ParameterizedTest
    @ValueSource(strings = {"2019-01-01", "2021-01-01", "2022-01-01", "2032-01-01"})
    public void NbreRttTest(String dateString) {
        // GIVEN
        Employe employe = new Employe();
        LocalDate localDate = LocalDate.parse(dateString);
        employe.setDateEmbauche(localDate);
        // WHEN
        Integer nbreRtt = employe.getNbRtt(localDate);
        // THEN
        switch (localDate.toString()) {
            case "2019-01-01":
                Assertions.assertEquals(8, nbreRtt);
                break;
            case "2021-01-01":
                Assertions.assertEquals(11, nbreRtt);
                break;
            case "2022-01-01":
                Assertions.assertEquals(10, nbreRtt);
                break;
            case "2032-01-01":
                Assertions.assertEquals(12, nbreRtt);
                break;
        }
    }

    /**
     * NbreRttTest2ans
     * @param dateString
     */
    @ParameterizedTest
    @ValueSource(strings = {"2021-01-01", "2022-01-01", "2032-01-01"})
    public void NbreRttTest2ans(String dateString) {
        // GIVEN
        Employe employe = new Employe();
        LocalDate localDate = LocalDate.parse(dateString);
        employe.setDateEmbauche(localDate.minusYears(2L));
        // WHEN
        Integer nbreRtt = employe.getNbRtt(localDate);
        // THEN
        switch (localDate.toString()) {
            case "2021-01-01":
                Assertions.assertEquals(9, nbreRtt);
                break;
            case "2022-01-01":
                Assertions.assertEquals(8, nbreRtt);
                break;
            case "2032-01-01":
                Assertions.assertEquals(10, nbreRtt);
                break;
        }
    }

}
