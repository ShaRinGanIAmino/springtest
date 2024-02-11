package net.springtest.springboot.controller;


import net.springtest.springboot.exception.FloorNumberConflictException;
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
@RequestMapping("/api/v1/floors")
public class FloorController {

    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    @GetMapping
    public List<Floor> getAllFloors(){
        return floorRepository.findAll();
    }

    @PostMapping
    public Floor createFloor(@RequestBody Floor floor){

        Building building = buildingRepository.findById(Long.valueOf(floor.getBuildingId()))
                .orElseThrow(() -> new ResourceNotFoundException("Building with id: " + floor.getBuildingId() + " not found"));

        if (floorRepository.existsByBuildingIdAndFloorNumber(floor.getBuildingId(), floor.getFloorNumber())) {
            throw new FloorNumberConflictException("Floor with number " + floor.getFloorNumber() + " already exists in the building");
        }

        floorRepository.save(floor);
        return floorRepository.save(floor);
    }

    @GetMapping("{id}")
    public ResponseEntity<Floor> getFLoorByID(@PathVariable long id){
       Floor floor = floorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Floor with id : "+id+"is not found !"));
        return ResponseEntity.ok(floor);
    }

    @GetMapping("/building/{buildingId}")
    public ResponseEntity<List<Floor>> getFloorsByBuildingId(@PathVariable String buildingId) {

        List<Floor> floors = floorRepository.findByBuildingId(buildingId);

        if (floors.isEmpty()) {
            return null;
        }
        return ResponseEntity.ok(floors);
    }

    @PutMapping("{id}")
    public ResponseEntity<Floor> updateFloor(@PathVariable long id ,@RequestBody Floor floorUpdates){
        Floor updatedFloor = floorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Floor with id : "+id+"is not found !"));

        Building building = buildingRepository.findById(Long.valueOf(floorUpdates.getBuildingId()))
                .orElseThrow(() -> new ResourceNotFoundException("Building with id: " + floorUpdates.getBuildingId() + " not found"));



        updatedFloor.setFloorState(floorUpdates.getFloorState());


        floorRepository.save(updatedFloor);
        return ResponseEntity.ok(updatedFloor);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteFloor(@PathVariable long id ){
        Floor floor = floorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Floor with id : "+id+"is not found !"));


        floorRepository.delete(floor);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
