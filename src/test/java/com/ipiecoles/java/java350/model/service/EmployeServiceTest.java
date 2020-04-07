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

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeRepository employeRepository;

    @Test
    void embaucheEmploye() throws EmployeException {
        //Given
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);
        //Then
        employeService.embaucheEmploye("Doe", "John", Poste.COMMERCIAL, NiveauEtude.LICENCE, 1.0);

        //When
    }

    @Test
    void embaucheEmployeXEmploye() throws EmployeException {
        //Given
        // Quand la méthode findByMatricule va être appelée, on veux qu'elle renvoie une valeur
        // comme si il y avait plusieurs employes , dont le matricule le plus élevé est C45678
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("45678");
        Mockito.when(employeRepository.findByMatricule("C45679")).thenReturn(null);
        //Then
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //When
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C45679");
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        //1521.22 * 1.2 * 1
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
    }

    @Test
    void embaucheEmployeEmbauchePartiel() throws EmployeException {
        //Given
        // Quand la méthode findByMatricule va être appelée, on veux qu'elle renvoie une valeur
        // comme si il y avait plusieurs employes , dont le matricule le plus élevé est C45678
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("45678");
        Mockito.when(employeRepository.findByMatricule("C45679")).thenReturn(null);
        //Then
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 0.5;
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //When
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C45679");
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        //1521.22 * 1.2 * 0.5 = 912.73
        Assertions.assertThat(employe.getSalaire()).isEqualTo(912.73);
    }

    @Test
    void embaucheEmployeEmployeExiste() {
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("11111");
        Mockito.when(employeRepository.findByMatricule("C11112")).thenReturn(new Employe());

        Assertions.assertThatThrownBy(() ->
                employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel)
        )
                .isInstanceOf(EntityExistsException.class)
                .hasMessage("L'employé de matricule C11112 existe déjà en BDD");
    }

    @Test
    void embaucheEmployeLimiteEmploye() {
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        Assertions.assertThatThrownBy(() ->
                employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel)
        )
                .isInstanceOf(EmployeException.class)
                .hasMessage("Limite des 100000 matricules atteinte !");
    }
}