package com.bpa.bloodbank.service.implementations;

import com.bpa.bloodbank.exceptions.CenterNotFoundException;
import com.bpa.bloodbank.models.dtos.CenterDTO;
import com.bpa.bloodbank.models.dtos.EditedCenterDTO;
import com.bpa.bloodbank.models.entities.Address;
import com.bpa.bloodbank.models.entities.Center;
import com.bpa.bloodbank.repositories.CenterRepository;
import com.bpa.bloodbank.service.interfaces.CenterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletionException;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;
    private final ObjectMapper objectMapper;

    @Override
    public CenterDTO createCenter(CenterDTO centerDTO) {
        Center center = Center.builder()
                .email(centerDTO.getEmail())
                .phoneNumber(centerDTO.getPhoneNumber())
                .centerName(centerDTO.getCenterName())
                .address(objectMapper.convertValue(centerDTO.getAddress(), Address.class))
                .build();

        return objectMapper.convertValue(centerRepository.save(center), CenterDTO.class);
    }

    @Override
    public List<CenterDTO> getAllCenters() {
        List<Center> centers = centerRepository.findAll();

        return centers.stream()
                .map(center->objectMapper.convertValue(center,CenterDTO.class))
                .toList();
    }

    @Override
    public CenterDTO updateCenter(Long id, EditedCenterDTO centerDTO) {
        Center center = centerRepository.findById(id).orElseThrow(()-> new CenterNotFoundException("Center not found!"));

        if(centerDTO.getEmail() != null && !centerDTO.getEmail().isEmpty()){
            center.setEmail(centerDTO.getEmail());
        }
            if(centerDTO.getPhoneNumber() != null && !centerDTO.getPhoneNumber().isEmpty()){
            center.setPhoneNumber(centerDTO.getPhoneNumber());
        }
        if(centerDTO.getCenterName() != null && !centerDTO.getCenterName().isEmpty()){
            center.setCenterName(centerDTO.getCenterName());
        }

        Center updateCenter = centerRepository.save(center);

        return objectMapper.convertValue(updateCenter,CenterDTO.class);
    }

    @Override
    public String deleteCenter(Long id) {

        if(centerRepository.existsById(id)){
            centerRepository.deleteById(id);

            return "Center "+ id + " successfully deleted!";
        } else {
            throw new CenterNotFoundException("Center not found!");
        }
    }

    @Override
    public CenterDTO findCenterById(Long id) {
        Center center = centerRepository
                .findById(id)
                .orElseThrow(()-> new CenterNotFoundException("Center not found!"));

        return objectMapper.convertValue(center,CenterDTO.class);
    }

    @Override
    public CenterDTO findCenterByName(String centerName) {
        Center center = centerRepository
                .findCenterByNameIgnoreCase(centerName)
                .orElseThrow(()-> new CenterNotFoundException("Center not found!"));

        return objectMapper.convertValue(center,CenterDTO.class);
    }

    @Override
    public CenterDTO findCenterByEmail(String email) {
        Center center = centerRepository
                .findByEmail(email)
                .orElseThrow(() -> new CenterNotFoundException("Center not found!"));

        return objectMapper.convertValue(center,CenterDTO.class);
    }
}
