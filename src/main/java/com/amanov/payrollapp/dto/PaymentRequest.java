package com.amanov.payrollapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PaymentRequest {
    private BigDecimal salary;
    private LocalDate firstDay;
    private LocalDate lastDay;
}
