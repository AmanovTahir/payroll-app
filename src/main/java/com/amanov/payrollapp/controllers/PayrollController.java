package com.amanov.payrollapp.controllers;

import com.amanov.payrollapp.dto.PaymentRequest;
import com.amanov.payrollapp.dto.PaymentResponse;
import com.amanov.payrollapp.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        checkRequest(paymentRequest);
        return ResponseEntity.ok(service.calculate(paymentRequest));
    }

    private void checkRequest(PaymentRequest request) {
        if (request.getFirstDay() == null || request.getLastDay() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Не указан конкретный период отпуска!", new IllegalArgumentException());
        } else if (request.getSalary() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Указанная заработная плата ниже нуля!", new IllegalArgumentException());
        } else if (request.getFirstDay().isAfter(request.getLastDay()) ||
                request.getLastDay().isBefore(request.getFirstDay())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Указанаая дата отпуска неверна!", new IllegalArgumentException());
        }
    }
}
