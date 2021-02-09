package com.ipiecoles.java.java350.repository;


import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

//@ExtendWith(SpringExtension.class) //Junit 5
//@DataJpaTest  // ou @SpringBootTest ==> cette ligne suffit pour tester le repo tout seul (mais il faut aussi celle du dessus)
//@ContextConfiguration(classes = {Java350Application.class}) //Pour définir le contexte de l'appli (spécifique pour l'appli (car il y a son nom))

@SpringBootTest //==> dans le cas ou on utilise Spring Boot seule cette ligne suffit (pour définir le context)
class EmployeRepositoryTest {

    //on importe la classe pour la tester
    @Autowired
    EmployeRepository employeRepository;

    //TU (<= même si on fait un peu d'insertion)  pour tester le dernier numéro de matricule enregistré en bdd
    @Test
    public void testFindLastMatriculeUnEmploye(){
        //given
        //insert des données en base
        employeRepository.save(new Employe("Doe", "John", "T012345", LocalDate.now(), 1500d, 1, 1.0));
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), 1500d, 1, 1.0));
        employeRepository.save(new Employe("Doe", "John", "T2345", LocalDate.now(), 1500d, 1, 1.0));
        employeRepository.save(new Employe("Doe", "John", "T345", LocalDate.now(), 1500d, 1, 1.0));

        //when
        //execute la requête dans la bdd
        String lastMatricule = employeRepository.findLastMatricule(); //la méthode enlève la lettre pour ne donner que le nombre

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("345");
    }


    //TU : ATTENTION PROBLEME D'ENTANCHEITE DES TESTS car si on teste les 2 méthodes la deuxième plante car pour lui il y a des employé en bdd
    @Test
    public void testFindLastMatricule0Employe(){
        //given
        //insert des données en base

        //when
        //execute la requête dans la bdd
        String lastMatricule = employeRepository.findLastMatricule(); //la méthode enlève la lettre pour ne donner que le nombre

        //Then
        Assertions.assertThat(lastMatricule).isNull(); //car on test 0
    }

}