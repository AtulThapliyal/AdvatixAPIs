package com.employee.advatixAPI.service;

import com.employee.advatixAPI.dto.ClientResponse;
import com.employee.advatixAPI.entity.Client.City;
import com.employee.advatixAPI.entity.Client.ClientInfo;
import com.employee.advatixAPI.entity.Client.Country;
import com.employee.advatixAPI.entity.Client.States;
import com.employee.advatixAPI.repository.ClientRepo.CityRepository;
import com.employee.advatixAPI.repository.ClientRepo.ClientRepository;
import com.employee.advatixAPI.repository.ClientRepo.CountryRepository;
import com.employee.advatixAPI.repository.ClientRepo.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    CityRepository cityRepository;

    public ClientResponse getClientById(Integer clientId) {
        ClientResponse clientResponse = new ClientResponse();
        Optional<ClientInfo> clientInfo = clientRepository.getClientByClientId(clientId);
        if (clientInfo.isPresent()){
            Country country = countryRepository.getCountryByCountryId(clientInfo.get().getCountryId());
            States state = stateRepository.getStateByStateIdAndCountryId(clientInfo.get().getStateId(),country.getCountryId());
            City city = cityRepository.getCityByStateIdAndCountryId(state.getStateId(), country.getCountryId());

            clientResponse.setClient(clientInfo.get());
            clientResponse.setCountry(country);
            clientResponse.setStates(state);
            clientResponse.setCity(city);

            return clientResponse;
        }
        return null;
    }
}
