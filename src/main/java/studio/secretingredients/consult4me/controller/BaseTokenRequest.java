package studio.secretingredients.consult4me.controller;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BaseTokenRequest {

    @NotNull
    protected String token;
}
