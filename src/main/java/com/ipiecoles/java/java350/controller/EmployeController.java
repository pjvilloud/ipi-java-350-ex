package com.ipiecoles.java.java350.controller;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Employe getEmploye (@PathVariable("id") Long id) {
        return employeRepository.findById(id).get();
    }
}
