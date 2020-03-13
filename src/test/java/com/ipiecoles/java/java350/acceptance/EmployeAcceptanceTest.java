package com.ipiecoles.java.java350.acceptance;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import com.thoughtworks.gauge.Step;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeAcceptanceTest {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EmployeService employeService;

    @Step("J'embauche une personne appelée <prenom> <nom> diplômée d'un <diplome> en tant que <poste> à <txActivite>.")
    public void embauche(String prenom, String nom, String diplome, String poste, String txActivite) throws EmployeException {
        NiveauEtude niveauEtude = NiveauEtude.valueOf(diplome.toUpperCase());
        Poste posteEmp = Poste.valueOf(poste.toUpperCase());
        Double tempsActivite = 1.0;
        if(txActivite.equals("mi-temps")){
            tempsActivite = 0.5;
        }
        employeService.embaucheEmploye(nom,prenom,posteEmp,niveauEtude,tempsActivite);
        System.out.println(prenom);
        System.out.println(nom);
        System.out.println(diplome);
        System.out.println(poste);
        System.out.println(txActivite);
    }

    @Step("Soit un employé appelé <John> <Doe> de matricule <C00123>")
    public void implementation1(String prenom, String nom, String matricule) {
        Employe employe = new Employe();
        employe.setPrenom(prenom);
        employe.setNom(nom);
        employe.setMatricule(matricule);
        employeRepository.save(employe);
    }

    @Step("J'obtiens bien un nouvel employé appelé <John> <Doe> portant le matricule <T00001> et touchant un salaire de <1521.22>€")
    public void implementation2(String prenom, String nom, String matricule, Double salaire) {
        Employe employe = employeRepository.findByMatricule(matricule);
        Assertions.assertThat(employe).isNotNull();
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getSalaire()).isEqualTo(salaire);
    }

}
