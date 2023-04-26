package com.amanov.payrollapp.service;

import com.amanov.payrollapp.dto.PaymentRequest;
import com.amanov.payrollapp.dto.PaymentResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class PayrollServiceImpl implements PayrollService {

    public static final int BUSINESS_DAYS = 22;

    @Override
    public PaymentResponse calculate(PaymentRequest request) {
        long days = businessDays(request.getFirstDay(), request.getLastDay());
        return getPayment(request.getSalary(), days);
    }


    private long businessDays(LocalDate firstDate, LocalDate lastDate) {
        Predicate<LocalDate> isWeekend = date ->
                date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        return Stream.iterate(firstDate, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(firstDate, lastDate))
                .filter(isWeekend.negate())
                .count();
    }

    private PaymentResponse getPayment(double salary, long days) {
        BigDecimal payment = BigDecimal.valueOf(salary / BUSINESS_DAYS * days).setScale(2, RoundingMode.HALF_UP);
        return new PaymentResponse(payment.toString());
    }
}
