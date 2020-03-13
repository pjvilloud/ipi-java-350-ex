package com.ipiecoles.java.java350.contoller;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/employes")
public class EmployeController {
    @Autowired
    private EmployeRepository employeRepository;

    @GetMapping("/{id}")
    public Employe getEmploye(@PathVariable("id") Long id) {
        Optional<Employe> employe = employeRepository.findById(id);
        if(employe.isPresent()) {
            return employe.get();
        }
        throw new EntityNotFoundException("L'employé d'identifiant " + id + " n'a pas été trouvé !");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Error handleENFException(EntityNotFoundException e) {
        return new Error(e.getMessage());
    }
}
