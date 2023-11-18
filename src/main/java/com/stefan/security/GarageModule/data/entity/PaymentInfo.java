package com.stefan.security.GarageModule.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo <T> {
    private T paymentMethod; //cash,debit/credit
}
