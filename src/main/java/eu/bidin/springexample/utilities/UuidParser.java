package eu.bidin.springexample.validators;

import java.util.UUID;
import eu.bidin.springexample.exceptions.InvalidUuidException;

public class UuidValidator {

    public void ValidateUuid(String value) {
        try {
            UUID uuid = UUID.fromString(value);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUuidException();
        }
    }
}
