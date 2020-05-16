package studio.secretingredients.consult4me.domain;

/**
 * Specialisation category types enumeration
 *
 * @author corbandalas - created 15.05.2020
 * @since 0.1.0
 */

public enum SpecialisationType {
    RELATION("RELATION"),
    DEPRESSION("DEPRESSION"),
    FAMILY_STRESS("FAMILY_STRESS")
    ;

    private String value;

    SpecialisationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}