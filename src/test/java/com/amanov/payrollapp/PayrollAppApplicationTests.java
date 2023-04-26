package com.amanov.payrollapp;

import com.amanov.payrollapp.controllers.PayrollController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@SpringBootTest
class PayrollAppApplicationTests {
    @Autowired
    PayrollController controller;

    @Test
    void calculate() {
        Double salary = 100000D;
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);
        HttpStatusCode statusCode = controller.getPayroll(salary, firstDay, lastDay).getStatusCode();
        Assertions.assertEquals(HttpStatus.OK, statusCode);
    }

    @Test
    void dateIsNull() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> controller.getPayroll(100000D, null, null));
    }

    @Test
    void negativeSalary() {
        Double salary = -1D;
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> controller.getPayroll(salary, firstDay, lastDay));
    }


    @Test
    void firstDayIsAfterLastDayVacation() {
        Double salary = 100000D;
        LocalDate firstDay = LocalDate.of(2023, 5, 20);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);
        Assertions.assertThrows(ResponseStatusException.class,
                () -> controller.getPayroll(salary, firstDay, lastDay));
    }

    @Test
    void LastDayIsBeforeFirstDayVacation() {
        Double salary = 100000D;
        LocalDate firstDay = LocalDate.of(2023, 5, 20);
        LocalDate lastDay = LocalDate.of(2023, 5, 15);
        Assertions.assertThrows(ResponseStatusException.class,
                () -> controller.getPayroll(salary, firstDay, lastDay));
    }
}
