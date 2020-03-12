package com.ipiecoles.java.java350.acceptance;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import com.thoughtworks.gauge.Step;
import net.bytebuddy.build.ToStringPlugin;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeAcceptanceTest {

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    EmployeService employeService;

    @Step("J'embauche une personne appelée <prénom> <nom> diplômée d'un <diplome> en tant que <poste> à <txActivite>.")
    public void embauche (String prenom, String nom, String diplome, String poste, String txActivite) throws Exception{
        NiveauEtude niveauEtude = NiveauEtude.valueOf(diplome.toUpperCase());
        Poste posteEmp = Poste.valueOf(poste.toUpperCase());
        Double tempsActivite = 1.0;

        if (txActivite.equals("mi-temps")){
            tempsActivite = 0.5;
        }

        employeService.embaucheEmploye(nom, prenom, posteEmp, niveauEtude, tempsActivite);
    }

    @Step("J'obtiens bien un nouvel employé appelé <prenom> <nom> portant le matricule <matricule> et touchant un salaire de <salaire> €")
    public void verifNewEmploye (String prenom, String nom, String matricule, Double salaire) {

        Employe employe = employeRepository.findByMatricule(matricule);
        Assertions.assertThat(employe).isNotNull();
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getSalaire()).isEqualTo(salaire);
    }

    @Step("Soit un employé appelé <prenom> <nom> de matricule <matricule>.")
    public void createEmploye (String prenom, String nom, String matricule) {
       Employe employe = new Employe();
       employe.setNom(nom);
       employe.setPrenom(prenom);
       employe.setMatricule(matricule);
       employeRepository.save(employe);
    }
}
