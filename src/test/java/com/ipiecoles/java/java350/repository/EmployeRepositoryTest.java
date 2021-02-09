package com.ipiecoles.java.java350.repository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//les 2 lignes @ chargent les données du context, c'est indispensable pour faire les tests
@ExtendWith(SpringExtension.class) //Junit 5
@DataJpaTest  // ou @SpringBootTest ==> cette ligne suffit pour tester le repo tout seul
class EmployeRepositoryTest {

    //on importe la classe pour la tester
    @Autowired
    EmployeRepository employeRepository;

    @Test
    public void test(){
        //given
        //insert des données en base

        //when
        employeRepository.findLastMatricule();

        //Then

    }
}