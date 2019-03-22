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
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cglib.core.Local;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Test
    void testEmbaucheTechnicienPleinTempsBts() throws EmployeException {
        //Given
        String nom = "Rabbit";
        String prenom = "Roger";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
        Employe e = employeCaptor.getValue();
        Assertions.assertEquals("T00001", e.getMatricule());
        Assertions.assertEquals(nom, e.getNom());
        Assertions.assertEquals(prenom, e.getPrenom());
        Assertions.assertEquals(LocalDate.now(), e.getDateEmbauche());
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, e.getPerformance());
        Assertions.assertEquals(1825.46, (double) e.getSalaire());
        Assertions.assertEquals(tempsPartiel, e.getTempsPartiel());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMasterLastMatricule00345() throws EmployeException {
        //Given
        String nom = "Duchamp";
        String prenom = "Marcel";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
        Mockito.when(employeRepository.findByMatricule("M00346")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());
        Employe e = employeCaptor.getValue();
        Assertions.assertEquals("M00346", e.getMatricule());
        Assertions.assertEquals(nom, e.getNom());
        Assertions.assertEquals(prenom, e.getPrenom());
        Assertions.assertEquals(LocalDate.now(), e.getDateEmbauche());
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, e.getPerformance());
        Assertions.assertEquals(1064.85, (double) e.getSalaire());
        Assertions.assertEquals(tempsPartiel, e.getTempsPartiel());

    }

    @Test
    public void testEmployeAlreadyExistForMatricule00100() {
        //Given
        String nom = "Durand";
        String prenom = "Pauline";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00099");
        Mockito.when(employeRepository.findByMatricule("C00100")).thenReturn(new Employe(
                "Garamond", "Jean-Pierre", "C00100", LocalDate.now(), 2000.0, 1, 1.0
        ));

        //When
        try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        } catch (Exception exception) {
            //Then
            Assertions.assertEquals("L'employé de matricule C00100 existe déjà en BDD", exception.getMessage());
        }
    }

}
