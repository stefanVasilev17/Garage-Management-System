package com.stefan.security.GarageModule.data.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CashReceipt {
    private Client clientInfo;
    private BigDecimal amount;
    private String fixedParts;
    private boolean byFacture;

    public CashReceipt(Client clientInfo, BigDecimal amount, String fixedParts) {
        this.clientInfo = clientInfo;
        this.amount = amount;
        this.fixedParts = fixedParts;
    }
}
