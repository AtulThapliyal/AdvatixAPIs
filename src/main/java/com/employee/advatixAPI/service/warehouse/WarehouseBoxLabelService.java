package com.employee.advatixAPI.service.warehouse;

import com.employee.advatixAPI.dto.warehouseBox.*;
import com.employee.advatixAPI.entity.Address.City;
import com.employee.advatixAPI.entity.Address.Country;
import com.employee.advatixAPI.entity.Address.States;
import com.employee.advatixAPI.entity.Order.FEPOrderInfo;
import com.employee.advatixAPI.entity.Order.OrderPickerInfo;
import com.employee.advatixAPI.entity.warehouse.Warehouse;
import com.employee.advatixAPI.entity.warehouse.WarehouseAddressEntity;
import com.employee.advatixAPI.entity.warehouse.WarehouseBox;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.ClientRepo.CityRepository;
import com.employee.advatixAPI.repository.ClientRepo.CountryRepository;
import com.employee.advatixAPI.repository.ClientRepo.StateRepository;
import com.employee.advatixAPI.repository.Order.FEPOrderRepository;
import com.employee.advatixAPI.repository.Order.OrderPickerInfoRepository;
import com.employee.advatixAPI.repository.Warehouse.WarehouseAddressRepository;
import com.employee.advatixAPI.repository.Warehouse.WarehouseBoxLabelRepository;
import com.employee.advatixAPI.repository.Warehouse.WhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseBoxLabelService {
    @Autowired
    WarehouseBoxLabelRepository warehouseBoxLabelRepository;

    @Autowired
    WhRepository whRepository;

    @Autowired
    OrderPickerInfoRepository orderPickerInfoRepository;

    @Autowired
    FEPOrderRepository fepOrderRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    WarehouseAddressRepository warehouseAddressRepository;

    public ResponseEntity<?> generateLabelsLists(BoxRequest boxRequest) {
        Pageable pageable = PageRequest.of(0, boxRequest.getQuantity());

        if (!whRepository.existsById(boxRequest.getWarehouseId())) {
            throw new NotFoundException("The warehouse with warehouse id " + boxRequest.getWarehouseId() + " does not exists");
        }

        List<WarehouseBox> warehouseBoxList = warehouseBoxLabelRepository.findByBoxTypeAndWarehouseIdAndStatus(boxRequest.getBoxType(), boxRequest.getWarehouseId(), true, pageable);

        if (boxRequest.getQuantity() > warehouseBoxList.size()) {
            throw new NotFoundException("There are only " + warehouseBoxList.size() + " boxes present in warehouse");
        }

        WarehouseBoxResponse warehouseBoxResponse = new WarehouseBoxResponse();
        List<String> boxIds = new ArrayList<>();

        for (int i = 0; i < warehouseBoxList.size(); i++) {
            WarehouseBox warehouseBox = warehouseBoxList.get(i);
            warehouseBox.setStatus(false);


            boxIds.add(warehouseBox.getBoxLabel());
            warehouseBoxLabelRepository.save(warehouseBox);
        }

        warehouseBoxResponse.setBoxLabels(boxIds);

        return ResponseEntity.ok(warehouseBoxResponse);
    }

    public ResponseEntity<BoxLabelResponse> getByBoxLabel(String labelId) {
        BoxLabelResponse boxLabelResponse = new BoxLabelResponse();

        List<OrderPickerInfo> orderInfo = orderPickerInfoRepository.findAllByBoxId(labelId);
        if(orderInfo== null){
            throw new NotFoundException("No box found");
        }


        boxLabelResponse.setOrderNumber(orderInfo.get(0).getOrderNumber());

        Optional<FEPOrderInfo> fepOrderInfo = fepOrderRepository.findByOrderNumber(orderInfo.get(0).getOrderNumber());
        if (fepOrderInfo.isPresent()) {
            Optional<WarehouseAddressEntity> warehouseAddress;

            if (fepOrderInfo.get().getWarehouseId() != null) {
                warehouseAddress = warehouseAddressRepository.findById(fepOrderInfo.get().getWarehouseId());
            } else {
                throw new NotFoundException("No warehouse added");
            }

            Warehouse warehouse = whRepository.findById(warehouseAddress.get().getWarehouseId()).get();
            boxLabelResponse.setServiceType(fepOrderInfo.get().getServiceType());
            boxLabelResponse.setCarrierType(fepOrderInfo.get().getCarrierName());

            List<City> cities = cityRepository.findAll();
            List<Country> countries = countryRepository.findAll();
            List<States> states = stateRepository.findAll();

            HashMap<Integer, String> citiesHash = new HashMap<>();
            HashMap<Integer, String> countryHash = new HashMap<>();
            HashMap<Integer, String> stateHash = new HashMap<>();

            cities.forEach(city -> {
                citiesHash.put(city.getCityId(), city.getCityName());
            });
            countries.forEach(country -> countryHash.put(country.getCountryId(), country.getCountryName()));
            states.forEach(state -> stateHash.put(state.getStateId(), state.getStateName()));

            boxLabelResponse.setShipToAddress(getShipToAddress(citiesHash, countryHash, stateHash, fepOrderInfo));

            boxLabelResponse.setShipFromAddress(getShipFromAddress(citiesHash, countryHash, stateHash, fepOrderInfo, warehouseAddress, warehouse));

            return ResponseEntity.ok(boxLabelResponse);
        }
        throw new NotFoundException("No fep order found");
    }

    private ShipFromAddress getShipFromAddress(HashMap<Integer, String> citiesHash, HashMap<Integer, String> countryHash, HashMap<Integer, String> stateHash, Optional<FEPOrderInfo> fepOrderInfo, Optional<WarehouseAddressEntity> warehouseAddress, Warehouse warehouse) {

        ShipFromAddress shipFromAddress = new ShipFromAddress();

        String shipFromCity = citiesHash.get(warehouseAddress.get().getCityId());
        String shipFromState = stateHash.get(warehouseAddress.get().getStateId());
        String shipFromCountry = countryHash.get(warehouseAddress.get().getCountryId());

        shipFromAddress.setCity(shipFromCity);
        shipFromAddress.setShipFromAddress(warehouseAddress.get().getAddress1());
        shipFromAddress.setShipFromName(warehouse.getWarehouseName());
        shipFromAddress.setCountry(shipFromCountry);
        shipFromAddress.setState(shipFromState);
        return shipFromAddress;
    }

    private ShipToAddress getShipToAddress(HashMap<Integer, String> citiesHash, HashMap<Integer, String> countryHash, HashMap<Integer, String> stateHash, Optional<FEPOrderInfo> fepOrderInfo) {

        ShipToAddress shipToAddress = new ShipToAddress();

        String city = citiesHash.get(fepOrderInfo.get().getShipToCityId());
        String country = countryHash.get(fepOrderInfo.get().getShipToCountryId());
        String state = stateHash.get(fepOrderInfo.get().getShipToStateId());
        shipToAddress.setCity(city);
        shipToAddress.setState(state);
        shipToAddress.setShipToName(fepOrderInfo.get().getShipToName());
        shipToAddress.setShipToName(fepOrderInfo.get().getShipToName());
        shipToAddress.setCountry(country);
        shipToAddress.setShipToAddress(fepOrderInfo.get().getShipToAddress());

        return shipToAddress;
    }

}
