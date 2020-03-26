package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class) //Permet de faire des test unitaires sur des éléments possédant des dépendances
public class EmployeServiceTest {

    @Mock
    EmployeRepository employeRepository;

    @InjectMocks
    EmployeService employeService;

    @Test
    void embaucheEmploye() throws EmployeException {
        //Given

        //When
        employeService.embaucheEmploye(null, null, null,null, null);
        //Then
    }

    @Test
    void testEmbauchEmployeLimiteMatricule() {
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(null);

        try {
            employeService.embaucheEmploye("Doe", "John", Poste.MANAGER, NiveauEtude.BTS_IUT, 1.0);
            Assertions.fail("Aurait du lancer une exection");
        } catch (EmployeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
        }
    }

}
