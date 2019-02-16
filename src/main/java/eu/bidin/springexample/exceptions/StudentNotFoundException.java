package eu.bidin.springexample.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {

    private static final String template = "Student '%s' does not exist.";

    public StudentNotFoundException(String uuid) {
        super(String.format(template, uuid == null ? "null" : uuid));
    }
}
