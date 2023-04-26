package com.amanov.payrollapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PaymentRequest {
    private Double salary;
    private LocalDate firstDay;
    private LocalDate lastDay;
}
