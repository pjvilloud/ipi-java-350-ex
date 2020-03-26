package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeRepository employeRepository;

    @Test
    void embaucheEmploye0Employe() throws EmployeException {
        //Given
        //Quand la méthode findLastMatricule va être appelée, on veut qu'elle renvoie null pour simuler une base emplyé vide
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);

        //Quand on va chercher si l'employé avec le matricule calculé existe, on veut que la méthode renvoie null
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);

        //When
        employeService.embaucheEmploye("Doe","John", Poste.COMMERCIAL, NiveauEtude.LICENCE,1.0);
        //Then
    }

    @Test
    void embaucheEmployeXEmploye() throws EmployeException {
        //Given
        //Quand la méthode findLastMatricule va être appelée, on veut qu'elle renvoie une valeur comme s'il y avait plusieurs employés, dont le matricule le plus élevé est C45678
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("45678");

        //Quand on va chercher si l'employé avec le matricule calculé existe, on veut que la méthode renvoie null
        Mockito.when(employeRepository.findByMatricule("M45678")).thenReturn(null);

        //When
        employeService.embaucheEmploye("Doe","John", Poste.MANAGER, NiveauEtude.LICENCE,1.0);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
//        Assertions.AssertThat(employe.getNom()).isEqualTo("Doe");
        Assertions.assertEquals("Doe",employe.getNom());
    }
}