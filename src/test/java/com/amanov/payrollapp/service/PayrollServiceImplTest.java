package com.amanov.payrollapp.service;

import com.amanov.payrollapp.dto.PaymentRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@SpringBootTest
class PayrollServiceImplTest {
    @Autowired
    PayrollService payrollService;

    @Test
    @DisplayName("отпуск на 30 дня")
    void calculate22days() {
        BigDecimal salary = BigDecimal.valueOf(100000);
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 31);
        PaymentRequest paymentRequest = new PaymentRequest(salary, firstDay, lastDay);

        BigDecimal actual = payrollService.calculate(paymentRequest).getPayment();
        BigDecimal expected = BigDecimal.valueOf(100000.00).setScale(2, RoundingMode.HALF_UP);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Отпуск на 14 дней")
    void calculate14days() {
        BigDecimal salary = BigDecimal.valueOf(100000);
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);
        PaymentRequest paymentRequest = new PaymentRequest(salary, firstDay, lastDay);

        BigDecimal actual = payrollService.calculate(paymentRequest).getPayment();
        BigDecimal expected = BigDecimal.valueOf(45454.55).setScale(2, RoundingMode.HALF_UP);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Отпуск на 7 дней")
    void calculate7days() {
        BigDecimal salary = BigDecimal.valueOf(100000);
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 6);
        PaymentRequest paymentRequest = new PaymentRequest(salary, firstDay, lastDay);

        BigDecimal actual = payrollService.calculate(paymentRequest).getPayment();
        BigDecimal expected = BigDecimal.valueOf(22727.27);

        Assertions.assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Средняя зарплата == 0р")
    void salaryZero() {
        BigDecimal salary = BigDecimal.valueOf(0);
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 6);
        PaymentRequest paymentRequest = new PaymentRequest(salary, firstDay, lastDay);

        BigDecimal actual = payrollService.calculate(paymentRequest).getPayment();
        BigDecimal expected = BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void dateIsNull() {
        BigDecimal salary = BigDecimal.valueOf(100000);
        Assertions.assertThrows(ResponseStatusException.class,
                () -> payrollService.calculate(new PaymentRequest(salary, null, null)));
    }

    @Test
    void negativeSalary() {
        BigDecimal salary = BigDecimal.valueOf(-1);
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);
        Assertions.assertThrows(ResponseStatusException.class,
                () -> payrollService.calculate(new PaymentRequest(salary, firstDay, lastDay)));
    }


    @Test
    void firstDayIsAfterLastDayVacation() {
        BigDecimal salary = BigDecimal.valueOf(100000);
        LocalDate firstDay = LocalDate.of(2023, 5, 20);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);
        Assertions.assertThrows(ResponseStatusException.class,
                () -> payrollService.calculate(new PaymentRequest(salary, firstDay, lastDay)));
    }

    @Test
    void LastDayIsBeforeFirstDayVacation() {
        BigDecimal salary = BigDecimal.valueOf(100000);
        LocalDate firstDay = LocalDate.of(2023, 5, 20);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);
        Assertions.assertThrows(ResponseStatusException.class,
                () -> payrollService.calculate(new PaymentRequest(salary, firstDay, lastDay)));
    }
}