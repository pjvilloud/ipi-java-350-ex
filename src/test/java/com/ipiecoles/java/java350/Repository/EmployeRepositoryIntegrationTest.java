package com.ipiecoles.java.java350.Repository;


import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeRepositoryIntegrationTest {

    @Autowired
    EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup() {
        employeRepository.deleteAll();
    }

    @ParameterizedTest(name = "La performance moyenne des {0} est {1}")
    @CsvSource({
            "'C', 138",
            "'T', 17",
            "'M', 237"
    })
    void testAvgPerformanceWhereMatriculeStartsWith(String type,Double expectedAvgPerf){
        //Given
        // Ajout de Commerciaux
        ajouterEmploye("C11052", 387);
        ajouterEmploye("C29082", 12);
        ajouterEmploye("C21072", 15);

        // Ajout de Techniciens
        ajouterEmploye("T11052", 46);
        ajouterEmploye("T29082", 2);
        ajouterEmploye("T21072", 3);

        // Ajout de Managers
        ajouterEmploye("M11052", 50);
        ajouterEmploye("M29082", 528);
        ajouterEmploye("M21072", 133);

        //When
        Double avgActual = this.employeRepository.avgPerformanceWhereMatriculeStartsWith(type);

        //Then
        Assertions.assertThat(avgActual).isEqualTo(expectedAvgPerf);
    }

    void ajouterEmploye(String matricule, int performance) {
        Employe employe = new Employe();
        employe.setNom("DENOT");
        employe.setPrenom("Emilien");
        employe.setPerformance(performance);
        employe.setMatricule(matricule);
        employe.setDateEmbauche(LocalDate.now());

        employeRepository.save(employe);
    }
}
