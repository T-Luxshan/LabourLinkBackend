package com.intelli5.labourlink.service;

import com.intelli5.labourlink.dto.LabourLocationDTO;
import com.intelli5.labourlink.entity.JobRole;
import com.intelli5.labourlink.entity.Labour;
import com.intelli5.labourlink.entity.LabourLocations;
import com.intelli5.labourlink.repository.LabourLocationsRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabourLocationsService {

    @Autowired
    private LabourLocationsRepository labourLocationsRepository;

    @Autowired
    private LabourRepository labourRepository;

    @Autowired
    private  LabourReviewService labourReviewService;

    public LabourLocationDTO createLabourLocation(LabourLocationDTO labourLocationsDTO) {
        Labour labour = (Labour) labourRepository.findById(labourLocationsDTO.getLabourId()).orElseThrow(() -> new RuntimeException("Labour not found"));
        LabourLocations labourLocations = LabourLocations.builder()
                .latitude(labourLocationsDTO.getLatitude())
                .longitude(labourLocationsDTO.getLongitude())
                .labour(labour)
                .build();

        try {
            labourLocations = labourLocationsRepository.save(labourLocations);
            return convertToDTO(labourLocations);
        } catch (RuntimeException e) {
            LabourLocations existingLocation = labourLocationsRepository.findByLabour(labour);
            return updateLabourLocation(existingLocation.getId(), labourLocationsDTO);
        } catch (Exception e){
            return new LabourLocationDTO();
        }
    }




    public LabourLocationDTO getLabourLocationById(Long id) {
        LabourLocations labourLocations = labourLocationsRepository.findById(id).orElseThrow(() -> new RuntimeException("LabourLocation not found"));
        return convertToDTO(labourLocations);
    }

    public List<LabourLocationDTO> getAllLabourLocations() {
        List<LabourLocations> labourLocationsList = labourLocationsRepository.findAll();
        return labourLocationsList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public LabourLocationDTO updateLabourLocation(Long id, LabourLocationDTO labourLocationsDTO) {
        LabourLocations labourLocations = labourLocationsRepository.findById(id).orElseThrow(() -> new RuntimeException("LabourLocation not found"));
        labourLocations.setLatitude(labourLocationsDTO.getLatitude());
        labourLocations.setLongitude(labourLocationsDTO.getLongitude());
        labourLocations = labourLocationsRepository.save(labourLocations);
        return convertToDTO(labourLocations);
    }

    public void deleteLabourLocation(Long id) {
        labourLocationsRepository.deleteById(id);
    }





    public List<LabourLocationDTO> findLocationsByJobRole(JobRole jobRole) {
        List<LabourLocations> locations = labourLocationsRepository.findByLabourJobRole(jobRole);
//        Double rating;
        return locations.stream()
                .map(labourLocation -> {
                    LabourLocationDTO dto = new LabourLocationDTO();
                    dto.setId(labourLocation.getId());
                    dto.setLatitude(labourLocation.getLatitude());
                    dto.setLongitude(labourLocation.getLongitude());
                    dto.setLabourId(labourLocation.getLabour().getEmail()); // Set Labour ID instead of email
                    dto.setLabourName(labourLocation.getLabour().getName());
                    Double rating = labourReviewService.getRating(labourLocation.getLabour().getEmail());
                    dto.setRating(rating != null ? rating : 0.0);
                    return dto;

                })
                .collect(Collectors.toList());
    }

    private LabourLocationDTO convertToDTO(LabourLocations labourLocations) {
        return LabourLocationDTO.builder()
                .id(labourLocations.getId())
                .latitude(labourLocations.getLatitude())
                .longitude(labourLocations.getLongitude())
                .labourId(labourLocations.getLabour().getEmail())
                .build();
    }
}