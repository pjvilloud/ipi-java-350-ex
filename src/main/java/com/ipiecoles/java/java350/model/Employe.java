package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Employe {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private String matricule;
    private LocalDate dateEmbauche;
    private Double salaire = Entreprise.SALAIRE_BASE;
    private Integer performance = Entreprise.PERFORMANCE_BASE;
    private Double tempsPartiel = 1.0;

    public Employe() {
    }

    public Employe(String nom, String prenom, String matricule, LocalDate dateEmbauche, Double salaire, Integer performance, Double tempsPartiel) {
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.dateEmbauche = dateEmbauche;
        this.salaire = salaire;
        this.performance = performance;
        this.tempsPartiel = tempsPartiel;
    }

    public Integer getNombreAnneeAnciennete() {
        if(this.dateEmbauche != null) {
            if (this.dateEmbauche.isAfter(LocalDate.now())) {
                return 0;
            }
            return LocalDate.now().getYear() - dateEmbauche.getYear();
        }
        else {
            return 0;
        }
    }

    public Integer getNbConges() {
        return Entreprise.NB_CONGES_BASE + this.getNombreAnneeAnciennete();
    }

    public Integer getNbRtt(){
        return getNbRtt(LocalDate.now());
    }

    /**
     * Calcul du nombre de jours de RTT dans l'année entrée en paramètre selon la formule :
     * Nombre de jours dans l'année
     * - Nombre de jours travaillés dans l'année en plein temps
     * - Nombre de samedi et dimanche dans l'année
     * - Nombre de jours fériés ne tombant pas le week-end
     *  -   Listes des fériés
     * - Nombre de congés payés.
     *
     * @param annee
     * @return le nombre de jours de rtt, au prorata du temps partiel, en int arrondi au supérieur
     */
    public Integer getNbRtt(LocalDate annee){

        int nbJoursCetteAnnee = annee.lengthOfYear();
        int nbSamedisEtDimanchesCetteAnnee = 104;

        if(annee.isLeapYear()) {
            switch (LocalDate.of(annee.getYear(), 1, 1).getDayOfWeek()){
                case FRIDAY:
                    nbSamedisEtDimanchesCetteAnnee += 1;
                    break;
                case SATURDAY:
                    nbSamedisEtDimanchesCetteAnnee += 2;
                    break;
                case SUNDAY:
                    nbSamedisEtDimanchesCetteAnnee += 1;
                    break;
                default:
                    break;
            }
        }
        else if(!annee.isLeapYear()){
            switch(LocalDate.of(annee.getYear(), 1, 1).getDayOfWeek()) {
                case SATURDAY:
                    nbSamedisEtDimanchesCetteAnnee += 1;
                    break;
                case SUNDAY:
                    nbSamedisEtDimanchesCetteAnnee += 1;
                    break;
                default:
                    break;
            }
        }

        Long nbFeriesJoursOuvresCetteAnnee = Entreprise.joursFeries(annee)
                                                .stream()
                                                .filter(jour -> jour.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue())
                                                .count();

        Integer nbCongesPayes = this.getNbConges();

        Integer nbJoursRtt = nbJoursCetteAnnee
                - Entreprise.NB_JOURS_MAX_FORFAIT
                - nbSamedisEtDimanchesCetteAnnee
                - nbFeriesJoursOuvresCetteAnnee.intValue()
                - nbCongesPayes;

        nbJoursRtt = (int) Math.ceil(nbJoursRtt * this.getTempsPartiel());

        return nbJoursRtt;

    }

    /**
     * Calcul de la prime annuelle selon la règle :
     * Pour les managers : Prime annuelle de base bonnifiée par l'indice prime manager
     * Pour les autres employés, la prime de base plus éventuellement la prime de performance calculée si l'employé
     * n'a pas la performance de base, en multipliant la prime de base par un l'indice de performance
     * (égal à la performance à laquelle on ajoute l'indice de prime de base)
     *
     * Pour tous les employés, une prime supplémentaire d'ancienneté est ajoutée en multipliant le nombre d'année
     * d'ancienneté avec la prime d'ancienneté. La prime est calculée au pro rata du temps de travail de l'employé
     *
     * @return la prime annuelle de l'employé en Euros et cents
     */
    public Double getPrimeAnnuelle(){
        //Calcul de la prime d'ancienneté
        Double primeAnciennete = Entreprise.PRIME_ANCIENNETE * this.getNombreAnneeAnciennete();
        Double prime;
        //Prime du manager (matricule commençant par M) : Prime annuelle de base multipliée par l'indice prime manager
        //plus la prime d'anciennté.
        if(matricule != null && matricule.startsWith("M")) {
            prime = Entreprise.primeAnnuelleBase() * Entreprise.INDICE_PRIME_MANAGER + primeAnciennete;
        }
        //Pour les autres employés en performance de base, uniquement la prime annuelle plus la prime d'ancienneté.
        else if (this.performance == null || Entreprise.PERFORMANCE_BASE.equals(this.performance)){
            prime = Entreprise.primeAnnuelleBase() + primeAnciennete;
        }
        //Pour les employés plus performance, on bonnifie la prime de base en multipliant par la performance de l'employé
        // et l'indice de prime de base.
        else {
            prime = Entreprise.primeAnnuelleBase() * (this.performance + Entreprise.INDICE_PRIME_BASE) + primeAnciennete;
        }
        //Au pro rata du temps partiel.
        return prime * this.tempsPartiel;
    }
    /** Augmenter le salaire du salarié à partir d'un double :
     * @param pourcentage le pourcentage d'augmentation (double)
     *  - si pourcentage = 0.5, alors le salaire augmente de 50%
     *  - si pourcentage = 2.0, alors le salaire augmente de 200 %
     *  On ne peut pas diminuer un salaire et on ne peut pas augmenter le salaire de 0%
     */
    public void augmenterSalaire(double pourcentage) throws EmployeException {
        if(pourcentage < 0) {
            throw new EmployeException("Le pourcentage donné : " + pourcentage + " ne peut être inférieur à 0, il est illégal de diminuer un salaire.");
        }
        if(pourcentage == 0) {
            throw new EmployeException("Le pourcentage d'augmentation est égal à 0 : aucune augmentation de salaire n'est effective");
        }
        if (this.salaire == null) {
            throw new EmployeException("Le salaire de l'employe n'est pas initialisé, il doit être renseigné avant d'être augmenté");
        }

        this.setSalaire(this.salaire * (1 + pourcentage));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the matricule
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * @param matricule the matricule to set
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * @return the dateEmbauche
     */
    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    /**
     * @param dateEmbauche the dateEmbauche to set
     */
    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    /**
     * @return the salaire
     */
    public Double getSalaire() {
        return salaire;
    }

    /**
     * @param salaire the salaire to set
     */
    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public Integer getPerformance() {
        return performance;
    }

    public void setPerformance(Integer performance) {
        this.performance = performance;
    }

    public Double getTempsPartiel() {
        return tempsPartiel;
    }

    public void setTempsPartiel(Double tempsPartiel) {
        this.tempsPartiel = tempsPartiel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employe)) return false;
        Employe employe = (Employe) o;
        return Objects.equals(id, employe.id) &&
                Objects.equals(nom, employe.nom) &&
                Objects.equals(prenom, employe.prenom) &&
                Objects.equals(matricule, employe.matricule) &&
                Objects.equals(dateEmbauche, employe.dateEmbauche) &&
                Objects.equals(salaire, employe.salaire) &&
                Objects.equals(performance, employe.performance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, matricule, dateEmbauche, salaire, performance);
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", matricule='" + matricule + '\'' +
                ", dateEmbauche=" + dateEmbauche +
                ", salaire=" + salaire +
                ", performance=" + performance +
                ", tempsPartiel=" + tempsPartiel +
                '}';
    }
}
