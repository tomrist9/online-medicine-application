package com.online.medicine.application.pharmacy.service.ports.output.repository;

import com.online.medicine.application.pharmacy.service.entity.OrderApproval;

public interface OrderApprovalRepository {

    OrderApproval save(OrderApproval orderApproval);
}
