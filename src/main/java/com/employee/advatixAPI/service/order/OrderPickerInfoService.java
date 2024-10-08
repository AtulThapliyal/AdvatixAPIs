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

        Optional<FEPOrderInfo> fepOrderInfo = fepOrderRepository.findById(orderPickerDto.getOrderId());

        if(fepOrderInfo.isPresent()){
            OrderPickerInfo orderPickerInfo = new OrderPickerInfo();

            orderPickerInfo.setOrderNumber(fepOrderInfo.get().getOrderNumber());
            orderPickerInfo.setPickerName(orderPickerDto.getPickerName());
            orderPickerInfo.setStatus(true);
            orderPickerInfo.setCreatedOn(LocalDate.now());
            orderPickerInfo.setUpdatedOn(LocalDate.now());

            orderPickerInfoRepository.save(orderPickerInfo);

            fepOrderInfo.get().setStatusId(3);
            fepOrderRepository.save(fepOrderInfo.get());


            return ResponseEntity.ok(orderPickerInfo);
        }
        throw new NotFoundException("The order Id does not exists");

    }
}
