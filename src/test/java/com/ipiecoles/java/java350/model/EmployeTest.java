package com.ipiecoles.java.java350.model;

import io.cucumber.java8.En;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

class EmployeTest {

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
    public void getNombreAnneeAncienneteMinus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(2, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNull() {
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNplus2() {
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

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "1, 'T12345', 2, 1.0, 1200.0",
            "2, 'T12345', 0, 1.0, 2300.0",
            "2, 'T12345', 1, 1.0, 2400.0",
            "1, 'M12345', 0, 1.0, 1700.0",
            "1, 'M12345', 5, 1.0, 2200.0",
            "2, 'M12345', 0, 1.0, 1700.0",
            "2, 'M12345', 8, 1.0, 2500.0"
    })
    public void getTempsPartielA05(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle) {
        //Given
        Employe employe = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        employe.setTempsPartiel(0.5d);
        Double prime = employe.getPrimeAnnuelle();

        //Then
        org.assertj.core.api.Assertions.assertThat(employe.getTempsPartiel()).isLessThan(1d);
        Assertions.assertEquals(primeAnnuelle/2, prime);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 0.5, 500.0",
            "1, 'T12345', 2, 0.5, 600.0",
            "2, 'T12345', 0, 0.5, 1150.0",
            "2, 'T12345', 1, 0.5, 1200.0",
            "1, 'M12345', 0, 0.5, 850.0",
            "1, 'M12345', 5, 0.5, 1100.0",
            "2, 'M12345', 8, 0.5, 1250.0"
    })
    public void getTempsPartielA1(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle) {
        //Given
        Employe employe = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        employe.setTempsPartiel(1d);
        Double prime = employe.getPrimeAnnuelle();

        //Then
        org.assertj.core.api.Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1d);
        Assertions.assertEquals(primeAnnuelle*2, prime);
    }

    @Test
    public void getTempsPartiel() {
        //Given
        Employe e = new Employe();
        e.setTempsPartiel(0d);

        //When
        Double tempsPartiel = e.getTempsPartiel();

        //Then
        Assertions.assertEquals(0, tempsPartiel.doubleValue());
    }

    @Test
    public void getAugmenterSalaireIsZero() throws Exception {
        //Given
        Employe e = new Employe();
        e.setSalaire(0d);

        //When
        e.augmenterSalaire(100d);

        //Then
        Assertions.assertEquals(0d, e.getSalaire());
    }

    @Test
    public void getAugmenterSalaire() throws Exception {
        //Given
        Employe e = new Employe();
        e.setSalaire(Entreprise.SALAIRE_BASE);

        //When
        e.augmenterSalaire(12d);

        //Then
        Assertions.assertEquals(1703.77, e.getSalaire());
    }

    @Test
    public void getAugmenterSalaireIsTooLow() throws Exception {
        //Given
        Employe e = new Employe();
        e.setSalaire(Entreprise.SALAIRE_BASE);
        Double pourcentage = 0d;

        //When //Then
        Throwable throwable = org.assertj.core.api.Assertions.catchThrowable(() -> e.augmenterSalaire(pourcentage));

        Assertions.assertEquals("Diminution de salaire impossible", throwable.getMessage());
    }



    @ParameterizedTest
    @CsvSource({
            "2, 'T12345', 1, 1, '2044-11-15', 8", //Vendredi
            "2, 'T12345', 1, 0.5, '2044-11-15', 4", //Vendredi
            "2, 'T12345', 1, 1, '2019-11-15', 8", //Mardi
            "2, 'T12345', 1, 0.5, '2019-11-15', 4", //Mardi
            "2, 'T12345', 1, 1, '2021-11-15', 9", //Vendredi
            "2, 'T12345', 1, 0.5, '2021-11-15', 5", //Vendredi
            "2, 'T12345', 1, 1, '2022-11-15', 10", //Samedi
            "2, 'T12345', 1, 0.5, '2022-11-15', 5", //Samedi
            "2, 'T12345', 1, 1, '2032-11-15', 11", //Jeudi
            "2, 'T12345', 1, 0.5, '2032-11-15', 6", //Jeudi
    })
    public void getNbRtt(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, LocalDate d, Integer nbRttFinal) {
        // Given
        Employe e = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        // When
        Integer nbRtt = e.getNbRtt(d);

        // Then
        Assertions.assertEquals(nbRttFinal, nbRtt);
    }

    @Test
    public void getNbWithNoDateTest() {
        //Given
        Employe e = new Employe();

        //When
        Integer nbRtt = e.getNbRtt();

        //Then
        Assertions.assertEquals(10, nbRtt);
    }
}
