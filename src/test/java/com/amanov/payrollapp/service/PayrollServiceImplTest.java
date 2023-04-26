package com.amanov.payrollapp.service;

import com.amanov.payrollapp.dto.PaymentRequest;
import com.amanov.payrollapp.dto.PaymentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@SpringBootTest
class PayrollServiceImplTest {
    @Autowired
    PayrollService payrollService;

    @Test
    @DisplayName("отпуск на 30 дня")
    void calculate22days() {
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 31);
        PaymentRequest paymentRequest = new PaymentRequest(100_000D, firstDay, lastDay);
        PaymentResponse calculate = payrollService.calculate(paymentRequest);
        String expected = "100000.00";
        String actual = calculate.getPayment();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Отпуск на 14 дней")
    void calculate14days() {
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);
        PaymentRequest paymentRequest = new PaymentRequest(100_000D, firstDay, lastDay);
        PaymentResponse calculate = payrollService.calculate(paymentRequest);
        String expected = "45454.55";
        String actual = calculate.getPayment();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Отпуск на 7 дней")
    void calculate7days() {
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 6);
        PaymentRequest paymentRequest = new PaymentRequest(100_000D, firstDay, lastDay);
        PaymentResponse calculate = payrollService.calculate(paymentRequest);
        String expected = "22727.27";
        String actual = calculate.getPayment();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Средняя зарплата == 0р")
    void salaryZero() {
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 6);
        PaymentRequest paymentRequest = new PaymentRequest(0D, firstDay, lastDay);
        PaymentResponse calculate = payrollService.calculate(paymentRequest);
        String expected = "0.00";
        String actual = calculate.getPayment();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void dateIsNull() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> payrollService.calculate(new PaymentRequest(100000D, null, null)));
    }

    @Test
    void negativeSalary() {
        Double salary = -1D;
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);
        Assertions.assertThrows(ResponseStatusException.class,
                () -> payrollService.calculate(new PaymentRequest(salary, firstDay, lastDay)));
    }


    @Test
    void firstDayIsAfterLastDayVacation() {
        Double salary = 100000D;
        LocalDate firstDay = LocalDate.of(2023, 5, 20);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);
        Assertions.assertThrows(ResponseStatusException.class,
                () -> payrollService.calculate(new PaymentRequest(salary, firstDay, lastDay)));
    }

    @Test
    void LastDayIsBeforeFirstDayVacation() {
        Double salary = 100000D;
        LocalDate firstDay = LocalDate.of(2023, 5, 20);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);
        Assertions.assertThrows(ResponseStatusException.class,
                () -> payrollService.calculate(new PaymentRequest(salary, firstDay, lastDay)));
    }
}