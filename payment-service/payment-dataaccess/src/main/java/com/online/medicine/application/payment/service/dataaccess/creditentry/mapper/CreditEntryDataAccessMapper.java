package com.online.medicine.application.payment.service.dataaccess.creditentry.mapper;

import com.online.medicine.application.payment.service.dataaccess.creditentry.entity.CreditEntryEntity;
import com.online.medicine.application.entity.CreditEntry;
import com.online.medicine.application.order.service.domain.valueobject.CustomerId;
import com.online.medicine.application.order.service.domain.valueobject.Money;
import com.online.medicine.application.valueobject.CreditEntryId;
import org.springframework.stereotype.Component;

@Component
public class CreditEntryDataAccessMapper {

    public CreditEntry creditEntryEntityToCreditEntry(CreditEntryEntity creditEntryEntity) {
        return CreditEntry.builder()
                .creditEntryId(new CreditEntryId(creditEntryEntity.getId()))
                .customerId(new CustomerId(creditEntryEntity.getCustomerId()))
                .totalCreditAmount(new Money(creditEntryEntity.getTotalCreditAmount()))
                .build();
    }

    public CreditEntryEntity creditEntryToCreditEntryEntity(CreditEntry creditEntry) {
        return CreditEntryEntity.builder()
                .id(creditEntry.getId().getValue())
                .customerId(creditEntry.getCustomerId().getValue())
                .totalCreditAmount(creditEntry.getTotalCreditAmount().getAmount())
                .build();
    }

}
