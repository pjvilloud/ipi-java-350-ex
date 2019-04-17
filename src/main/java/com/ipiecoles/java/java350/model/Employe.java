package com.ipiecoles.java.java350.model;

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

    /**
     * Retourne le nombre d'année d'ancienneté de l'employé par rapport à sa date d'embauche (on ne prend pas en compte
     * les mois et les jours. Il faut en revanche que la d'embauche soit non nulle et l'année antérieure à l'année courante
     * sinon on renvoie une ancienneté de 0
     *
     * @return le nombre d'année d'ancienneté
     */
    public final Integer getNombreAnneeAnciennete() {
        return dateEmbauche != null && LocalDate.now().getYear() >= dateEmbauche.getYear() ? LocalDate.now().getYear() - dateEmbauche.getYear() : 0;
    }

    public Integer getNbConges() {
        return Entreprise.NB_CONGES_BASE + this.getNombreAnneeAnciennete();
    }

    /**
     * Nombre de jours de RTT =
     *   Nombre de jours dans l'année
     * – plafond maximal du forfait jours de la convention collective
     * – nombre de jours de repos hebdomadaires
     * – jours de congés payés
     * – nombre de jours fériés tombant un jour ouvré
     *
     * Au prorata de son pourcentage d'activité (arrondi au supérieur)
     *
     * @return le nombre de jours de RTT
     */
    public Integer getNbRtt(){
        return getNbRtt(LocalDate.now());
    }

    /**
     * Nombre de jours de RTT =
     *   Nombre de jours dans l'année
     * – plafond maximal du forfait jours de la convention collective
     * – nombre de jours de repos hebdomadaires
     * – jours de congés payés
     * – nombre de jours fériés tombant un jour ouvré
     *
     * Au prorata de son pourcentage d'activité (arrondi au supérieur)
     *
     * @return le nombre de jours de RTT
     */
    public Integer getNbRtt(LocalDate date){
        // Les résultats de la ternaire étaient inversés
        int nbJoursAnnee = date.isLeapYear() ? 366 : 365;

        // Ne pas oublier les breaks dans les bloc switch, et éviter les if en ligne
        // Ajout d'une condition default pour éviter un code smell
        int nbJoursWeekend;
        switch (LocalDate.of(date.getYear(),1,1).getDayOfWeek()){
            case FRIDAY:
                nbJoursWeekend = date.isLeapYear() ? 105 : 104;
                break;
            case SATURDAY:
                nbJoursWeekend = date.isLeapYear() ? 106 : 105;
                break;
            case SUNDAY:
                nbJoursWeekend = date.isLeapYear() ? 104 : 105;
                break;
            default:
                nbJoursWeekend = 104;
        }

        // Si la date demandée est inférieure à l'année en cours, on ne peut trouver les jours fériés dans la liste
        // Pour éviter l'exception, on passe le nbRtt à 0 dans ce cas
        Double nbRtt = 0.0;
        if (date.getYear() >= LocalDate.now().getYear()) {
            Long nbJoursFeries = Entreprise.joursFeries(date).stream().filter(localDate -> localDate.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue()).count();

            // floor au lieu de ceil. On arrondi au plus bas si un jour n'est pas complétement acquis
            nbRtt = Math.floor((nbJoursAnnee - Entreprise.NB_JOURS_MAX_FORFAIT - nbJoursWeekend - Entreprise.NB_CONGES_BASE - nbJoursFeries) * tempsPartiel);
        }

        return nbRtt.intValue();
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
        //Calcule de la prime d'ancienneté
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
        return Math.round(prime * this.tempsPartiel * 100)/100.0;
    }

    //Augmenter salaire
    public void augmenterSalaire(double pourcentage){
        if (pourcentage < -1.0 || salaire < 0.0) {
            salaire = 0.0;
        }
        else {
            salaire = salaire * (1 + pourcentage);
        }
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
}