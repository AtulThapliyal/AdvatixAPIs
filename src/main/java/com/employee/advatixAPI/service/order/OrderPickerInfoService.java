package com.employee.advatixAPI.service.order;

import com.employee.advatixAPI.dto.order.OrderPickerDto;
import com.employee.advatixAPI.entity.Order.FEPOrderInfo;
import com.employee.advatixAPI.entity.Order.OrderPickerInfo;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.Order.FEPOrderRepository;
import com.employee.advatixAPI.repository.Order.OrderPickerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OrderPickerInfoService {
    @Autowired
    OrderPickerInfoRepository orderPickerInfoRepository;

    @Autowired
    FEPOrderRepository fepOrderRepository;


    public ResponseEntity<?> assignPicker(OrderPickerDto orderPickerDto) {

        //find the order with the order id in fep layer.
        FEPOrderInfo fepOrderInfo = fepOrderRepository.findById(orderPickerDto.getOrderId()).orElseThrow(() -> new NotFoundException("No id found with " + orderPickerDto.getOrderId()));

        //get the order information if already present in order picker table
        Optional<OrderPickerInfo> orderPickerInformation = orderPickerInfoRepository.findByOrderNumber(fepOrderInfo.getOrderNumber());

       //if present update the updated on day and picker name as all other fields can not be changed.
        if (orderPickerInformation.isPresent()) {
            orderPickerInformation.get().setPickerName(orderPickerDto.getPickerName());
            orderPickerInformation.get().setUpdatedOn(LocalDate.now());

            orderPickerInfoRepository.save(orderPickerInformation.get());
            return ResponseEntity.ok(orderPickerInformation);

        }

        //if no order found in order picker generate a new record and insert into it
        OrderPickerInfo orderPickerInfo = new OrderPickerInfo();

        orderPickerInfo.setOrderNumber(fepOrderInfo.getOrderNumber());
        orderPickerInfo.setPickerName(orderPickerDto.getPickerName());
        orderPickerInfo.setStatus(true);
        orderPickerInfo.setCreatedOn(LocalDate.now());
        orderPickerInfo.setUpdatedOn(LocalDate.now());

        orderPickerInfoRepository.save(orderPickerInfo);

        //change the status id to 3 that is picked.
        fepOrderInfo.setStatusId(3);
        fepOrderRepository.save(fepOrderInfo);

        return ResponseEntity.ok(orderPickerInfo);
    }
}
