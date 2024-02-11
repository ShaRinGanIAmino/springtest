package net.springtest.springboot.controller;


import net.springtest.springboot.exception.ResourceNotFoundException;
import net.springtest.springboot.model.Building;
import net.springtest.springboot.model.Floor;
import net.springtest.springboot.repository.BuildingRepository;
import net.springtest.springboot.repository.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buildings")
public class BuildingController {
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private FloorRepository floorRepository;



    @GetMapping
    public List<Building> getAllBuildings(){
        return buildingRepository.findAll();
    }


    @PostMapping
    public Building createBuilding(@RequestBody Building building){

        buildingRepository.save(building);
        return buildingRepository.save(building);
    }

    @GetMapping("{id}")
    public ResponseEntity<Building> getBuildingByID(@PathVariable long id){
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building with id : "+id+"is not found !"));
        return ResponseEntity.ok(building);
    }

    @PutMapping("{id}")
    public ResponseEntity<Building> updateBuilding(@PathVariable long id ,@RequestBody Building buildingUpdates){
        Building updatedBuilding = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building with id : "+id+"is not found !"));


        updatedBuilding.setBuildingName(buildingUpdates.getBuildingName());
        updatedBuilding.setBuildingLocalization(buildingUpdates.getBuildingLocalization());


        buildingRepository.save(updatedBuilding);
        return ResponseEntity.ok(updatedBuilding);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteBuilding(@PathVariable long id ){
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building with id : "+id+"is not found !"));


        List<Floor> floors = floorRepository.findByBuildingId(String.valueOf(id));

        floorRepository.deleteAll(floors);

        buildingRepository.delete(building);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
