package com.ipiecoles.java.java350.service;
import com.ipiecoles.java.java350.Java350Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.testng.asserts.Assertion;

public class Java350ApplicationTest {

    @Test
    public void testmain(){
        //Given
        SpringApplication.run(Java350Application.class);
    }
}
