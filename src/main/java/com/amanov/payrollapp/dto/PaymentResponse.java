package com.amanov.payrollapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private BigDecimal payment;
}
