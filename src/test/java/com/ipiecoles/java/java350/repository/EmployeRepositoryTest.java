package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    public void setUpDB() {

        employeRepository.deleteAll();
    }

    @Test
    @DisplayName("Test findNullMatricule")
    public void testFindNullMatricule() {

        String lastMatricule = employeRepository.findLastMatricule();
        Assertions.assertThat(lastMatricule).isNull();

    }

    @Test
    @DisplayName("Test findByMatricule")
    public void testFindByMatricule() {

        // given
        Employe employe = new Employe();
        employe.setMatricule("M12345");
        employeRepository.save(employe);

        // when
        Employe result = employeRepository.findByMatricule("M12345");

        // then
        Assertions.assertThat(result).isEqualTo(employe);

    }

    @Test
    @DisplayName("Test findLastMatricule le plus grand  matricule")
    public void testFindLastMatricule2Employes() { //le plus grand nombre des matricules
        // given
        Employe emp1 = employeRepository.save(new Employe("Doe", "John", "M56789",
                LocalDate.now(), 1500d, 1, 1.0));
        Employe emp2 = employeRepository.save(new Employe("Doe", "Toto", "T01234",
                LocalDate.now(), 1500d, 1, 1.0));

        // when
        String lastMatricule = employeRepository.findLastMatricule();

        // then
        Assertions.assertThat(lastMatricule).isEqualTo("56789");
    }


}
