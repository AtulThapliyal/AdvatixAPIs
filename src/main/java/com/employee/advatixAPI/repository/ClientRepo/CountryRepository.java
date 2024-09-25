package com.employee.advatixAPI.repository.ClientRepo;

import com.employee.advatixAPI.entity.Client.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Country getCountryByCountryId(Integer countryId);
}
