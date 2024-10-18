package com.employee.advatixAPI.service.warehouse;

import com.employee.advatixAPI.dto.ShipmentLabel.*;
import com.employee.advatixAPI.dto.warehouseBox.*;
import com.employee.advatixAPI.entity.Address.City;
import com.employee.advatixAPI.entity.Address.Country;
import com.employee.advatixAPI.entity.Address.States;
import com.employee.advatixAPI.entity.Order.FEPOrderInfo;
import com.employee.advatixAPI.entity.Order.OrderPickerInfo;
import com.employee.advatixAPI.entity.ShipmentLabels.BoxEntity;
import com.employee.advatixAPI.entity.warehouse.Warehouse;
import com.employee.advatixAPI.entity.warehouse.WarehouseAddressEntity;
import com.employee.advatixAPI.entity.warehouse.WarehouseBox;
import com.employee.advatixAPI.exception.NotFoundException;
import com.employee.advatixAPI.repository.BoxLabel.BoxLabelRepository;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
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

    @Autowired
    BoxLabelRepository boxLabelRepository;


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
        if (orderInfo == null) {
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
                citiesHash.put(city.getCityId(), city.getCityCode());
            });
            countries.forEach(country -> countryHash.put(country.getCountryId(), country.getCountryCode()));
            states.forEach(state -> stateHash.put(state.getStateId(), state.getStateCode()));

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
        shipFromAddress.setPhoneNumber(warehouseAddress.get().getPhoneNumber());
        shipFromAddress.setEmailId(warehouseAddress.get().getEmailId());
        shipFromAddress.setIsResidential(warehouseAddress.get().getIsResidential());
        shipFromAddress.setPostalCode(warehouseAddress.get().getPostalCode());

        return shipFromAddress;
    }

    private ShipToAddress getShipToAddress(HashMap<Integer, String> citiesHash, HashMap<Integer, String> countryHash, HashMap<Integer, String> stateHash, Optional<FEPOrderInfo> fepOrderInfo) {

        ShipToAddress shipToAddress = new ShipToAddress();

        String city = citiesHash.get(fepOrderInfo.get().getShipToCityId());
        String country = countryHash.get(fepOrderInfo.get().getShipToCountryId());
        String state = stateHash.get(fepOrderInfo.get().getShipToStateId());
        shipToAddress.setCity(city);
        shipToAddress.setState(state);
        shipToAddress.setCountry(country);
        shipToAddress.setShipToName(fepOrderInfo.get().getShipToName());
        shipToAddress.setShipToAddress(fepOrderInfo.get().getShipToAddress());
        shipToAddress.setPhoneNumber(fepOrderInfo.get().getPhone());
        shipToAddress.setEmailId(fepOrderInfo.get().getEmail());
        shipToAddress.setIsResidential(fepOrderInfo.get().getIsResidential());
        shipToAddress.setPostalCode(fepOrderInfo.get().getPostalCode());

        return shipToAddress;
    }

    public ResponseEntity<?> generateBoxLabel(BoxRequestDto boxRequest) {
        BoxEntity box = new BoxEntity();
        box.setOrderNumber(boxRequest.getOrderNumber());
        box.setBoxLabel(boxRequest.getBoxLabel());
        box.setLength(boxRequest.getDimensions().getLength());
        box.setHeight(boxRequest.getDimensions().getHeight());
        box.setWidth(boxRequest.getDimensions().getWidth());
        box.setBoxWeight(boxRequest.getBoxWeight());
        boxLabelRepository.save(box);

        ShipmentRequestDto shipmentRequestDto = new ShipmentRequestDto();
        ShipmentDTO shipmentDTO = new ShipmentDTO();
        List<ParcelDTO> parcesList = new ArrayList<>();

        ResponseEntity<BoxLabelResponse> boxLabelResponseResponseEntity = getByBoxLabel(boxRequest.getBoxLabel());
        shipmentDTO.setExternal_reference(box.getOrderNumber());

        ShippingAddress shippingAddress = generateShippingToAddress(boxLabelResponseResponseEntity.getBody().getShipToAddress());
        ShippingAddress shipperAddress = generateShippingAddress(boxLabelResponseResponseEntity.getBody().getShipFromAddress());

        ParcelDTO parcelDTO = new ParcelDTO();
        DimensionsDTO dimensionsDTO = new DimensionsDTO();
        WeightDTO weightDTO = new WeightDTO();

        dimensionsDTO.setHeight(boxRequest.getDimensions().getHeight());
        dimensionsDTO.setLength(boxRequest.getDimensions().getLength());
        dimensionsDTO.setWidth(boxRequest.getDimensions().getWidth());

        weightDTO.setValue(boxRequest.getBoxWeight());
        weightDTO.setUnit("Ounce");

        parcelDTO.setDimensions(dimensionsDTO);
        parcelDTO.setWeight(weightDTO);

        parcesList.add(parcelDTO);

        shipmentDTO.setAddress_to(shippingAddress);
        shipmentDTO.setAddress_from(shipperAddress);
        shipmentDTO.setParcels(parcesList);

        shipmentRequestDto.setShipment(shipmentDTO);
        System.out.println(shipmentRequestDto);

        try {
            return new ResponseEntity<>(generateShipmentLabel(shipmentRequestDto), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);

        }
        return new ResponseEntity<>(shipmentDTO, HttpStatus.OK);
    }

    private ShippingAddress generateShippingToAddress(ShipToAddress shipToAddress) {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setName(shipToAddress.getShipToName());
        shippingAddress.setCompany("");
        shippingAddress.setEmail(shipToAddress.getEmailId());
        shippingAddress.setPostal_code(shipToAddress.getPostalCode());
        shippingAddress.setPhone(shipToAddress.getPhoneNumber());
        shippingAddress.setStreet2("");
        shippingAddress.setCountry(shipToAddress.getCountry());
        shippingAddress.setCity(shipToAddress.getCity());
        shippingAddress.setState(shipToAddress.getState());
        shippingAddress.setIs_residential(shipToAddress.getIsResidential());
        shippingAddress.setStreet1(shipToAddress.getShipToAddress());
        ;
        return shippingAddress;
    }

    private ShippingAddress generateShippingAddress(ShipFromAddress shipFromAddress) {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setName(shipFromAddress.getShipFromName());
        shippingAddress.setCompany(shipFromAddress.getShipFromName());
        shippingAddress.setEmail(shipFromAddress.getEmailId());
        shippingAddress.setPostal_code(shipFromAddress.getPostalCode());
        shippingAddress.setPhone(shipFromAddress.getPhoneNumber());
        shippingAddress.setCountry(shipFromAddress.getCountry());
        shippingAddress.setCity(shipFromAddress.getCity());
        shippingAddress.setState(shipFromAddress.getState());
        shippingAddress.setStreet2("");
        shippingAddress.setIs_residential(shipFromAddress.getIsResidential());
        shippingAddress.setStreet1(shipFromAddress.getShipFromAddress());
        return shippingAddress;
    }


    private ShipmentResponseDto generateShipmentLabel(ShipmentRequestDto shipmentRequest) throws URISyntaxException {
        URI uri = new URI("https://apisandbox.tusklogistics.com/v1/labels");
        RestTemplate restTemplate = new RestTemplate();

        ShipmentResponseDto shipmentResponseDto = new ShipmentResponseDto();
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/json");
        headers.set("x-api-key", "7Du28nKx66p6PloG9iGz9Vbg9PZINZCuIUXdahH5");

        HttpEntity<ShipmentRequestDto> httpEntity = new HttpEntity<>(shipmentRequest, headers);

        ResponseEntity<ShipmentResponseDto> result = restTemplate.postForEntity(uri, httpEntity, ShipmentResponseDto.class);
        System.out.println(result.getBody());
        return result.getBody();
    }
}
