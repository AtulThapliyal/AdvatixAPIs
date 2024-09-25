package com.employee.advatixAPI.service.product;

import com.employee.advatixAPI.repository.product.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributeService {
    @Autowired
    AttributeRepository attributeRepository;
}
