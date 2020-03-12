package com.ipiecoles.java.java350.model;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class EmployeTest {

    @Test
    @DisplayName("Test AugmenterSalaire avec pourcentage négatif => salaire identique")
    public void testAugmenterSalaire() throws Exception {

        //Given
        Employe employe = new Employe();
        employe.setSalaire(1000.0);

        //When
        //Integer nbAnnees = employe.getNombreAnneeAnciennete();
        Double nouveauSalaire = employe.augmenterSalaire( -0.5 );
        //Double pourcentage=-0.5;
        //Then
        //Assertions.assertThat(nbAnnees).isEqualTo(2);
        Assertions.assertThat(nouveauSalaire).isEqualTo(employe.getSalaire());
    }

    @Test
    @DisplayName("Test AugmenterSalaire avec salaire multiplié par 4 soit pourcentage de 300%")
    public void testAugmenterSalaireFois4() throws Exception {

        //Given
        Employe employe = new Employe();
        employe.setSalaire(1000.0);

        //When
        //Integer nbAnnees = employe.getNombreAnneeAnciennete();
        Double nouveauSalaire = employe.augmenterSalaire( 3.0 );
        //Double pourcentage=-0.5;
        //Then
        //Assertions.assertThat(nbAnnees).isEqualTo(2);
        Assertions.assertThat(nouveauSalaire).isEqualTo(4000.0);
    }

    //Employé dateEmbauche avec date 2 ans avant aujourd'hui =>
    //2 années d'ancienneté
    @Test
    @DisplayName("Test getNombreAnneeAnciennete a N-2")
    public void testAncienneteDateEmbaucheNmoins2() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(2);
    }

    //Date dans le futur => 0
    @Test
    @DisplayName("Test getNombreAnneeAnciennete a N+2")
    public void testAncienneteDateEmbaucheNplus2() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    //Date aujourd'hui => 0
    @Test
    @DisplayName("Test getNombreAnneeAnciennete a N now")
    public void testAncienneteDateEmbaucheAujourdhui() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    //Date d'embauche indéfinie => 0
    @Test
    @DisplayName("Test getNombreAnneeAnciennete a N null")
    public void testAncienneteDateEmbaucheNull() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);

    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",

            "1, 'T12345', 0, 0.5, 500.0",
            "1, 'M12345', 0, 1.0, 1700.0",
            "2, 'T12345', 0, 1.0, 2300.0",
    })
    @DisplayName("Test parametré Get Prime Annuelle")
    public void testGetPrimeAnnuelle(Integer performance,
                                     String matricule,
                                     Integer nbAnneesAnciennete,
                                     Double tempsPartiel,
                                     Double prime
    ) {

        // Given
        Employe e = new Employe();
        e.setMatricule(matricule);
        e.setDateEmbauche(LocalDate.now().minusYears(nbAnneesAnciennete));
        e.setPerformance(performance);
        e.setTempsPartiel(tempsPartiel);

        //When
        Double result = e.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(result).isEqualTo(prime);
    }
}

