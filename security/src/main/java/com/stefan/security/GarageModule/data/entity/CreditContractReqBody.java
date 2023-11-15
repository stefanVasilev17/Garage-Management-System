package com.stefan.security.GarageModule.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditContractReqBody {

    private CreditDuration creditDuration;
    private long clientId;
    private BigDecimal clientSalary;
}
