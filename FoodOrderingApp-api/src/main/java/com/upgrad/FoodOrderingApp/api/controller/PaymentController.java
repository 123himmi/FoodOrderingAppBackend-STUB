package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.PaymentService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @RequestMapping(method = RequestMethod.GET, path = "/payment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PaymentListResponse> getAllPaymentMethods() {

            List <PaymentEntity> paymentEntities = paymentService.getPaymentMethods();

            PaymentListResponse paymentListResponses = new PaymentListResponse();

            for (PaymentEntity c : paymentEntities) {
                PaymentResponse temp = new PaymentResponse();
                temp.setId(UUID.fromString(c.getUuid()));
                temp.setPaymentName(c.getPaymentName());

                paymentListResponses.addPaymentMethodsItem(temp);
            }

            return new ResponseEntity<PaymentListResponse>(paymentListResponses, HttpStatus.OK);
        }

    }
