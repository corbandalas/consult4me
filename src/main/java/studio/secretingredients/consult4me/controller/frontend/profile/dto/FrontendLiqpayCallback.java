package studio.secretingredients.consult4me.controller.frontend.profile.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

@Data
public class FrontendLiqpayCallback extends BaseTokenRequest {

    private String data;

    private String signature;

}
