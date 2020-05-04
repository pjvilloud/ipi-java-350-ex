package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
    void testAugmenterSalaire_Negatif_IllegalArgumentException(){
        // 0 - Arrange
        Employe employe = getNormalEmploye();
        employe.setSalaire(1000d);

        // 1, 2 - Act && Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> employe.augmenterSalaire(-0.5));
    }

    @Test
    void testAugmenterSalaire_Zero_Succes(){
        // 0 - Arrange
        Employe employe = getNormalEmploye();
        employe.setSalaire(1000d);

        // 1 - Act
        employe.augmenterSalaire(0d);

        // 2 - Assert
        org.assertj.core.api.Assertions.assertThat(employe.getSalaire()).isEqualTo(1000d);
    }

    @Test
    void testAugmenterSalaire_Positif_Succes(){
        // 0 - Arrange
        Employe employe = getNormalEmploye();
        employe.setSalaire(1000d);

        // 1 - Act
        employe.augmenterSalaire(0.5d);

        // 2 - Assert
        org.assertj.core.api.Assertions.assertThat(employe.getSalaire()).isEqualTo(1500d);
    }

    @Test
    void testAugmenterSalaire_TropGrand_IllegalArgumentException(){
        // 0 - Arrange
        Employe employe = getNormalEmploye();
        employe.setSalaire(1000d);

        // 1, 2 - Act && Assert
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> employe.augmenterSalaire(1.5));
    }

    @Test
    void testGetNbRtt_1Ferie_Succes(){
        // 0 - Arrange
        List<LocalDate> joursFerie = Arrays.asList(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 01, 05));
        Employe mockedEmploye = Mockito.spy(getNormalEmploye());
        when(mockedEmploye.getNbConges()).thenReturn(1);

        // 1 - Act
        Integer nbRtt = mockedEmploye.getNbRtt(LocalDate.of(2019, 1, 1), joursFerie);

        // 2 - Assert
        org.assertj.core.api.Assertions.assertThat(nbRtt).isEqualTo(41);
    }

    @Test
    void testGetNbRtt_MiTemps_Succes(){
        // 0 - Arrange
        Employe mockedEmploye = Mockito.spy(getNormalEmploye());
        mockedEmploye.setTempsPartiel(0.5);
        when(mockedEmploye.getNbConges()).thenReturn(0);

        // 1 - Act
        Integer nbRtt = mockedEmploye.getNbRtt(LocalDate.of(2019, 1, 1), Collections.emptyList());

        // 2 - Assert
        org.assertj.core.api.Assertions.assertThat(nbRtt).isEqualTo(22);
    }

    @ParameterizedTest
    @CsvSource({
            "'2019-01-01', 43", //base
            "'2020-01-01', 44", //bissextile normal
            "'2018-01-01', 43", //lundi normal
            "'2019-01-01', 43", //mardi normal
            "'2025-01-01', 43", //mercredi normal
            "'2026-01-01', 42", //jeudi normal
            "'2021-01-01', 42", //vendredi normal
            "'2022-01-01', 42", //samedi normal
            "'2023-01-01', 43", //dimanche normal
            "'2024-01-01', 44", //lundi bissextile
            "'2036-01-01', 44", //mardi bissextile
            "'2020-01-01', 44", //mercredi bissextile
            "'2032-01-01', 43", //jeudi bissextile
            "'2044-01-01', 42", //vendredi bissextile
            "'2029-01-01', 43", //samedi bissextile
            "'2040-01-01', 44", //dimanche bissextile
    })
    void testGetNbRtt_AnneeParametree_Succes(LocalDate jourNbRtt, Integer expectedRtt) {
        // 0 - Arrange
        Employe mockedEmploye = Mockito.spy(getNormalEmploye());
        when(mockedEmploye.getNbConges()).thenReturn(0);

        // 1 - Act
        Integer nbRtt = mockedEmploye.getNbRtt(jourNbRtt, Collections.emptyList());

        // 2 - Assert
        org.assertj.core.api.Assertions.assertThat(nbRtt).isEqualTo(expectedRtt);
    }

    static Employe getNormalEmploye(){
        return new Employe("nom", "prenom", "matricule", LocalDate.now(), 1500d, 1, 1d);
    }

}