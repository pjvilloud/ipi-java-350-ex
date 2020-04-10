package com.ipiecoles.java.java350.model.model;

import com.ipiecoles.java.java350.exception.SalaireException;
import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

public class EmployeTest {

    @Test
    public void testDateEmbaucheValueSameYars() {
        //Given
        Employe employe = new Employe();
        LocalDate dateEmbauche = LocalDate.now().with(firstDayOfYear());
        employe.setDateEmbauche(dateEmbauche);
        //When
        Integer date = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(date).isEqualTo(0);
    }

    @Test
    public void testDateEmbaucheLastYears() {
        //Given
        Employe employe = new Employe();
        LocalDate dateEmbauche = LocalDate.now().with(firstDayOfYear()).minusDays(1);
        employe.setDateEmbauche(dateEmbauche);
        //When
        Integer date = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(date).isEqualTo(1);
    }

    @Test
    public void testDateEmbauche() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);
        //When
        Integer date = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(date).isEqualTo(null);
    }

    @Test
    public void testDateEmbaucheSuperieurNow() {
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusDays(1L));
        //When
        Integer date = employe.getNombreAnneeAnciennete();
        //Then
        Assertions.assertThat(date).isEqualTo(0);
    }

    @ParameterizedTest()
    @CsvSource({
            "1,'M00000',0,1.0,1700.0",
            "1,'M00000',0,0.5,850.0",
            "1,'M00000',1,1.0,1800.0",
            "1000,'M00000',0,1.0,1700.0",
            "1,'E00000',0,1.0,1000.0",
            "1,'E00000',0,0.5,500",
            "1,'E00000',2,1.0,1200.0",
            "2,'E00000',0,1.0,2300.0",
            "2,'E00000',2,0.5,1250.0",
            "2,'M00000',2,0.5,950.0",
    })
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle) {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(performance);
        employe.setMatricule(matricule);
        employe.setDateEmbauche(LocalDate.now().minusYears(nbYearsAnciennete));
        employe.setTempsPartiel(tempsPartiel);

        //When
        Double primeA = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(primeA).isEqualTo(primeAnnuelle);
    }

    @Test
    public void augmenterSalaireNormal() throws SalaireException {
        //Given
        Employe employe = new Employe("Doe", "John", "C00001", LocalDate.now(), 1000.0, 1, 1.0);
        //When
        employe.augmenterSalaire(20.0);
        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1200);
    }

    @Test
    public void augmenterSalairePourcentageDessous0() {
        //Given
        Employe employe = new Employe("Doe", "John", "C00001", LocalDate.now(), 1000.0, 1, 1.0);

        Assertions.assertThatThrownBy(() -> {
                    //Then
                    employe.augmenterSalaire(-2);
                }
        )//When
                .isInstanceOf(Exception.class)
                .hasMessage("Impossible de diminuer le salaire avec la méthode augmenterSalaire");
    }

    @Test
    public void augmenterSalaireNoSalaire() {
        //Given
        Employe employe = new Employe("Doe", "John", "C00001", LocalDate.now(), null, 1, 1.0);

        Assertions.assertThatThrownBy(() -> {
                    //Then
                    employe.augmenterSalaire(2);
                }
        )//When
                .isInstanceOf(Exception.class)
                .hasMessage("L'employé n'a pas de salaire");
    }

    // Pour satisfaire Pit
    @Test
    public void augmenterSalairepourcentage0() throws SalaireException {
        //Given
        Employe employe = new Employe("Doe", "John", "C00001", LocalDate.now(), 1000.0, 1, 1.0);
        //When
        employe.augmenterSalaire(0);
        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1000);
    }
}