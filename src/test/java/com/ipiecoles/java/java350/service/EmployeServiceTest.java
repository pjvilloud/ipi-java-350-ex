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
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

    @Mock
    EmployeRepository employeRepository;

    @InjectMocks
    EmployeService employeService;

    @Test
    public void embaucheEmployeTest() throws EmployeException {
    String nom = "Name";
    String prenom = "Prenom";
    Poste poste = Poste.TECHNICIEN;
    NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
    Double tempsPartiel = 1.0;

    when(employeRepository.findLastMatricule()).thenReturn("00345");
    when(employeRepository.findByMatricule("T00346")).thenReturn(null);

    employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

    // simulation d'un save : ici on récupère l'objet généré par le .save dans la méthode embauchEmploye
    ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
    verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
    Assertions.assertEquals(prenom, employeArgumentCaptor.getValue().getPrenom());
    Assertions.assertEquals(nom, employeArgumentCaptor.getValue().getNom());
    Assertions.assertEquals("T00346", employeArgumentCaptor.getValue().getMatricule());
    Assertions.assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), employeArgumentCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMaster99999(){
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        when(employeRepository.findLastMatricule()).thenReturn("99999");

        //When/Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
        Assertions.assertEquals("Limite des 100000 matricules atteinte !", e.getMessage());
    }
}
