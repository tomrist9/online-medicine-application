package com.online.medicine.application.service.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private final BigDecimal amount;
    public static final Money ZERO = new Money(BigDecimal.ZERO);
    public Money(BigDecimal amount) {
        this.amount = amount;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Money money = (Money) object;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
    public boolean isGreaterThanZero() {
        return this.amount!=null && this.amount.compareTo(BigDecimal.ZERO)>0;
    }
    public boolean isGreaterThan(Money money){
        return this.amount!=null && this.amount.compareTo(money.getAmount())>0;
    }
    public Money add(Money money){
        return new Money(setScale(this.amount.add(money.getAmount())));
    }
    public Money subtract(Money money){
        return new Money(setScale(this.amount.subtract(money.getAmount())));
    }
    public Money multiply(int multiplier) {
        return new Money(setScale(this.amount.multiply(new BigDecimal(multiplier))));
    }
    private BigDecimal setScale(BigDecimal input){
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
