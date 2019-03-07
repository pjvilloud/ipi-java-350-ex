package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class EmployeTest {

    //#region testGetNombreAnneeAnciennete
    @Test
    void testGetNombreAnneeAnciennete() {
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        assertThat(nbAnneeAnciennete).isEqualTo(0);
    }
    @Test
    void testGetNombreAnneeAncienneteMinusYear() {
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        assertThat(nbAnneeAnciennete).isEqualTo(2);
    }
    @Test
    void testGetNombreAnneeAnciennetePlusYear() {
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        assertThat(nbAnneeAnciennete).isEqualTo(0);
    }
    @Test
    void testGetNombreAnneeAncienneteNull() {
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        assertThat(nbAnneeAnciennete).isEqualTo(0);
    }
    //#endregion

    //#region testGetPrimeAnnuel
    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "1, 'C12345', 2, 0.5, 600.0",
            "1, 'M12345', 1, 0.7, 1260.0"
            })
    void testGetPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        //Given
        Employe employe = new Employe("Covert",
                "Harry",
                matricule,
                LocalDate.now().minusYears(nbYearsAnciennete),
                Entreprise.SALAIRE_BASE,
                performance,
                tempsPartiel);
        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        assertThat(prime).isEqualTo(primeAnnuelle);
    }
    //#endregion

    //#region testAugmenterSalaire
    @Test
    public void testAugmenterSalaireSalaireNull(){
        //Given
        Throwable throwable;
        Double augmentation = 1.20d;
        Employe e = new Employe();

        e.setSalaire(null);

        //When
        throwable = catchThrowable(() -> e.augmenterSalaire(augmentation));

        // Then
        assertThat(throwable).isInstanceOf(EmployeException.class).hasMessage("Salaire insuffisant.");
    }

    @Test
    public void testAugmenterSalaireSalaireZero(){
        //Given
        Throwable throwable;
        Double augmentation = 1.20d;
        Employe e = new Employe();
        Double salaire = 0.00d;

        e.setSalaire(salaire);

        //When
        throwable = catchThrowable(() -> e.augmenterSalaire(augmentation));

        // Then
        assertThat(throwable).isInstanceOf(EmployeException.class).hasMessage("Salaire insuffisant.");
    }

    @Test
    public void testAugmenterSalaireTauxNull(){
        //Given
        Throwable throwable;
        Employe e = new Employe();
        Double salaire = 1500.00d;

        e.setSalaire(salaire);

        //When
        throwable = catchThrowable(() -> e.augmenterSalaire(null));

        // Then
        assertThat(throwable).isInstanceOf(EmployeException.class).hasMessage("Taux insuffisant.");
    }

    @Test
    public void testAugmenterSalaireTauxZero(){
        //Given
        Throwable throwable;
        Double augmentation = 0.00d;
        Employe e = new Employe();
        Double salaire = 1500.00d;

        e.setSalaire(salaire);

        //When
        throwable = catchThrowable(() -> e.augmenterSalaire(augmentation));

        // Then
        assertThat(throwable).isInstanceOf(EmployeException.class).hasMessage("Taux insuffisant.");
    }

    @Test
    public void testAugmenterSalaireTauxNegatif(){
        //Given
        Throwable throwable;
        Double augmentation = -0.50d;
        Employe e = new Employe();
        Double salaire = 1500.00d;

        e.setSalaire(salaire);

        //When
        throwable = catchThrowable(() -> e.augmenterSalaire(augmentation));

        // Then
        assertThat(throwable).isInstanceOf(EmployeException.class).hasMessage("Taux insuffisant.");
    }

    @Test
    public void testAugmenterSalaireTauxPositif() throws EmployeException {
        //Given
        Double augmentation = 0.2;
        Employe e = new Employe();
        Double salaire = 1500.00d;

        e.setSalaire(salaire);

        //When
        e.augmenterSalaire(augmentation);

        //Then
        assertThat(e.getSalaire()).isEqualTo(salaire * (1.00d+augmentation));
    }
    //#endregion
}