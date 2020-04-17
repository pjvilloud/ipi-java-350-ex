package java350;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDate;
@ExtendWith(SpringExtension.class) // Junit 5
@SpringBootTest
public class EmployeRepositoryIntegrationTest {
    @Autowired
EmployeRepository employeRepository;
@BeforeEach
public void setup(){
        employeRepository.deleteAll();
}
    @Test
void avgPerformanceWhereMatriculeStartsWithTest(){
        // Given
String premiereLettreMattricule = "M";
Employe employe = new Employe("Delacour", "Michel", "M00001", LocalDate.now(), 1825.46, 2, null);
employeRepository.save(employe);
Employe employe2 = new Employe("Mouch", "Jean", "M00002", LocalDate.now(), 2500D, 2, null);

employeRepository.save(employe2);
// When
Double avg = employeRepository.avgPerformanceWhereMatriculeStartsWith(premiereLettreMattricule);
// Then
Assertions.assertEquals(2D, avg);
}
}



