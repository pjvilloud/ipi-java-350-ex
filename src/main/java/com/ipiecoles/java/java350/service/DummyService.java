package com.ipiecoles.java.java350.service;

import org.springframework.stereotype.Service;

@Service
public class DummyService {
    public static Integer doSomething(){
        throw new RuntimeException();
    }
}
