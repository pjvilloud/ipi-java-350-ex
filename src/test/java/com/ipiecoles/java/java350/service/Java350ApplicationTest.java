package com.ipiecoles.java.java350.service;
import com.ipiecoles.java.java350.Java350Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Java350ApplicationTest {

    @Test
    public void testmain(){
        //Given
        SpringApplication.run(Java350Application.class);
    }
}
