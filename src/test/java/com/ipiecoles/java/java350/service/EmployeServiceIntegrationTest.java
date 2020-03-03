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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

@SpringBootTest
public class EmployeServiceIntegrationTest {
    @Autowired
    private EmployeService employeService;
    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    public void before(){
        //Appelé avant chaque test
        //On supprime toutes les données de la BDD de test
        employeRepository.deleteAll();
    }

    @Test
    public void testEmbaucheTechnicienBTSPleinTemps() throws EmployeException {
        //Given
        Employe employe1 = new Employe("Doe", "John", "T00146", LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0);
        employeRepository.save(employe1);
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

        //Then
        Employe employe = employeRepository.findByMatricule("T00147");
        Assertions.assertThat(employe).isNotNull();
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
        Assertions.assertThat(employe.getMatricule()).isEqualTo("T00147");
        //1521.22 * 1.2 = 1825.464
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1825.46d);
    }

}
