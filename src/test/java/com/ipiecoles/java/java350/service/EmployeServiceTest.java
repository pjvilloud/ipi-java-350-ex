package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    //element que l'on teste
    @InjectMocks
    EmployeService employeService;

    //element que l'on va injecter dans @InjectMocks
    @Mock
    EmployeRepository employeRepository;

    @Test
    void embaucheEmploye0Employe() throws EmployeException {
        //Given
        //Quand la méthode findLastMatricule va être appellée, on veut qu'elle renvoie null, pour simuler une base employé vide
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);

        //Quand on va chercher si l'employé avec le matricule calculé existe, on veut que la méthode renvoie null également
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);

        //When
        employeService.embaucheEmploye("Doe","John", Poste.COMMERCIAL, NiveauEtude.LICENCE,1.0);

        //Then
    }
}