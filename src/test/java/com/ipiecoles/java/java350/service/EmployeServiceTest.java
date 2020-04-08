package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeRepository employeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this.getClass());
    }

    @Test
    void embaucheEmploye0Employe() throws EmployeException {
        //Given
        //Quand la méthode findLastMatricule va être appelée, on veut qu'elle renvoie null pour simuler une base employé vide
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

    @ParameterizedTest
    @CsvSource({
            "'C14785', , 1000",
            "'C36985', -3500, 1000"
    })
    public void testCalculPerformanceCommercialCATNullouNegatif(String matricule, Long caTraite, Long objectifCa) throws EmployeException {
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
        Assertions.assertEquals("Le chiffre d'affaire traité ne peut être négatif ou null !", e.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "'C14785', 3500, ",
            "'C36985', 3500, -1500"
    })
    public void testCalculPerformanceCommercialCAONullouNegatif(String matricule, Long caTraite, Long objectifCa) throws EmployeException {
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
        Assertions.assertEquals("L'objectif de chiffre d'affaire ne peut être négatif ou null !", e.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            ", 1560, 1560",
            "'M45678', 3500, 3500"
    })
    public void testCalculPerformanceCommercialMatriculeNullouPasC(String matricule, Long caTraite, Long objectifCa) throws EmployeException {
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
        Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !", e.getMessage());
    }


}