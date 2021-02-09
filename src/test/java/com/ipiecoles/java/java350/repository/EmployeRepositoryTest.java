package com.ipiecoles.java.java350.repository;


import com.ipiecoles.java.java350.Java350Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ExtendWith(SpringExtension.class) //Junit 5
//@DataJpaTest  // ou @SpringBootTest ==> cette ligne suffit pour tester le repo tout seul
//@ContextConfiguration(classes = {Java350Application.class}) //Pour définir le contexte de l'appli (spécifique pour l'appli (car il y a son nom))
@SpringBootTest //==> dans le cas ou on utilise Spring Boot
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