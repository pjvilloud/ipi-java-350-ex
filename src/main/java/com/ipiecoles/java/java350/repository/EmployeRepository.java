package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {

    //on teste par contre toutes les méthodes écrites manuellement donc celle-ci
    @Query("select max(substring(matricule,2)) from Employe")
    String findLastMatricule();

    //Cette méthode n'a pas besoin d'être testée, car c'est une méthode native = générée par SpringData
    Employe findByMatricule(String matricule);

    //et cette méthode là
    @Query("select avg(performance) from Employe where SUBSTRING(matricule,0,1) = ?1 ")
    Double avgPerformanceWhereMatriculeStartsWith(String premiereLettreMatricule);
}
