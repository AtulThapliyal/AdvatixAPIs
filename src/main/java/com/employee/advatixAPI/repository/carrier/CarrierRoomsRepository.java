package com.employee.advatixAPI.repository.carrier;

import com.employee.advatixAPI.entity.Carrier.CarrierRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrierRoomsRepository extends JpaRepository<CarrierRooms, Integer> {
//    Optional<CarrierRooms> findByCarrierId(Integer carrierId);

    Optional<CarrierRooms> findByRoomId(Integer roomId);
}
