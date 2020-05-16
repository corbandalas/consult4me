package studio.secretingredients.consult4me.domain;

/**
 * Specialisation category enumeration
 *
 * @author corbandalas - created 15.05.2020
 * @since 0.1.0
 */

public enum SpecialisationCategory {
    PSYCHOLOGY("Психология"),
    ;

    private String value;

    SpecialisationCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}