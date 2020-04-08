package com.ipiecoles.java.java350;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Java350ApplicationTest {
    @Autowired
    private static Java350Application java350Application;

    @Test
    public void mainTest() {
        // Given
        String[] args = new String[0];

        // When
        Java350Application.main(args);

        // Then
    }
}