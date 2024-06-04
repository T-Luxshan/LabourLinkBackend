package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.dto.LabourLocationDTO;
import com.intelli5.labourlink.service.LabourLocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labour-locations")
public class LabourLocationsController {

    @Autowired
    private LabourLocationsService labourLocationsService;

    @PostMapping
    public ResponseEntity<LabourLocationDTO> createLabourLocation(@RequestBody LabourLocationDTO labourLocationsDTO) {
        LabourLocationDTO createdLabourLocation = labourLocationsService.createLabourLocation(labourLocationsDTO);
        return ResponseEntity.ok(createdLabourLocation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabourLocationDTO> getLabourLocationById(@PathVariable Long id) {
        LabourLocationDTO labourLocation = labourLocationsService.getLabourLocationById(id);
        return ResponseEntity.ok(labourLocation);
    }

    @GetMapping
    public ResponseEntity<List<LabourLocationDTO>> getAllLabourLocations() {
        List<LabourLocationDTO> labourLocationsList = labourLocationsService.getAllLabourLocations();
        return ResponseEntity.ok(labourLocationsList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabourLocationDTO> updateLabourLocation(@PathVariable Long id, @RequestBody LabourLocationDTO labourLocationsDTO) {
        LabourLocationDTO updatedLabourLocation = labourLocationsService.updateLabourLocation(id, labourLocationsDTO);
        return ResponseEntity.ok(updatedLabourLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabourLocation(@PathVariable Long id) {
        labourLocationsService.deleteLabourLocation(id);
        return ResponseEntity.noContent().build();
    }
}
