package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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
        Employe emp = new Employe("SMITH", "John", "EMP01", LocalDate.now().plusYears(5), 5000.0, 1, 1.0);

        //When
        int res = emp.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(res).isEqualTo(0);
    }

    @Test
    void getNombreAnneeAncienneteEmbauchePast() {
        // Given
        Employe emp = new Employe("SMITH", "John", "EMP01", LocalDate.now().minusYears(5), 5000.0, 1, 1.0);

        //When
        int res = emp.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(res).isEqualTo(5);
    }
}