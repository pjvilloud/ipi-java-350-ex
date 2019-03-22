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

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// outils pour couverture des test : Coverage
// PiTest : il opère des mutations : il remplace les + par les -, les * en / etc... très vicieux
// dans Run > edit config > Maven : Command line: écrire : clean install org.pitest:pitest-maven:mutationCoverage

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {

    @InjectMocks
    private EmployeService employeService;
    // on injecte le service en utilisant le mock ci-dessous

    @Mock
    private EmployeRepository employeRepository;
    // on veut simuler un employeRepository

    @Test
    void testEmbaucheEmployeTechnicienPleinTempsBts() throws EmployeException {

        // Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtudes = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        // on veut qu'à l'appel de la fonction findLastMatricule, le résultat soit null
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        //When

        Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel);

        // Then
        // si employe existe dans la base avec les bonnes infos

        Assertions.assertEquals("T00001", e.getMatricule());
        Assertions.assertEquals(nom, e.getNom());
        Assertions.assertEquals(prenom, e.getPrenom());
        Assertions.assertEquals(LocalDate.now(), e.getDateEmbauche());
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, e.getPerformance());
        Assertions.assertEquals(1825.46, (double)e.getSalaire());
        // le calcul du salaire correspond à : 1521.22 * 1.2 * 1 = 1825.46
        Assertions.assertEquals(tempsPartiel, e.getTempsPartiel());
    }

    // si on voulait tester plus de scenarios on aurait pu faire un test mock + paramétrable
    @Test
    public void testEmbaucheManagerMiTempsLastMatricule00345() throws EmployeException {

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtudes = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;

        // on veut qu'à l'appel de la fonction findLastMatricule, le résultat soit 00345
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("00345");
        // on veut qu'à l'appel de la fonction findByMatricule, le résultat soit null
        Mockito.when(employeRepository.findByMatricule("M00346")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        // When
        Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel);

        //Then

        // si employe existe dans la base avec les bonnes infos
        Assertions.assertEquals("M00346", e.getMatricule());
        Assertions.assertEquals(nom, e.getNom());
        Assertions.assertEquals(prenom, e.getPrenom());
        Assertions.assertEquals(LocalDate.now(), e.getDateEmbauche());
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, e.getPerformance());

        Assertions.assertEquals(1064.85, (double)e.getSalaire());
        // le calcul du salaire correspond à : 1521.22 * 1.4 * 0.5 = 1064.85

        Assertions.assertEquals(tempsPartiel, e.getTempsPartiel());

    }

    // pour tester la levée d'exception on peut faire ça :
    // ou sinon voir les slides pour la version Junit5 avec une notation avec des lambdas
    @Test
    public void testEmbaucheManagerMiTempsLastMatricule99999() {

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtudes = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;

        // on veut qu'à l'appel de la fonction findLastMatricule, le résultat soit :
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        // When
        try {
            Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel);
            Assertions.fail("Devrait lancer une exception");
        } catch (EmployeException e1) {
            //Then
            Assertions.assertEquals("Limite des 100000 matricules atteinte !", e1.getMessage());
        }

    }

    // on va tester l'autre levée d'exception :
    @Test
    public void testEmbaucheManagerMiTempsLastMatriculeExisteDeja() throws EmployeException {

        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.MANAGER;
        NiveauEtude niveauEtudes = NiveauEtude.MASTER;
        Double tempsPartiel = 0.5;

        // on veut qu'à l'appel de la fonction findLastMatricule, le résultat soit null
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        // on veut qu'à l'appel de la fonction findByMatricule, le résultat soit null
        Mockito.when(employeRepository.findByMatricule("M00001")).thenReturn(new Employe());
        // Mockito.when(employeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        // When
        try {
            Employe e = employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel);
            Assertions.fail("Devrait lancer une exception");
        } catch (EntityExistsException e1) {
            //Then
            Assertions.assertEquals("L'employé de matricule M00001 existe déjà en BDD",
                    e1.getMessage());
        }

    }

    //quand une méthode ne retourne rien, on utilise les argument captors pour récupérer les résultats
    // voir slide Mock 2 :
    // on crée le captor :
    // ArgumentCaptor<Vehicule> vehiculeCaptor = ArgumentCaptor.forClass(Vehicule.class);
    // et là on vérifie que l'appel à la fonction est fait une fois :
   // Mockito.verify(vehiculeRepository, Mockito.times(1)).save(vehiculeCaptor.capture());
    // on peut récupérer aussi une liste en retour d'une méthode ou l'absence d'appel à une méthode

    // : on définit un Argument Captor et ensuite on capture le résultat d'une méthode qui return void avec
    // vehiculeCaptor.getValue()


    public void testEmbaucheEmployeTechnicienPleinTempsBtsArgumentCaptor() throws EmployeException {
        //Given
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtudes = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        // on veut qu'à l'appel de la fonction findLastMatricule, le résultat soit null
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("T00001")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        // When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtudes, tempsPartiel);
        // Then
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        // par défaut le test se fait pour ces valeurs : , Mockito.times(1)
        // donc cette partie est facultative
        Mockito.verify(employeRepository, Mockito.times(1)).save(employeCaptor.capture());

        Assertions.assertEquals("T00001", employeCaptor.getValue().getMatricule());
        Assertions.assertEquals(nom, employeCaptor.getValue().getNom());
        Assertions.assertEquals(prenom, employeCaptor.getValue().getPrenom());
        Assertions.assertEquals(LocalDate.now(), employeCaptor.getValue().getDateEmbauche());
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE, employeCaptor.getValue().getPerformance());
        Assertions.assertEquals(1064.85, (double)employeCaptor.getValue().getSalaire());
        Assertions.assertEquals(tempsPartiel, employeCaptor.getValue().getTempsPartiel());

        // ou :

        // Employe e = employeCaptor.capture().getValue();
        // Assertions.assertEquals("T00001", e.getMatricule());
        // etc

    }

}