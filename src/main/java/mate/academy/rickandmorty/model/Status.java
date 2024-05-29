package mate.academy.rickandmorty.model;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum Status {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("unknown");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static Status getByValue(String value) {
        return Arrays.stream(Status.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Undefined character status: " + value));
    }
}
