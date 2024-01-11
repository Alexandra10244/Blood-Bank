package com.bpa.bloodbank.service.interfaces;

import com.bpa.bloodbank.models.dtos.CenterDTO;
import com.bpa.bloodbank.models.dtos.EditedCenterDTO;

import java.util.List;

public interface CenterService {
    CenterDTO createCenter(CenterDTO centerDTO);
    List<CenterDTO> getAllCenters();
    CenterDTO updateCenter(Long id, EditedCenterDTO centerDTO);
    String deleteCenter(Long id);
    CenterDTO findCenterById(Long id);
    CenterDTO findCenterByName(String centerName);
    CenterDTO findCenterByEmail(String email);
}
