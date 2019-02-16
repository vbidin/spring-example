package eu.bidin.springexample.controllers;

import eu.bidin.springexample.entities.Student;
import eu.bidin.springexample.utilities.UuidParser;
import eu.bidin.springexample.validators.StudentValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class StudentController {

    private final StudentValidator validator;
    private final UuidParser parser;

    public StudentController(StudentValidator validator, UuidParser parser) {
        this.validator = validator;
        this.parser = parser;
    }

    @GetMapping("/students/{uuid}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Student> getStudents() {
        return null;
    }

    @GetMapping("/students/{uuid}")
    @ResponseStatus(value = HttpStatus.OK)
    public Student getStudent(@PathVariable String uuid) {
        return null;
    }

    @PostMapping("/students")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createStudent() {

    }

    @PutMapping("/students/{uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateStudent() {

    }

    @DeleteMapping("/students/{uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteStudent() {

    }
}
