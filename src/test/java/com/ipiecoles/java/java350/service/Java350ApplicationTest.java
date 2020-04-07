package com.ipiecoles.java.java350.service;
import com.ipiecoles.java.java350.Java350Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

public class Java350ApplicationTest {

    @Test
    public void testmain(){
        Java350Application.main(new String[]{
                "--spring.main.web-environment=false",
                "--spring.autoconfigure.exclude=blahblahblah",
                // Override any other environment properties according to your needs
        });
    }
}
