package com.stefan.security.GarageModule.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class CashReceipt {
    @JsonIgnoreProperties("garage")
    private Client clientInfo;
    @Min(0)
    private BigDecimal amount;
    private String fixedParts;
    private boolean byFacture;

    public CashReceipt(Client clientInfo, BigDecimal amount, String fixedParts) {
        this.clientInfo = clientInfo;
        this.amount = amount;
        this.fixedParts = fixedParts;
    }
}
