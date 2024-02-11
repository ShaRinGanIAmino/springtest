package net.springtest.springboot.repository;


import net.springtest.springboot.model.Building;
import net.springtest.springboot.model.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends JpaRepository<Floor,Long> {
    boolean existsByBuildingIdAndFloorNumber(String buildingId, int floorNumber);

    List<Floor> findByBuildingId(String buildingId);
}
