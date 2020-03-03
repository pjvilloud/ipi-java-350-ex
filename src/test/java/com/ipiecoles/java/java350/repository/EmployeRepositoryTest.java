package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;

@ExtendWith(SpringExtension.class) //Junit 5
@SpringBootTest // ou  @DataJpaTest
public class EmployeRepositoryTest {

    @Autowired EmployeRepository employeRepository;

//    @BeforeEach
//    public void before() {
//        employeRepository.deleteAll();
//    }

    @AfterEach
    public void before() {
        employeRepository.deleteAll();
    }

    @Test
    public void findLastMatriculeTest() {

        Employe e1 = new Employe("Nom", "Joe", "T12345", LocalDate.now(), 1550d,1,1.0);
        Employe e2 = new Employe("Name", "Poe", "T78596", LocalDate.now(), 1550d,1,1.0);
        Employe e3 = new Employe("SonNom", "Soe", "C59524", LocalDate.now(), 1550d,1,1.0);
        Employe e4 = new Employe("TonNom", "Toe", "M89542", LocalDate.now(), 3250d,1,1.0);
        Employe e5 = new Employe("TonName", "Loe", "M23644", LocalDate.now(), 3250d,1,1.0);

        employeRepository.saveAll(Arrays.asList(e1,e2,e3,e4,e5));

        String lastMatricule = employeRepository.findLastMatricule();

        Assertions.assertThat(lastMatricule).isEqualTo("89542");
    }

    @Test
    public void findLastMatriculeNullTest() {

        String lastMatricule = employeRepository.findLastMatricule();

        Assertions.assertThat(lastMatricule).isNull();
    }
}
