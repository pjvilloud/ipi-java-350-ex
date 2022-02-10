package com.ipiecoles.java.java350.repository;
import com.ipiecoles.java.java350.model.Employe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
@SpringBootTest
public class EmployeRepositoryTest {
    @Autowired
    private EmployeRepository employeRepository;
    @BeforeEach
    @AfterEach
    public void deleteInBDD(){
        employeRepository.deleteAll();
    }
    @Test
    public void testFindLastMatricule0Employe(){
        //Given
        //When
        String lastMatricule = employeRepository.findLastMatricule();
        //Then
        Assertions.assertThat(lastMatricule).isNull();
    }
    @Test
    public void testFindLastMatricule3Employes(){
        //Given
        Employe e2 = new Employe("Doe", "Jane", "C66666", LocalDate.now(), 2000d, 1, 1.0);
        Employe e3 = new Employe("Doe", "Paul", "T22222", LocalDate.now(), 2000d, 1, 1.0);
        employeRepository.save(new Employe("Doe", "John", "M44444", LocalDate.now(), 2000d, 1, 1.0));
        employeRepository.save(e2);
        employeRepository.save(e3);
        //When
        String lastMatricule = employeRepository.findLastMatricule();
        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("66666");
    }

}