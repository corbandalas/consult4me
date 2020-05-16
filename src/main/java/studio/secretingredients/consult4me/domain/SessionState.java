package studio.secretingredients.consult4me.domain;

/**
 * Session state enumeration
 *
 * @author corbandalas - created 15.05.2020
 * @since 0.1.0
 */

public enum SessionState {
    ORDERED("ORDERED"),
    PAYED("PAYED"),
    STARTED("STARTED"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED")
    ;

    private String value;

    SessionState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}