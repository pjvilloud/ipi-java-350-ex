package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;


    @Test
    void testEmbaucheEmployeLimitMarticule() {
        //Given
        when(employeRepository.findLastMatricule()).thenReturn("99999");

        //When
        Throwable t = Assertions.catchThrowable(() -> {
                employeService.embaucheEmploye("Doe", "John", Poste.COMMERCIAL, NiveauEtude.MASTER, 1.0);
        });
        //Then
        Assertions.assertThat(t).isInstanceOf(EmployeException.class).hasMessage("Limite des 100000 matricules atteinte !");

    }

    @Test
    void testEmbaucheEmployeExist(){
        //G
        when(employeRepository.findLastMatricule()).thenReturn(null);
        when(employeRepository.findByMatricule("C00001")).thenReturn(new Employe());

        //When
        Throwable t = Assertions.catchThrowable(() -> {
            employeService.embaucheEmploye("Doe", "John", Poste.COMMERCIAL, NiveauEtude.MASTER, 1.0);
        });

        Assertions.assertThat(t).isInstanceOf(EntityExistsException.class).hasMessage("L'employé de matricule C00001 existe déjà en BDD");

    }
    @Test
    void testEmbaucheEmploye() throws EmployeException {
        //Given
        when(employeRepository.findLastMatricule()).thenReturn(null);
        when(employeRepository.findByMatricule("C00001")).thenReturn(null);
        when(employeRepository.save(Mockito.any(Employe.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        //When
        employeService.embaucheEmploye("Doe", "John", Poste.COMMERCIAL, NiveauEtude.MASTER, 1.0);
        //Then
//        Employe employe = employeRepository.findByMatricule("C00001");
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe.getNom()).isEqualTo("Doe");
        Assertions.assertThat(employe.getPrenom()).isEqualTo("John");
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getSalaire()).isEqualTo(2129.71);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1);
    }

    @Test
    void testEmbaucheEmployeEmployesExist() throws EmployeException {
        //Given
        when(employeRepository.findLastMatricule()).thenReturn("12345");
        when(employeRepository.findByMatricule("C12346")).thenReturn(null);
        when(employeRepository.save(Mockito.any(Employe.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        //When
        employeService.embaucheEmploye("Doe", "John", Poste.COMMERCIAL, NiveauEtude.MASTER, 0.5);

        //Then
//        Employe employe = employeRepository.findByMatricule("C00001");
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe.getNom()).isEqualTo("Doe");
        Assertions.assertThat(employe.getPrenom()).isEqualTo("John");
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C12346");
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1064.85);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(0.5);
    }
    @Test
    void testCalculPEmployeNull() {
        //Given
        when(employeRepository.findByMatricule("C12345")).thenReturn(null);
        //When
        Throwable throwable = Assertions.catchThrowable(() -> employeService.calculPerformanceCommercial("C12345", 1L, 1L));
        //Then
        Assertions.assertThat(throwable).isInstanceOf(EmployeException.class)
                .hasMessage("Le matricule C12345 n'existe pas !");
    }

    @Test
    void testCalculPerformanceNullValue() {
        //When
        Throwable throwableMatricule = Assertions.catchThrowable(() -> {
            employeService.calculPerformanceCommercial(null, 2L, 2L);
        });
        // CA null
        Throwable catchThrowable = Assertions.catchThrowable(() -> {
            employeService.calculPerformanceCommercial("C12345", null, 2L);
        });
        // Objectif null
        Throwable throwableObjectif = Assertions.catchThrowable(() -> {
            employeService.calculPerformanceCommercial("C12345", 2L, null);
        });

        //Then
        Assertions.assertThat(throwableMatricule).isInstanceOf(EmployeException.class)
                .hasMessage("Le matricule ne peut être null et doit commencer par un C !");
        Assertions.assertThat(catchThrowable).isInstanceOf(EmployeException.class)
                .hasMessage("Le chiffre d'affaire traité ne peut être négatif ou null !");
        Assertions.assertThat(throwableObjectif).isInstanceOf(EmployeException.class)
                .hasMessage("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
    }

    @Test
    void testCalculPValeursIncorrectes() {
        //Given

        //When
        // Matricule ne commence pas par C
        Throwable throwableMatricule = Assertions.catchThrowable(() -> employeService.calculPerformanceCommercial("M12345", 1L, 1L));
        // CA < 0
        Throwable throwableCa = Assertions.catchThrowable(() -> employeService.calculPerformanceCommercial("C12345", -1L, 1L));
        // Objectif < 0
        Throwable throwableObjectif = Assertions.catchThrowable(() -> employeService.calculPerformanceCommercial("C12345", 1L, -1L));
        //Then
        Assertions.assertThat(throwableMatricule).isInstanceOf(EmployeException.class)
                .hasMessage("Le matricule ne peut être null et doit commencer par un C !");
        Assertions.assertThat(throwableCa).isInstanceOf(EmployeException.class)
                .hasMessage("Le chiffre d'affaire traité ne peut être négatif ou null !");
        Assertions.assertThat(throwableObjectif).isInstanceOf(EmployeException.class)
                .hasMessage("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
    }


    @ParameterizedTest
    @CsvSource({
            "100,100,3,1.2",
            "82,100,1,1.2",
            "0,100,1,1.2",
            "80,0,7,1.2",
            "82,80,3,1.2",
            "95,95,3,1.2",
            "97,100,3,1.2",
            "105,105,3,1.2",
            "107,105,3,1.2",
            "110,100,4,1.2",
            "130,100,7,1.2"
    })
    void testPerformanceCommercial(
            Long caTraite,
            Long objetifCa,
            Integer expectedPerformance,
            Double averagePerformance
    ) throws EmployeException {
        //Given
        Employe employe = new Employe();
        employe.setPerformance(2);
        when(employeRepository.findByMatricule("C12345")).thenReturn(employe);
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(averagePerformance);
        when(employeRepository.save(employe)).thenReturn(employe);

        //When
        employeService.calculPerformanceCommercial("C12345", caTraite, objetifCa);

        //Then
        Assertions.assertThat(employe.getPerformance()).isEqualTo(expectedPerformance);
    }
}
