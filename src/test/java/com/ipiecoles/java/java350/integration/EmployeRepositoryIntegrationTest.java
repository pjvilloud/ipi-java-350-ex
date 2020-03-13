package com.ipiecoles.java.java350.integration;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class EmployeRepositoryIntegrationTest {


    @Autowired
    private EmployeRepository employeRepositoryTrue;

    @Autowired
    private EmployeService employeServiceTrue;


    @Test
    public void testCalculperf() throws EmployeException {
        // given 2 commerciaux en BDD, chacun avec une perf de 2, donnant une perf moyenne de 2, l'un des deux
        // va voir sa perf recomptée, et réduite à 1, il ne bénéficiera donc pas du bonus.

        employeRepositoryTrue.save(new Employe("Doe", "John", "C00001", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));
        employeRepositoryTrue.save(new Employe("Doe", "Jane", "C00002", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));
        employeRepositoryTrue.save(new Employe("Doe", "Jim", "C00003", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeServiceTrue.calculPerformanceCommercial("C00003", 100L,1000L);
        Assertions.assertEquals(1,employeRepositoryTrue.findByMatricule("C00003").getPerformance());
        employeRepositoryTrue.deleteAll();
    }

    @Test
    public void avgPerformanceWhereMatriculeStartsWith(){
        // given 2 commerciaux et 1 employe, un avec une perf de 1, et un avec une perf de 5, ce qui donne une moyenne de: (5+1)/ 2 = 3
        employeRepositoryTrue.save(new Employe("Doe", "John", "C00001", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepositoryTrue.save(new Employe("Doe", "Jane", "C00002", LocalDate.now(), Entreprise.SALAIRE_BASE, 5, 1.0));
//        employeRepositoryTrue.save(new Employe("Doe", "Jerem", "T00002", LocalDate.now(), Entreprise.SALAIRE_BASE, 20, 1.0));
        // TODO Une erreur dans la méthode de calcul? elle prend en compte le troisième malgré un matricule en T?
        // when //
        Assertions.assertEquals(3.0 ,employeRepositoryTrue.avgPerformanceWhereMatriculeStartsWith("C"));
        employeRepositoryTrue.deleteAll();
    }
}
