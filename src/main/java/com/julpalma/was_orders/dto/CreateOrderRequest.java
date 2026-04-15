package com.julpalma.was_orders.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOrderRequest {

    @NotBlank
    private String customerName;

    @NotBlank
    private String orderType;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

}
