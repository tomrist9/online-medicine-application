package com.online.medicine.pharmacy.service.dataaccess.adapter;

import com.online.medicine.application.pharmacy.service.entity.OrderApproval;
import com.online.medicine.application.pharmacy.service.ports.output.repository.OrderApprovalRepository;
import com.online.medicine.pharmacy.service.dataaccess.mapper.PharmacyDataAccessMapper;
import com.online.medicine.pharmacy.service.dataaccess.repository.OrderApprovalJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderApprovalRepositoryImpl implements OrderApprovalRepository {

    private final OrderApprovalJpaRepository orderApprovalJpaRepository;
    private final PharmacyDataAccessMapper pharmacyDataAccessMapper;

    public OrderApprovalRepositoryImpl(OrderApprovalJpaRepository orderApprovalJpaRepository,
                                       PharmacyDataAccessMapper pharmacyDataAccessMapper) {
        this.orderApprovalJpaRepository = orderApprovalJpaRepository;
        this.pharmacyDataAccessMapper = pharmacyDataAccessMapper;
    }

    @Override
    public OrderApproval save(OrderApproval orderApproval) {
        return pharmacyDataAccessMapper
                .orderApprovalEntityToOrderApproval(orderApprovalJpaRepository
                        .save(pharmacyDataAccessMapper.orderApprovalToOrderApprovalEntity(orderApproval)));
    }

}