package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootTest
public class EmployeServiceIntegrationTest {

    @Autowired
    EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;

    @AfterEach
    @BeforeEach
    public void before(){
        employeRepository.deleteAll();
    }

    @Test
    void testFindLastMatriculeWithoutEmploye() {
        //When
        String marticule = employeRepository.findLastMatricule();

        //THen
        Assertions.assertThat(marticule).isNull();
    }

    @Test
    void testEmbaucheEmploye() throws EmployeException {

        employeService.embaucheEmploye("Doe", "John", Poste.COMMERCIAL, NiveauEtude.MASTER, 1.0);


        Employe employe = employeRepository.findByMatricule("C00001");

        Assertions.assertThat(employe.getNom()).isEqualTo("Doe");
        Assertions.assertThat(employe.getPrenom()).isEqualTo("John");
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getSalaire()).isEqualTo(2129.71);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1);
    }

    @Test
    void testFindLastMatriculeWithEmploye() {
        //GIven
        Employe employe1 = new Employe("Doe", "John", "C12345", LocalDate.now(), 2500d, 1, 1.0);
        Employe employe2 = new Employe("juju", "sisi", "M12342", LocalDate.now(), 2500d, 1, 1.0);
        Employe employe3 = new Employe("lam", "coucou", "T12341", LocalDate.now(), 2500d, 1, 1.0);
        employeRepository.saveAll(Arrays.asList(employe1,employe2,employe3));

        //When
        String marticule = employeRepository.findLastMatricule();

        //THen
        Assertions.assertThat(marticule).isEqualTo("12345");
    }
}
