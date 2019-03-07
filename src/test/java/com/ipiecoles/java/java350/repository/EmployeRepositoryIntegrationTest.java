package com.ipiecoles.java.java350.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeRepositoryIntegrationTest {

    @Autowired
    EmployeRepository employeRepository;

    @Test
    void testAvgPerformanceWhereMatriculeStartsWith() {

    }

}