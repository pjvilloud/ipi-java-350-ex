package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    // Remplace la vrai implémentation du truc par un fictif de test :
    @Mock
    EmployeRepository employeRepository;

    @Test
    void embaucheEmploye() throws EmployeException {
        //Given
        // Quand la méthode findLast va etre appelée, on veut qu elle renvoie null pour simuler une base employé vude
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);

        // Quand on va chercher si l employé avec le matricule calcule existe, on veut que la méthode renvoie null.
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);

        //When
        employeService.embaucheEmploye("Doe","John", Poste.COMMERCIAL, NiveauEtude.LICENCE,1.0);

        //Then
        employeService.embaucheEmploye("Doe", "John", Poste.COMMERCIAL, NiveauEtude.LICENCE, 1.0);
    }

    @Test
    void embaucheEmploye2() throws EmployeException {
        //Given
        // Quand on va chercher si l employé avec le matricule calcule existe, on veut que la méthode renvoie null.
        Mockito.when(employeRepository.findByMatricule("M45679")).thenReturn(null);

        //When
        employeService.embaucheEmploye("Doe","John", Poste.COMMERCIAL, NiveauEtude.LICENCE,1.0);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertEquals(employe.getNom(), "Doe");
   }
}