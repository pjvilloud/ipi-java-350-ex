package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    //element que l'on teste
    @InjectMocks
    EmployeService employeService;

    //element que l'on va injecter dans @InjectMocks
    @Mock
    EmployeRepository employeRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this.getClass());
    }

    @Test
    void embaucheEmploye0Employes() throws EmployeException {
        //Given
        //Quand la méthode findLastMatricule va être appellée, on veut qu'elle renvoie null, pour simuler une base employé vide
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);

        //Quand on va chercher si l'employé avec le matricule calculé existe, on veut que la méthode renvoie null également
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);

        //When
        employeService.embaucheEmploye("Doe","John", Poste.COMMERCIAL, NiveauEtude.LICENCE,1.0);

        //Then
    }

    @Test
    void embaucheEmployeXEmployes() throws EmployeException {
        //////////////////////////////////////////Given///////////////////////////////////////////////////

        //Quand la méthode findLastMatricule va être appellée, on veut qu'elle renvoie une valeur comme si il y'avait N employés dont le matricule le plus élever est C45678
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("45678");

        //Quand on va chercher si l'employé avec le matricule calculé existe, on veut que la méthode renvoie null également
        Mockito.when(employeRepository.findByMatricule("M45679")).thenReturn(null);

        //On stocke les données dans des variables
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;

        //Quand on fait une save() d'un employé, on renvoie exactement l'employé passé en paramètre du save
        Mockito.when(employeRepository.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());

        //////////////////////////////////////////When///////////////////////////////////////////////////

        employeService.embaucheEmploye(nom,prenom,poste,niveauEtude,tempsPartiel);

        //////////////////////////////////////////Then///////////////////////////////////////////////////

        //Employe employe = employeRepository.findByMatricule("M45679");
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        //On vérifie que le méthode save() a bien été appelé sur employeRepository, et on capture le paramètre
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employeArgumentCaptor.getValue().getMatricule()).isEqualTo("M45679");
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(tempsPartiel);
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        //1521.22 * 1.2 * 1.0 = 1825.46
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46);
    }

    @Test
    void testExceptionJavaMatricule100000(){
        //Given
        //Quand la méthode findLastMatricule va être appellée, on veut qu'elle renvoie une valeur comme si il y'avait N employés dont le matricule le plus élever est C45678
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        try{
            employeService.embaucheEmploye(nom,prenom,poste,niveauEtude,tempsPartiel);
            Assertions.fail("Aurait du lancer une exception");
        } catch(Exception e){
            //Then
            //Vérifie que l'exception levée est de type EmployeException
            Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            //Vérifie le contenue du message
            Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
        }

    }

    @Test
    void testExceptionJavaEmployeAlreadyExist(){
        //Given
        //Quand la méthode findLastMatricule va être appellée, on veut qu'elle renvoie une valeur comme si il y'avait N employés dont le matricule le plus élever est C45678
        String nom = "Jonh";
        String prenom = "Doe";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtude = NiveauEtude.LICENCE;
        Double tempsPartiel = 1.0;
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("55555");
        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(new Employe());

        try{
            employeService.embaucheEmploye(nom,prenom,poste,niveauEtude,tempsPartiel);
            Assertions.fail("Aurait du lancer une exception");
        } catch(Exception e){
            //Then
            //Vérifie que l'exception levée est de type EmployeException
            Assertions.assertThat(e).isInstanceOf(EntityExistsException.class);
            //Vérifie le contenue du message
            Assertions.assertThat(e.getMessage()).isEqualTo("L'employé de matricule M55556 existe déjà en BDD");
        }

    }
}