package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EmployeServiceTest {

    @Mock
    EmployeRepository employeRepository;
    @InjectMocks
    EmployeService employeService;

    @ParameterizedTest() //  caTraite , objectifCa,  expectedPerformance

    @CsvSource({
            " 70, 100,  1",
            " 90, 100,  8",
            " 99, 100,  10",
            " 110, 100,  12",
            " 1500, 100, 15 "
    })

    public void testCalculPerformenceCommercial(Long caTraite,
                                                Long objectifCa,
                                                Integer excpectedPerformance) throws EmployeException {
        //Given
        Integer performance = 10;
        String matricule = "C54321";
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setPerformance(performance);
        Mockito.when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(10.0);

        //WHEN
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //THEN
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, times(1))
                .save(employeCaptor.capture());

        Employe employeCaptorValue = employeCaptor.getValue();
        Assertions.assertEquals(employeCaptorValue.getPerformance(), excpectedPerformance);
    }

    @Test
    public void testEmbaucheEmployeCommercialPleinTempsBTS() throws EmployeException {

        // Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
        Mockito.when(employeRepository.findByMatricule("C00346")).thenReturn(null);

        // When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        // Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, times(1)).save(employeCaptor.capture());
        Employe employeCaptorValue = employeCaptor.getValue();
        Assertions.assertEquals(employeCaptorValue.getNom(), nom);
        Assertions.assertEquals(employeCaptorValue.getPrenom(), prenom);
        Assertions.assertEquals(employeCaptorValue.getMatricule(), "C00346");
        Assertions.assertEquals(employeCaptorValue.getDateEmbauche(), LocalDate.now());
        Assertions.assertEquals(employeCaptorValue.getTempsPartiel(), tempsPartiel);
        Assertions.assertEquals(employeCaptorValue.getPerformance(), 1);
        Assertions.assertEquals(employeCaptorValue.getSalaire(), 1825.46);
    }
    @Test
    public void testEmbaucheEmployeCommercialMiTempsMaster() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
        Mockito.when(employeRepository.findByMatricule("C00346")).thenReturn(null);

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
        Assertions.assertEquals(nom, employeArgumentCaptor.getValue().getNom());
        Assertions.assertEquals(prenom, employeArgumentCaptor.getValue().getPrenom());
        Assertions.assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), employeArgumentCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Assertions.assertEquals("C00346", employeArgumentCaptor.getValue().getMatricule());
        Assertions.assertEquals(tempsPartiel, employeArgumentCaptor.getValue().getTempsPartiel());

        //1521.22 * 1.4 * 0.5
        Assertions.assertEquals(1064.85, employeArgumentCaptor.getValue().getSalaire().doubleValue());
    }

    @Test
    public void testEmbaucheEmployeLimiteMatricule() throws EmployeException{

        // Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        // je simule que la base arrive à terme
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        // When
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
        Assertions.assertEquals("Limite des 100000 matricules atteinte !", e.getMessage());
    }

    @Test
    public void testEmbaucheEmployeCommercialMiTempsMasterExistingEmploye(){
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(new Employe());

        //When/Then
        EntityExistsException e = Assertions.assertThrows(EntityExistsException.class, () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
        Assertions.assertEquals("L'employé de matricule C00001 existe déjà en BDD", e.getMessage());
    }
}