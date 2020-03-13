package com.ipiecoles.java.java350.controller;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/employes")
public class EmployeController {
    @Autowired
    private EmployeRepository employeRepository;

    @GetMapping("/{id}")
    public Employe getEmploye(@PathVariable("id") Long id) {
         Optional<Employe> e = employeRepository.findById(id);
        if (e.isPresent()) {
            return e.get();
        } else throw new EntityNotFoundException("Employe " + id + " introuvable");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handledException(EntityNotFoundException e) {
        return e.getMessage();
    }
}


