package com.ipiecoles.java.java350.service;
import com.ipiecoles.java.java350.Java350Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Java350Application.class)
public class Java350ApplicationTest {

    @Test
    public void testMain(){
        Java350Application.main(new String[]{});
    }
}
