package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
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

import javax.persistence.EntityExistsException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Test
    void testEmbaucheEmployeTechPleinTempsBts() throws EmployeException {
        //Given
        String nom = "Obama";
        String prenom = "Captain";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(null);
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("12345");

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
        Assertions.assertEquals(nom, employeCaptor.getValue().getNom());
        Assertions.assertEquals(prenom, employeCaptor.getValue().getPrenom());
        Assertions.assertEquals(tempsPartiel, employeCaptor.getValue().getTempsPartiel());
        Assertions.assertEquals("T12346", employeCaptor.getValue().getMatricule());
        Assertions.assertEquals(new Integer(0), employeCaptor.getValue().getNombreAnneeAnciennete());
        Assertions.assertEquals(1825.46, (double)employeCaptor.getValue().getSalaire().doubleValue());
    }


    @Test
    void testEmbaucheEmployeMatriculeMax() throws EmployeException {
        //Given
        String nom = "Obama";
        String prenom = "Captain";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        //When
        Assertions.assertThrows(EmployeException.class, () -> {
                employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        });

    }

    @Test
    void testEmbaucheEmployeMatriculeExists() throws EmployeException {
        //Given
        String nom = "Obama";
        String prenom = "Captain";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(new Employe());
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("12345");

        //When
        Assertions.assertThrows(EntityExistsException.class, () -> {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        });

    }
}