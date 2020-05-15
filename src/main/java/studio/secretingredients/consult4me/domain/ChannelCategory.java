package studio.secretingredients.consult4me.domain;

/**
 * Social channel enumeration
 *
 * @author corbandalas - created 15.05.2020
 * @since 0.1.0
 */

public enum ChannelCategory {

    SKYPE("SKYPE"),
    ZOOM("ZOOM"),
    WHATSAPP("WHATSAPP"),
    TELEGRAM("TELEGRAM")
    ;

    private String value;

    ChannelCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}