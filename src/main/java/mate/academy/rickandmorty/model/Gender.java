package mate.academy.rickandmorty.model;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum Gender {
    FEMALE("Female"),
    MALE("Male"),
    GENDERLESS("Genderless"),
    UNKNOWN("unknown");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static Gender getByValue(String value) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Undefined character gender: " + value));
    }
}
