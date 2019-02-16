package eu.bidin.springexample.utilities;

import java.util.UUID;
import eu.bidin.springexample.exceptions.InvalidUuidException;

public class UuidParser {

    public UUID Parse(String value) {
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUuidException(value);
        }
    }
}
