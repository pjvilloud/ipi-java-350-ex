package com.ipiecoles.java.java350.model;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeRepositoryTest {

    @Autowired
    EmployeRepository empRepo;

    @BeforeEach
    void init()
    {
        empRepo.deleteAll();
    }

    @Test
    void findLastMatricule(){
        //Given


        //When
        String matricule = empRepo.findLastMatricule();

        //Then
        Assertions.assertNull(matricule);
    }
}
