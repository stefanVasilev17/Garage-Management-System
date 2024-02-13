package com.stefan.security.GarageModule.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditContractInfo extends CashReceipt {
    @Min(0)
    private BigDecimal monthlyPayment;
    @Min(0)
    private BigDecimal maxMonthlyPayment;
    private int creditDuration;

    public CreditContractInfo(Client clientInfo,
                              BigDecimal amount,
                              String fixedParts,
                              boolean byFacture,
                              BigDecimal monthlyPayment,
                              BigDecimal maxMonthlyPayment,
                              int creditDuration) {
        super(clientInfo, amount, fixedParts, byFacture);
        this.monthlyPayment = monthlyPayment;
        this.maxMonthlyPayment = maxMonthlyPayment;
        this.creditDuration = creditDuration;
    }
}
