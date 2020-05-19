package studio.secretingredients.consult4me.controller.customer.register.dto;

import lombok.Data;
import studio.secretingredients.consult4me.domain.ChannelCategory;

@Data
public class CustomerChannel {

    private ChannelCategory channelCategory;
    private String account;

}