package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;
    @Mock
    EmployeRepository employeRepository;

    @Test
    void embaucheEmployeTechnicienPleinTempsBts() throws EmployeException {
        //given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(null);
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("12345");
        //when
        //pour tester l'exeption
        //Mockito.when(employeRepository.findLastMatricule()).thenReturn("102300");
        /*try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("aurais du lever une exeption")
        }
        catch(EmployeException e){
            Assertions.assertEquals("message", e.getMessage());
        }*/


        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        //then
        ArgumentCaptor<Employe> EmployeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(EmployeCaptor.capture());

        Assertions.assertEquals(EmployeCaptor.getValue().getNom(),nom);
        Assertions.assertEquals(EmployeCaptor.getValue().getPrenom(),prenom);
        Assertions.assertEquals(EmployeCaptor.getValue().getMatricule(),"T12346");
        Assertions.assertEquals(EmployeCaptor.getValue().getTempsPartiel(),tempsPartiel);
        Assertions.assertEquals(EmployeCaptor.getValue().getPerformance(), Entreprise.PERFORMANCE_BASE);
        Assertions.assertEquals(EmployeCaptor.getValue().getSalaire(), (Double)1825.46);
        Assertions.assertEquals(EmployeCaptor.getValue().getDateEmbauche(), LocalDate.now());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMaster99999(){
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        //When/Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
        Assertions.assertEquals("Limite des 100000 matricules atteinte !", e.getMessage());
    }
}