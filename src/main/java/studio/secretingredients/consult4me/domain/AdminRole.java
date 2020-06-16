package studio.secretingredients.consult4me.domain;

/**
 * Enumeration for admin roles
 *
 * @author corbandalas - created 16.05.2020
 * @since 0.1.0
 */
public enum AdminRole {

    ROLE_ADMIN_PROPERTY_LIST("Allows to view property list"),
    ROLE_ADMIN_PROPERTY_UPDATE("Allows to view property update"),
    ROLE_ADMIN_ACCOUNT_LIST("Allows to view account list"),
    ROLE_ADMIN_ACCOUNT_CREATE("Allows to create account"),
    ROLE_ADMIN_ACCOUNT_UPDATE("Allows to update account"),
    ROLE_ADMIN_ACCOUNT_GET("Allows to get account data"),
    ROLE_ADMIN_USER_LIST("Allows to view user list"),
    ROLE_ADMIN_USER_CREATE("Allows to create user"),
    ROLE_ADMIN_USER_UPDATE("Allows to update user"),
    ROLE_ADMIN_USER_GET("Allows to get user data"),
    ROLE_ADMIN_USER_GET_ALL_ROLES("Allows to get all roles data"),
    ROLE_ADMIN_USER_ADD_TO_ACCOUNT("Allows to get all user to account"),
    ROLE_ADMIN_SPECIALIST_LIST("Allows to view specialist list"),
    ROLE_ADMIN_CUSTOMER_LIST("Allows to view customer list"),
    ROLE_ADMIN_ADD_SPECIALIST("Allows to add specialist"),
    ROLE_ADMIN_EDIT_SPECIALIST("Allows to edit specialist"),
    ROLE_ADMIN_EDIT_CATEGORIES("Allows to edit categories"),
    ROLE_ADMIN_VIEW_SESSIONS("Allows to view customer/specialist sessions"),
    ROLE_ADMIN_PERFORM_PAYOUT("Allows to perform session payout"),
    ROLE_ADMIN_VIEW_PAYOUT("Allows to view session payout"),

    ;

    private String value;

    AdminRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
