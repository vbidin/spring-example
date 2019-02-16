package eu.bidin.springexample.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidUuidException extends RuntimeException {

    private static final String template = "UUID '%s' is invalid.";

    public InvalidUuidException(String value) {
        super(String.format(template, value == null ? "null" : value));
    }
}
