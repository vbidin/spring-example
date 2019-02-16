package eu.bidin.springexample.controllers;

import eu.bidin.springexample.entities.Student;
import eu.bidin.springexample.exceptions.InvalidUuidException;
import eu.bidin.springexample.exceptions.StudentModelInvalidException;
import eu.bidin.springexample.exceptions.StudentNotFoundException;
import eu.bidin.springexample.models.StudentModel;
import eu.bidin.utility.MyHashtable;
import eu.bidin.utility.UuidParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class StudentController {

    private final MyHashtable<UUID, Student> database;

    public StudentController(MyHashtable<UUID, Student> database) {
        this.database = database;
    }

    @GetMapping("/students")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Student> getStudents() {
        return StreamSupport
                .stream(database.spliterator(), false)
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    @GetMapping("/students/{uuid}")
    @ResponseStatus(value = HttpStatus.OK)
    public Student getStudent(@PathVariable String uuid) {
        UUID key = UuidParser.Parse(uuid);
        if (key == null)
            throw new InvalidUuidException(uuid);

        Student student = database.get(key);
        if (student == null)
            throw new StudentNotFoundException(uuid);

        return student;
    }

    @PostMapping("/students")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createStudent(@RequestBody StudentModel model) {
        List<String> errors = model.Validate();
        if (!errors.isEmpty())
            throw new StudentModelInvalidException(errors);

        UUID key = UUID.randomUUID();
        Student value = new Student(key, model);
        database.put(key, value);
    }

    @PutMapping("/students/{uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateStudent(@PathVariable String uuid, @RequestBody StudentModel model) {
        UUID key = UuidParser.Parse(uuid);
        if (key == null)
            throw new InvalidUuidException(uuid);

        Student student = database.get(key);
        if (student == null)
            throw new StudentNotFoundException(uuid);

        List<String> errors = model.Validate();
        if (!errors.isEmpty())
            throw new StudentModelInvalidException(errors);

        Student value = new Student(key, model);
        database.put(key, value);
    }

    @DeleteMapping("/students/{uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable String uuid) {
        UUID key = UuidParser.Parse(uuid);
        if (key == null)
            throw new InvalidUuidException(uuid);

        if (!database.containsKey(key))
            throw new StudentNotFoundException(uuid);

        database.remove(key);
    }
}
