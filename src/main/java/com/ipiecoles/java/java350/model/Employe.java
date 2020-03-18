package com.ipiecoles.java.java350.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ipiecoles.java.java350.exception.EmployeException;

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

	public Employe(String nom, String prenom, String matricule, LocalDate dateEmbauche, Double salaire,
			Integer performance, Double tempsPartiel) {
		this.nom = nom;
		this.prenom = prenom;
		this.matricule = matricule;
		this.dateEmbauche = dateEmbauche;
		this.salaire = salaire;
		this.performance = performance;
		this.tempsPartiel = tempsPartiel;
	}

	public Integer getNombreAnneeAnciennete() {
		if (dateEmbauche != null && dateEmbauche.isBefore(LocalDate.now())) {
			return LocalDate.now().getYear() - dateEmbauche.getYear();
		}
		return 0;
	}

	public Integer getNbConges() {
		return Entreprise.NB_CONGES_BASE + this.getNombreAnneeAnciennete();
	}

	public Integer getNbRtt() throws EmployeException {
		return getNbRtt(LocalDate.now());
	}

	/**
	 * calcul le nombre de jours de RTT. cette méthode prendre en paramètre une
	 * date, en suite elle comptes le nombre des jours dans l'année puis le nombre
	 * de jours feriés.
	 * 
	 * le calcule comme la suite Nombre de jour dans l'année - nombre de jour du
	 * travaill (218) - nombre des jours feriés - nombre de jours des congés payées
	 * (25)
	 * 
	 * @param date pour l'année de laqulle on souahite calculer le RTT
	 * @return le nombre de jours Rtt
	 * @throws EmployeException
	 *
	 */
	public Integer getNbRtt(LocalDate date) throws EmployeException {

		if (date == null) {
			throw new EmployeException("La Date ne peut pas être null");
		}
		// nombre de joures dans l'année, si c'est une Année bissextile lil est égale à
		// 366 sinon 365;
		int nbjoursAnnee = date.isLeapYear() ? 366 : 365;

		// Nombre de Samedi et dimance dans l'année
		int weekends = 104;

		switch (LocalDate.of(date.getYear(), 1, 1).getDayOfWeek()) {
		// si l'année commence le vendredi et que l'annéé est bissextile donc un jour de
		// plus est ça tombe à samedi. du coup weekends+1
		case FRIDAY:
			if (date.isLeapYear()) {
				weekends = weekends + 1;
			}
			break;
		// si l'année commence le samedi un jour de plus parce que ça termine un samdedi
		// et l'annéé est bissextile donc est ça tombe à un dimanche. du coup weekends+2
		case SATURDAY:
			if (date.isLeapYear()) {
				weekends = weekends + 2;
			} else {
				weekends = weekends + 1;
			}
			break;
		// si l'année commence le dimanche, un jour de plus parce que ça termine un
		// dimanche.
		case SUNDAY:
			weekends = weekends + 1;
			break;
		default:
			// do nothings. we don't need modify weekends variable
		}
		/*
		 * ce variable contient le nombre de jours fériés calculé par la méthode
		 * joursFeries(d) qui prend en paramètre une date. puis avec le streem on trouve
		 * juste les jours fériés qui ne tombent pas à un weekend
		 */
		int monInt = (int) Entreprise.joursFeries(date).stream()
				.filter(localDate -> localDate.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue()).count();

		return (int) Math
				.ceil((nbjoursAnnee - Entreprise.NB_JOURS_MAX_FORFAIT - weekends - Entreprise.NB_CONGES_BASE - monInt)
						* tempsPartiel);
	}

	/**
	 * Calcul de la prime annuelle selon la règle : Pour les managers : Prime
	 * annuelle de base bonnifiée par l'indice prime manager Pour les autres
	 * employés, la prime de base plus éventuellement la prime de performance
	 * calculée si l'employé n'a pas la performance de base, en multipliant la prime
	 * de base par un l'indice de performance (égal à la performance à laquelle on
	 * ajoute l'indice de prime de base)
	 *
	 * Pour tous les employés, une prime supplémentaire d'ancienneté est ajoutée en
	 * multipliant le nombre d'année d'ancienneté avec la prime d'ancienneté. La
	 * prime est calculée au pro rata du temps de travail de l'employé
	 *
	 * @return la prime annuelle de l'employé en Euros et cents
	 */
	public Double getPrimeAnnuelle() {
		// Calcule de la prime d'ancienneté
		Double primeAnciennete = Entreprise.PRIME_ANCIENNETE * this.getNombreAnneeAnciennete();
		Double prime;
		// Prime du manager (matricule commençant par M) : Prime annuelle de base
		// multipliée par l'indice prime manager
		// plus la prime d'anciennté.
		if (matricule != null && matricule.startsWith("M")) {
			prime = Entreprise.primeAnnuelleBase() * Entreprise.INDICE_PRIME_MANAGER + primeAnciennete;
		}
		// Pour les autres employés en performance de base, uniquement la prime annuelle
		// plus la prime d'ancienneté.
		else if (this.performance == null || Entreprise.PERFORMANCE_BASE.equals(this.performance)) {
			prime = Entreprise.primeAnnuelleBase() + primeAnciennete;
		}
		// Pour les employés plus performance, on bonnifie la prime de base en
		// multipliant par la performance de l'employé
		// et l'indice de prime de base.
		else {
			prime = Entreprise.primeAnnuelleBase() * (this.performance + Entreprise.INDICE_PRIME_BASE)
					+ primeAnciennete;
		}
		// Au pro rata du temps partiel.
		return prime * this.tempsPartiel;
	}

	// Augmenter salaire
	public void augmenterSalaire(double pourcentage) {
		if (pourcentage >= 0 && pourcentage <= 1) {
			this.salaire = this.salaire * (1 + pourcentage);
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
		if (this == o)
			return true;
		if (!(o instanceof Employe))
			return false;
		Employe employe = (Employe) o;
		return Objects.equals(id, employe.id) && Objects.equals(nom, employe.nom)
				&& Objects.equals(prenom, employe.prenom) && Objects.equals(matricule, employe.matricule)
				&& Objects.equals(dateEmbauche, employe.dateEmbauche) && Objects.equals(salaire, employe.salaire)
				&& Objects.equals(performance, employe.performance);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nom, prenom, matricule, dateEmbauche, salaire, performance);
	}

}
