package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void beforeEach(){
        this.employeRepository.deleteAll();
    }

    //#region testFindLastMatricule
    @Test
    public void testFindLastMatriculeEmpty(){
        //Given

        //When
        String lastMatricule = this.employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();
    }

    @Test
    public void testFindLastMatriculeSingle(){
        //Given
        this.employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        //When
        String lastMatricule = this.employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("12345");
    }

    @Test
    public void testFindLastMatriculeMultiple(){
        //Given
        this.employeRepository.save(new Employe("Covert", "Harry", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        this.employeRepository.save(new Employe("Euro", "Mac", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        this.employeRepository.save(new Employe("Aimichel", "Jackie", "M40325", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        //When
        String lastMatricule = this.employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("40325");
    }
    //#endregion
}