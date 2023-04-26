package com.amanov.payrollapp.service;

import com.amanov.payrollapp.dto.PaymentRequest;
import com.amanov.payrollapp.dto.PaymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        checkRequest(request);
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
        BigDecimal payment = BigDecimal.valueOf(salary / BUSINESS_DAYS * days)
                .setScale(2, RoundingMode.HALF_UP);
        return new PaymentResponse(payment.toString());
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
