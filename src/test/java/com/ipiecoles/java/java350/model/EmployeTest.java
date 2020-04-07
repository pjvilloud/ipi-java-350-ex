package com.ipiecoles.java.java350.model;

//import org.junit.jupiter.api.Assertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest extends Assertions {

    @Test
    public void testNbAnneeAncienneteNow() {

        // Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());
        // When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();
        // Then
//        assertEquals(0, nbAnnees);
        Assertions.assertThat(0).isEqualTo(nbAnnees);

    }

    @Test
    public void testNbAnneeAncienneteNowMoins2() {

        // Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));
        // When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();
        // Then
        Assertions.assertThat(2).isEqualTo(nbAnnees);
    }

    @Test
    public void testNbAnneeAncienneteNowPlus3() {

        // Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(3));
        // When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();
        // Then
//        assertEquals(0,nbAnnees);
        Assertions.assertThat(0).isEqualTo(nbAnnees);
    }

    @Test
    public void getNombreAnneeAncienneteNplus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
//        assertEquals(-2, anneeAnciennete.intValue());
        Assertions.assertThat(-2).isEqualTo(anneeAnciennete.intValue());
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
//        Assertions.assertEquals(primeAnnuelle, prime);
        Assertions.assertThat(primeAnnuelle).isEqualTo(prime);

    }

    @Test
    public void testaugmenterSalaire() throws Exception {
        // Given
        Employe employe = new Employe("Doe", "John", "C0000", LocalDate.now(), 1000.0, 1, 10.0);

        // When
        employe.augmenterSalaire(20);

        // Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1200);
    }

    @Test
    public void testaugmenterSalaireNegatif() {
        // Given
        Employe employe = new Employe("Doe", "John", "C0000", LocalDate.now(), 1000.0, 1, 10.0);

        // When // Then
        Assertions.assertThatThrownBy(() -> {
            employe.augmenterSalaire(-5);
                }
        )
                .isInstanceOf(Exception.class)
                .hasMessage("Le pourcentage ne peut pas être négatif");

    }

    @Test
    public void testaugmenterSalaireNull() {
        // Given
        Employe employe = new Employe("Doe", "John", "C0000", LocalDate.now(), null, 1, 10.0);

        // When // Then
        Assertions.assertThatThrownBy(() -> {
                    employe.augmenterSalaire(2);
                }
        )
                .isInstanceOf(Exception.class)
                .hasMessage("Le salaire est nulle");
    }

    @Test
    public void testaugmenterSalairePourcentage0() {
        // Given
        Employe employe = new Employe("Doe", "John", "C0000", LocalDate.now(), 1000.0, 1, 10.0);

        // When // Then
        Assertions.assertThatThrownBy(() -> {
                    employe.augmenterSalaire(0);
                }
        )
                .isInstanceOf(Exception.class)
                .hasMessage("Le pourcentage ne peut pas être 0");
    }

}
