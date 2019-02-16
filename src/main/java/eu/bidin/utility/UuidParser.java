package eu.bidin.utility;

import java.util.UUID;

public class UuidParser {

    public static UUID Parse(String value) {
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
