package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest 
{

    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeRepository employeRepository;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.initMocks(this.getClass());
    }

    @Test
    public void testEmbaucheEmployeTechnicienPleinTempsBts() throws EmployeException 
    {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        when(employeRepository.findLastMatricule()).thenReturn("00345");
        when(employeRepository.findByMatricule("T00346")).thenReturn(null);

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
        Assertions.assertEquals(nom, employeArgumentCaptor.getValue().getNom());
        Assertions.assertEquals(prenom, employeArgumentCaptor.getValue().getPrenom());
        Assertions.assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), employeArgumentCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Assertions.assertEquals("T00346", employeArgumentCaptor.getValue().getMatricule());
        Assertions.assertEquals(tempsPartiel, employeArgumentCaptor.getValue().getTempsPartiel());

        //1521.22 * 1.2 * 1.0
        Assertions.assertEquals(1825.46, employeArgumentCaptor.getValue().getSalaire().doubleValue());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMaster() throws EmployeException 
    {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        when(employeRepository.findLastMatricule()).thenReturn("00345");
        when(employeRepository.findByMatricule("M00346")).thenReturn(null);

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
        Assertions.assertEquals(nom, employeArgumentCaptor.getValue().getNom());
        Assertions.assertEquals(prenom, employeArgumentCaptor.getValue().getPrenom());
        Assertions.assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), employeArgumentCaptor.getValue().getDateEmbauche().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Assertions.assertEquals("M00346", employeArgumentCaptor.getValue().getMatricule());
        Assertions.assertEquals(tempsPartiel, employeArgumentCaptor.getValue().getTempsPartiel());

        //1521.22 * 1.4 * 0.5
        Assertions.assertEquals(1064.85, employeArgumentCaptor.getValue().getSalaire().doubleValue());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMasterNoLastMatricule() throws EmployeException 
    {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        when(employeRepository.findLastMatricule()).thenReturn(null);
        when(employeRepository.findByMatricule("M00001")).thenReturn(null);

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
        Assertions.assertEquals("M00001", employeArgumentCaptor.getValue().getMatricule());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMasterExistingEmploye()
    {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;
        when(employeRepository.findLastMatricule()).thenReturn(null);
        when(employeRepository.findByMatricule("M00001")).thenReturn(new Employe());

        //When/Then
        EntityExistsException e = Assertions.assertThrows(EntityExistsException.class, () -> employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel));
        Assertions.assertEquals("L'employé de matricule M00001 existe déjà en BDD", e.getMessage());
    }

    @Test
    public void testEmbaucheEmployeManagerMiTempsMaster99999()
    {
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
    
    @ParameterizedTest
	@CsvSource(
			{
				"1, 'C12345', 0, 1.0, 1000.0, 1000, 10, 75, 100",
	            "1, 'C12345', 2, 0.5, 600.0, 1000, 10, 90, 100",
	            "1, 'C12345', 2, 1.0, 1200.0, 1000, 10, 98, 100",
	            "2, 'C12345', 0, 1.0, 2300.0, 1000, 10, 107, 100",
	            "2, 'C12345', 1, 1.0, 2400.0, 1000, 10, 130, 100"
			})
    public void testCalculPerformanceCommercial(Integer performance, String matricule, Integer nbAnneesAnciennete, Double TempsPartiel, Double primeAnnuelle, Double salaire, Double pourcentage, Long caTraite, Long objectifCa) throws EmployeException
    {
    	
    	//Given
    	Employe employe = new Employe();
    	employe.setMatricule(matricule);
    	employe.setPerformance(performance);
    	employe.setDateEmbauche(LocalDate.now().minusYears(nbAnneesAnciennete));
    	employe.setTempsPartiel(TempsPartiel);
    	employe.setSalaire(salaire);
    	employe.setNom("Doe");
    	employe.setPrenom("John");
    	
    	//String nom = "Doe";
        //String prenom = "John";
        //Poste poste = Poste.COMMERCIAL;
        //NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        //Double tempsPartiel = 1.0;
         
        //when(employeRepository.findLastMatricule()).thenReturn("00345");
        when(employeRepository.findByMatricule("C12345")).thenReturn(employe);

         //When
         //employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
    	
        //Cas 2
        if(caTraite >= objectifCa*0.8 && caTraite < objectifCa*0.95)
        {
        	performance = Math.max(Entreprise.PERFORMANCE_BASE, employe.getPerformance() - 2);
        }
            
        //Cas 3
        else if(caTraite >= objectifCa*0.95 && caTraite <= objectifCa*1.05)
        {
            performance = Math.max(Entreprise.PERFORMANCE_BASE, employe.getPerformance());
        }
        
        //Cas 4
        else if(caTraite <= objectifCa*1.2 && caTraite > objectifCa*1.05)
        {
            performance = employe.getPerformance() + 1;
        }
        
        //Cas 5
        else if(caTraite > objectifCa*1.2)
        {
            performance = employe.getPerformance() + 4;
        }
            
        //Si autre cas, on reste à la performance de base.

        //Calcul de la performance moyenne
        Double performanceMoyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        if(performanceMoyenne != null && performance > performanceMoyenne)
        {
            performance++;
        }
        
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        Assertions.assertEquals(performance, employe.getPerformance());
    
    }
    
    @ParameterizedTest
	@CsvSource(
			{
				"90, 100"
			})
    
    public void testIntegreCalculPerformanceCommercial(Long caTraite, Long objectifCa) throws EmployeException
    {
    	//Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Integer performance = Entreprise.PERFORMANCE_BASE;

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
        
       String matricule = employeRepository.findLastMatricule();
       Employe employe = employeRepository.findByMatricule(matricule);
        
        //Cas 2
        if(caTraite >= objectifCa*0.8 && caTraite < objectifCa*0.95)
        {
        	performance = Math.max(Entreprise.PERFORMANCE_BASE, employe.getPerformance() - 2);
        }
            
        //Cas 3
        else if(caTraite >= objectifCa*0.95 && caTraite <= objectifCa*1.05)
        {
            performance = Math.max(Entreprise.PERFORMANCE_BASE, employe.getPerformance());
        }
        
        //Cas 4
        else if(caTraite <= objectifCa*1.2 && caTraite > objectifCa*1.05)
        {
            performance = employe.getPerformance() + 1;
        }
        
        //Cas 5
        else if(caTraite > objectifCa*1.2)
        {
            performance = employe.getPerformance() + 4;
        }
            
        //Si autre cas, on reste à la performance de base.

        //Calcul de la performance moyenne
        Double performanceMoyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        if(performanceMoyenne != null && performance > performanceMoyenne)
        {
            performance++;
        }
        
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        Assertions.assertEquals(performance, employe.getPerformance());
        
        
    }
}