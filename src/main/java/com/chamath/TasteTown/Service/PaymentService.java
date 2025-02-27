package com.chamath.TasteTown.Service;

import com.chamath.TasteTown.Model.Order;
import com.chamath.TasteTown.Model.User;
import com.chamath.TasteTown.Response.PaymentResponse;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

public interface PaymentService {
    public PaymentResponse createPaymentLink(Order order) throws StripeException;

}
