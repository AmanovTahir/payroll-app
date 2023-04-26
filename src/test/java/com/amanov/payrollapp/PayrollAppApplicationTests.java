package com.amanov.payrollapp;

import com.amanov.payrollapp.controllers.PayrollController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
class PayrollAppApplicationTests {
    @Autowired
    PayrollController controller;

    @Test
    void calculate() {
        BigDecimal salary = BigDecimal.valueOf(100000);
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);
        HttpStatusCode statusCode = controller.getPayroll(salary, firstDay, lastDay).getStatusCode();
        Assertions.assertEquals(HttpStatus.OK, statusCode);
    }
}
