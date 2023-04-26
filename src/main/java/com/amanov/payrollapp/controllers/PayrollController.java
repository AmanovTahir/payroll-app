package com.amanov.payrollapp.controllers;

import com.amanov.payrollapp.dto.PaymentRequest;
import com.amanov.payrollapp.dto.PaymentResponse;
import com.amanov.payrollapp.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class PayrollController {
    private final PayrollService service;

    @GetMapping("/calculate")
    public ResponseEntity<PaymentResponse> getPayroll(
            @RequestParam(name = "salary") Double salary,
            @RequestParam(name = "firstDay") LocalDate firstDay,
            @RequestParam(name = "lastDay") LocalDate lastDay) {
        PaymentRequest paymentRequest = new PaymentRequest(salary, firstDay, lastDay);
        return ResponseEntity.ok(service.calculate(paymentRequest));
    }
}
