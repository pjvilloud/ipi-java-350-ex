package com.ipiecoles.java.java350.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeRepositoryTest {

    @Mock
    EmployeRepository employeRepository;

    // avant et apres chaque test :
    @BeforeEach
    void setUp(){
        employeRepository.deleteAll();
    }

    @Test
    void findLastMatricule() {
        // Given
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("T54899");

        // When
        String lastMarticule = employeRepository.findLastMatricule();

        // Then
        Assertions.assertEquals("T54899", lastMarticule);
    }

    @Test
    void avgPerformanceWhereMatriculeStartsWithTest(){

        // Given
        String premiereLettreMattricule = "M";
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith(premiereLettreMattricule)).thenReturn(2.333D);

        // When
        Double avg = employeRepository.avgPerformanceWhereMatriculeStartsWith(premiereLettreMattricule);

        // Then
        Assertions.assertEquals(2.333D, avg);
    }
}