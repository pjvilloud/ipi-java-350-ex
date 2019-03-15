package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest extends EmployeService {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    //Réinitialise le jeu de données
    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this.getClass());
    }

    @Test
    void testEmbaucheEmployeTechnicienPleinTpsBts() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        when(employeRepository.findLastMatricule()).thenReturn(null);
        when(employeRepository.findByMatricule("T00001")).thenReturn(null);
        when(employeRepository.save(any())).thenAnswer(returnsFirstArg());

        //When Junit 5
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository).save(employeCaptor.capture()); // times(1) est une option du verify
        Employe e = employeCaptor.getValue(); //Récupère l'employe généré par la méthode save ci-dessus
        //Employe e = employeCaptor.getAllValues(); Servira si l'on appelle plusieurs fois la méthode save (employeCaptor.capture())

        Assertions.assertThat(employeCaptor.getValue().getNom()).isEqualTo(nom);
        Assertions.assertThat(employeCaptor.getValue().getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employeCaptor.getValue().getMatricule()).isEqualTo("T00001");
        Assertions.assertThat(employeCaptor.getValue().getPerformance()).isEqualTo(1);
        //Salaire de base * Coefficient (=1521.22 * 1.2)
        Assertions.assertThat(employeCaptor.getValue().getSalaire()).isEqualTo(1825.46);
        Assertions.assertThat(employeCaptor.getValue().getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employeCaptor.getValue().getTempsPartiel()).isEqualTo(tempsPartiel);

    }

    /**
     * Dernier matricule pour un employé : 00345
     * Vérifier avec la requête select max(substring(matricule,2)) from Employe
     *
     */
    @Test
    public void testEmbaucheEmployeManagerMiTempsMaster() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;

        when(employeRepository.findLastMatricule()).thenReturn("00345");
        when(employeRepository.findByMatricule("M00346")).thenReturn(null);
        when(employeRepository.save(any())).thenAnswer(returnsFirstArg());

        //When Junit 5
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository).save(employeCaptor.capture());

        Assertions.assertThat(employeCaptor.getValue().getNom()).isEqualTo(nom);
        Assertions.assertThat(employeCaptor.getValue().getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employeCaptor.getValue().getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employeCaptor.getValue().getMatricule()).isEqualTo("M00346");
        Assertions.assertThat(employeCaptor.getValue().getSalaire()).isEqualTo(1064.85); //1521.22 * 1.4 * 0.5
        Assertions.assertThat(employeCaptor.getValue().getDateEmbauche()).isEqualTo(LocalDate.now());

    }

    @Test
    public void testEmbaucheEmployeLastMatricule99999() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;

        when(employeRepository.findLastMatricule()).thenReturn("99999");

        //When
        try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("La méthode aurait dû lancer une exception");
        }
        //Then
        catch (EmployeException employeException) {
            Assertions.assertThat(employeException.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
        }
    }


    @Test
    public void testEmbaucheEmployeExistingEmploye() throws EmployeException, EntityExistsException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;

        when(employeRepository.findLastMatricule()).thenReturn("00001");
        when(employeRepository.findByMatricule("M00002")).thenReturn(new Employe());

        //When
        try {
            employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);
            Assertions.fail("La méthode aurait dû lancer une exception");
        }
        //Then
        catch (EntityExistsException entityExistsException) {
            Assertions.assertThat(entityExistsException.getMessage()).isEqualTo("L'employé de matricule M00002 existe déjà en BDD");
        }
    }

}