package com.online.medicine.application.pharmacy.service;

import com.online.medicine.application.pharmacy.service.entity.Pharmacy;
import com.online.medicine.application.pharmacy.service.event.OrderApprovalEvent;

import java.util.List;

public interface PharmacyDomainService {

    OrderApprovalEvent validateOrder(Pharmacy pharmacy,
                                     List<String> failureMessages);
}
