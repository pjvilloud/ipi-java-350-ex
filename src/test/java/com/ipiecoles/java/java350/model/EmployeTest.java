package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.internal.matchers.Null;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeTest {

    @Test
    void getNombreAnneeAncienneteEmbaucheNow() {
        // Given
        Employe emp = new Employe("SMITH", "John", "EMP01", LocalDate.now(), 5000.0, 1, 1.0);

        //When
        int res = emp.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(res).isEqualTo(0);
    }

    @Test
    void getNombreAnneeAncienneteEmbaucheNull() {
        // Given
        Employe emp = new Employe("SMITH", "John", "EMP01", null, 5000.0, 1, 1.0);


        try{
            //When
            int res = emp.getNombreAnneeAnciennete();
        }catch(Exception e){
            //Then
            Assertions.assertThat(e).isInstanceOf(NullPointerException.class);
        }


    }

    @Test
    void getNombreAnneeAncienneteEmbaucheFuture() {
        // Given
        Employe emp = new Employe("SMITH", "John", "EMP01", LocalDate.of(2022,11,01), 5000.0, 1, 1.0);

        //When
        int res = emp.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(res).isEqualTo(0);
    }

    @Test
    void getNombreAnneeAncienneteEmbauchePast() {
        // Given
        Employe emp = new Employe("SMITH", "John", "EMP01", LocalDate.of(2020,10,01), 5000.0, 1, 1.0);

        //When
        int res = emp.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(res).isEqualTo(1);
    }


    @ParameterizedTest(name = "Augmentation de {1}% sur {0} euros : {2}")
    @CsvSource({
            "1000.0, 8.0, 1080.0",
            "2500.0, 100.0, 5000.0",
            "2000.0, 10.0, 2200.0",
            "1500.0, 0.1, 1501.5",
            "1000.0, 0, 1000.0"
    })
    void testAugmenterSalaire(double salaireBase, double pourcentage, double salaireAugmente){
        //Given
        Employe employe = new Employe();
        employe.setSalaire(salaireBase);

        //When
        employe.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertThat(employe.getSalaire()).isEqualTo(salaireAugmente);
    }
}