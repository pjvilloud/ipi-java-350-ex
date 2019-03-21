package com.ipiecoles.java.java350;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class StepImplementation {

    @Autowired
    private EmployeService employeService;

    @Autowired
    private EmployeRepository employeRepository;

    @Step("J'embauche une personne appelée <prenom> <nom> diplomée d'un <diplome> en tant que <poste> à <tempsActivite>.")
    public void embaucheEmploye(String prenom, String nom, String diplome, String poste, String tempsActivite) throws EmployeException {
        Double tempsPartiel;
        if(tempsActivite.equalsIgnoreCase("plein temps")){
            tempsPartiel = 1.0;
        }else if(tempsActivite.equalsIgnoreCase("mi-temps")){
            tempsPartiel = 0.5;
        } else {
            tempsPartiel=0.0;
        }

            employeService.embaucheEmploye(nom, prenom, Poste.valueOf(poste.toUpperCase()), NiveauEtude.valueOf(diplome.toUpperCase()), tempsPartiel);
    }



    @Step("J'obtiens bien un nouvel employé appelé <prenom> <nom> portant le matricule <matricule> et touchant un salaire <salaire>€")
    public void checkEmploye(String prenom, String nom, String matricule, Double salaire) {
        Employe employe = employeRepository.findByMatricule(matricule);
        Assertions.assertThat(employe.getNom()).isEqualTo(nom);
        Assertions.assertThat(employe.getPrenom()).isEqualTo(prenom);
        Assertions.assertThat(employe.getSalaire()).isEqualTo(salaire);

    }
}