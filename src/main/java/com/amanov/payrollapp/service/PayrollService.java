package com.amanov.payrollapp.service;

import com.amanov.payrollapp.dto.PaymentRequest;
import com.amanov.payrollapp.dto.PaymentResponse;

public interface PayrollService {
    PaymentResponse calculate(PaymentRequest request);
}
