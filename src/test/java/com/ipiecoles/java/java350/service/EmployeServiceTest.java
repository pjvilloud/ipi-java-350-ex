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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;


@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Test
    void testEmbaucheEmploye() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "Johny";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");

        //When
        employeService.embaucheEmploye(nom,prenom,poste,niveauEtude,tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeArgumentCaptor.capture());
        Assertions.assertThat(employeArgumentCaptor.getValue().getMatricule()).isEqualTo("T00346");
        Assertions.assertThat(employeArgumentCaptor.getValue().getNom()).isEqualTo(nom);
        Assertions.assertThat(employeArgumentCaptor.getValue().getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employeArgumentCaptor.getValue().getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employeArgumentCaptor.getValue().getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employeArgumentCaptor.getValue().getSalaire()).isEqualTo(1825.46);
    }

    @Test
    public void testFindByMatriculeIsNull(){
        //Given
        Mockito.when(employeRepository.findByMatricule("X")).thenReturn(null);
        //When Junit 5
        NullPointerException e =
                org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () ->
                        employeService.embaucheEmploye(null,null,null,null,null)
                );
        //Then Exception
        org.junit.jupiter.api.Assertions.assertEquals(e.getMessage(), null);
    }
}
