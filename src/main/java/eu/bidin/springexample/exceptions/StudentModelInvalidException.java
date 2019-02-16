package eu.bidin.springexample.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class StudentModelInvalidException extends RuntimeException {

    private static final String template = "Student is invalid: %s.";

    public StudentModelInvalidException(List<String> errors) {
        super(String.format(template, errors == null || errors.isEmpty() ? "N/A" : String.join(", ", errors)));
    }
}
