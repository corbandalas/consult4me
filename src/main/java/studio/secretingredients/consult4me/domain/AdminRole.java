package studio.secretingredients.consult4me.domain;

/**
 * Enumeration for admin roles
 *
 * @author corbandalas - created 16.05.2020
 * @since 0.1.0
 */
public enum AdminRole {

    ROLE_CUSTOMER_LOGIN("Allow account to authorize customers"),

    ;

    private String value;

    AdminRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
