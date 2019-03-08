package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeRepositoryIntegrationTest {
    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    public void beforeEach() {
        this.employeRepository.deleteAll();

        this.employeRepository.save(new Employe(
                "a",
                "a",
                "C00123",
                LocalDate.now(),
                1000D,
                2,
                1D));
        this.employeRepository.save(new Employe(
                "aa",
                "aa",
                "T00124",
                LocalDate.now(),
                1000D,
                4,
                1D));
        this.employeRepository.save(new Employe(
                "aaa",
                "aaa",
                "C00125",
                LocalDate.now(),
                1000D,
                6,
                1D));
        this.employeRepository.save(new Employe(
                "aaaa",
                "aaaa",
                "M00126",
                LocalDate.now(),
                1000D,
                8,
                1D));
        this.employeRepository.save(new Employe(
                "aaaaa",
                "aaaaa",
                "C00127",
                LocalDate.now(),
                1000D,
                10,
                1D));
    }

    //#region testAvgPerformanceWhereMatriculeStartsWith()
    @Test
    public void testAvgPerformanceWhereMatriculeStartsWithC() {
        //Given
        Double moyenneOk = (2D + 6D + 10D) / 3D;

        //When
        Double moyenne = this.employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        Assertions.assertThat(moyenne).isEqualTo(moyenneOk);

    }

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWithLettreInconnue() {
        //Given
        String lettre = "X";

        //When
        Double result = this.employeRepository.avgPerformanceWhereMatriculeStartsWith(lettre);

        //Then
        Assertions.assertThat(result).isNull();

    }

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWithPlusieursLettres() {
        //Given
        String lettre = "KAMELEON";

        //When
        Double result = this.employeRepository.avgPerformanceWhereMatriculeStartsWith(lettre);
        //Then
        Assertions.assertThat(result).isNull();

    }

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWithNull() {
        //Given
        String lettre = null;

        //When
        Double result = this.employeRepository.avgPerformanceWhereMatriculeStartsWith(lettre);

        //Then
        Assertions.assertThat(result).isNull();

    }
    //#endregion
}
