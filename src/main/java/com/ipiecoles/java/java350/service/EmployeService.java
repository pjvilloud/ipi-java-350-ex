package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmployeService.class);

    /**
     * Alows to abstract rules for calculating performance
     */
    private class PerformanceRule {
        private Double lowerBound;
        private Double upperBound;
        private int modifier;

        /**
         * Verify the rule has to be applied
         *
         * @param caTraite The ca the employe made this year
         * @return
         */
        public boolean verify(Long caTraite) {
            return (lowerBound == null || caTraite >= lowerBound) &&
                    (upperBound == null || caTraite < upperBound);
        }

        /**
         * Computes the new value for employe's performance
         * Neither initial value nor computed value can be inferior to Entreprise's base value
         *
         * @param performance the employe's initial performance
         * @return int The new performance
         */
        public int apply(int performance) {
            if (performance <= Entreprise.PERFORMANCE_BASE) performance = Entreprise.PERFORMANCE_BASE;
            return  Math.max(Entreprise.PERFORMANCE_BASE, performance + modifier);
        }

        /**
         * Constructor
         *
         * @param lowerBound The minimal value for which this rule will apply
         * @param modifier The modifier to be applied to the employe's performance
         */
        PerformanceRule(Double lowerBound, Double upperBound, int modifier) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.modifier = modifier;
        }
    }

    /**
     * Méthode enregistrant un nouvel employé dans l'entreprise
     *
     * @param nom Le nom de l'employé
     * @param prenom Le prénom de l'employé
     * @param poste Le poste de l'employé
     * @param niveauEtude Le niveau d'étude de l'employé
     * @param tempsPartiel Le pourcentage d'activité en cas de temps partiel
     *
     * @throws EmployeException Si on arrive au bout des matricules possibles
     * @throws EntityExistsException Si le matricule correspond à un employé existant
     */
    public void embaucheEmploye(String nom, String prenom, Poste poste, NiveauEtude niveauEtude, Double tempsPartiel) throws EmployeException {
        String infoMessage = String.format("Trying to hire new %s named %s %s, with diploma : %s, working at %f of time.", poste, prenom, nom, niveauEtude, tempsPartiel);
        logger.info(infoMessage);

        //Récupération du type d'employé à partir du poste
        String typeEmploye = poste.name().substring(0,1);

        //Récupération du dernier matricule...
        String lastMatricule = employeRepository.findLastMatricule();
        if(lastMatricule == null){
            lastMatricule = Entreprise.MATRICULE_INITIAL;
        }
        //... et incrémentation
        Integer numeroMatricule = Integer.parseInt(lastMatricule) + 1;
        if (numeroMatricule >= 80000 && numeroMatricule < 100000){
            String warningMessage = String.format("Close to limit of 100000 matricules, actual matricule number: %d", numeroMatricule );
            logger.warn(warningMessage);
        }
        else if(numeroMatricule >= 100000){
            String errorMessage = "Limite des 100000 matricules atteinte !";
            logger.error(errorMessage);
            throw new EmployeException(errorMessage);
        }
        //On complète le numéro avec des 0 à gauche
        String matricule = "00000" + numeroMatricule;
        matricule = typeEmploye + matricule.substring(matricule.length() - 5);

        //On vérifie l'existence d'un employé avec ce matricule
        if(employeRepository.findByMatricule(matricule) != null){
            String errorMessage = String.format("L'employé de matricule %s existe déjà en BDD", matricule);
            logger.error(errorMessage);
            throw new EntityExistsException(errorMessage);
        }

        //Calcul du salaire
        Double salaire = Entreprise.getCoeffSalaireEtudes(niveauEtude) * Entreprise.SALAIRE_BASE;
        if(tempsPartiel != null){
            salaire = salaire * tempsPartiel;
        }
        logger.debug("Wage before rounding : {}", salaire);
        salaire = Math.round(salaire*100d)/100d;

        //Création et sauvegarde en BDD de l'employé.
        Employe employe = new Employe(nom, prenom, matricule, LocalDate.now(), salaire, Entreprise.PERFORMANCE_BASE, tempsPartiel);
        logger.info("Saving employe : {}", employe);

        employeRepository.save(employe);

    }


    /**
     * Méthode calculant la performance d'un commercial en fonction de ses objectifs et du chiffre d'affaire traité dans l'année.
     * Cette performance lui est affectée et sauvegardée en BDD
     *
     * 1 : Si le chiffre d'affaire est inférieur de plus de 20% à l'objectif fixé, le commercial retombe à la performance de base
     * 2 : Si le chiffre d'affaire est inférieur entre 20% et 5% par rapport à l'ojectif fixé, il perd 2 de performance (dans la limite de la performance de base)
     * 3 : Si le chiffre d'affaire est entre -5% et +5% de l'objectif fixé, la performance reste la même.
     * 4 : Si le chiffre d'affaire est supérieur entre 5 et 20%, il gagne 1 de performance
     * 5 : Si le chiffre d'affaire est supérieur de plus de 20%, il gagne 4 de performance
     *
     * Si la performance ainsi calculée est supérieure à la moyenne des performances des commerciaux, il reçoit + 1 de performance.
     *
     * @param matricule le matricule du commercial
     * @param caTraite le chiffre d'affaire traité par le commercial pendant l'année
     * @param objectifCa l'object de chiffre d'affaire qui lui a été fixé
     *
     * @throws EmployeException Si le matricule est null ou ne commence pas par un C
     */
    public void calculPerformanceCommercial(String matricule, Long caTraite, Long objectifCa) throws EmployeException {
        //Vérification des paramètres d'entrée
        if(caTraite == null || caTraite < 0){
            throw new EmployeException("Le chiffre d'affaire traité ne peut être négatif ou null !");
        }
        if(objectifCa == null || objectifCa < 0){
            throw new EmployeException("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
        }
        if(matricule == null || !matricule.startsWith("C")){
            throw new EmployeException("Le matricule ne peut être null et doit commencer par un C !");
        }
        //Recherche de l'employé dans la base
        Employe employe = employeRepository.findByMatricule(matricule);
        if(employe == null){
            throw new EmployeException("Le matricule " + matricule + " n'existe pas !");
        }

        ArrayList<PerformanceRule> rules = new ArrayList<>();
        rules.add(new PerformanceRule(null, objectifCa * 0.8, -employe.getPerformance()));
        rules.add(new PerformanceRule(objectifCa * 0.8, objectifCa * 0.95, -2));
        rules.add(new PerformanceRule(objectifCa * 0.95, objectifCa * 1.05 + 1, 0));
        rules.add(new PerformanceRule(objectifCa * 1.05 + 1,  objectifCa * 1.2 + 1, 1));
        rules.add(new PerformanceRule(objectifCa * 1.2 + 1, null, 4));

        rules.forEach(rule ->{ if (rule.verify(caTraite)) {
            employe.setPerformance(rule.apply(employe.getPerformance()));
        }});

        //Calcul de la performance moyenne
        Double performanceMoyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        if(performanceMoyenne != null && employe.getPerformance() > performanceMoyenne){
            employe.setPerformance(employe.getPerformance() + 1);
        }

        //Sauvegarde
        employeRepository.save(employe);
    }
}
