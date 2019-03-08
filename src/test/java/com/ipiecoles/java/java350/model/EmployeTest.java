package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class EmployeTest {

    //#region testGetNombreAnneeAnciennete()
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
    void testGetNombreAnneeAncienneteMoinsAnnee() {
        // Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        // When
        Integer nbAnneeAnciennete = e.getNombreAnneeAnciennete();

        // Then
        assertThat(nbAnneeAnciennete).isEqualTo(2);
    }
    @Test
    void testGetNombreAnneeAnciennetePlusAnnee() {
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

    //#region testGetPrimeAnnuel()
    @ParameterizedTest
    @CsvSource({
            "'T12345', 1, 0, 1.0, 1000.00",
            "'C12345', 1, 2, 0.5, 600.00",
            "'M12345', 1, 1, 0.7, 1260.00"
            })
    void testGetPrimeAnnuelle(String matricule, Integer performance, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        //Given
        Employe employe = new Employe(
                "Covert",
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

    //#region testAugmenterSalaire()
    @Test
    public void testAugmenterSalaireSalaireNull(){
        //Given
        Throwable throwable;
        Double augmentation = 1.20D;
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
        Double augmentation = 1.20D;
        Employe e = new Employe();
        Double salaire = 0.00D;

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
        Double salaire = 1500.00D;

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
        Double augmentation = 0.00D;
        Employe e = new Employe();
        Double salaire = 1500.00D;

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
        Double augmentation = -0.50D;
        Employe e = new Employe();
        Double salaire = 1500.00D;

        e.setSalaire(salaire);

        //When
        throwable = catchThrowable(() -> e.augmenterSalaire(augmentation));

        // Then
        assertThat(throwable).isInstanceOf(EmployeException.class).hasMessage("Taux insuffisant.");
    }

    @Test
    public void testAugmenterSalaireTauxPositif() throws EmployeException {
        //Given
        Double augmentation = 0.2D;
        Employe e = new Employe();
        Double salaire = 1500.00D;

        e.setSalaire(salaire);

        //When
        e.augmenterSalaire(augmentation);

        //Then
        assertThat(e.getSalaire()).isEqualTo(salaire * (1.00D+augmentation));
    }
    //#endregion

    //#region testGetNbRtt()

    /**
     * Test de récupération du nombre de rtt
     * @param annee Année
     * @param tempsPartiel Taux de d'activité
     * @param attendu Nombre de rtt attendu
     */
    @ParameterizedTest
    @CsvSource({
            "2019, 0D, 0",
            "2019, 0.5D, 4",
            "2019, 1D, 8",
            "2021, 0D, 0",
            "2021, 0.5D, 5",
            "2021, 1D, 10",
            "2032, 0D, 0",
            "2032, 0.5D, 6",
            "2032, 1D, 11"
    })
    public void testGetNbRtt(int annee, double tempsPartiel, int attendu) {
        //Given
        Integer nbRtt;
        Employe e = new Employe();

        e.setDateEmbauche(LocalDate.now());
        e.setTempsPartiel(tempsPartiel);

        //When
        nbRtt = e.getNbRtt(LocalDate.now().withYear(annee));

        //Then
        assertThat(nbRtt).isEqualTo(attendu);
    }
    //#endregion
}