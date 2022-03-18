


Prime annuelle d'un manager embauché au cours de l'année à temps plein ----------------
* L'employe est un manager
* Embauché cette année
* Travaillant a plein temps
* Sa performance est de "1"
* Lorsque je calcule sa prime annuelle, j'obtiens "1700.00"
[16:30]
@Step("L'employe est un manager")
    public void createManager() {
        employe = new Employe();
        employe.setMatricule("M12345");
    }

    @Step("Embauché cette année")
    public void setEmbaucheCetteAnnee() {
        employe.setDateEmbauche(LocalDate.now());
    }

    @Step("Travaillant a plein temps")
    public void setTempsPlein() {
        employe.setTempsPartiel(1.0);
    }

    @Step("Sa performance est de <performance>")
    public void setPerformance(Integer performance) {
        employe.setPerformance(performance);
    }

    @Step("Lorsque je calcule sa prime annuelle, j'obtiens <prime>")
    public void setPrime(Double prime) {
        Assertions.assertThat(employe.getPrimeAnnuelle()).isEqualTo(prime);
    }
