package com.hsms.clinicalService.mapper;

import com.hsms.clinicalService.dto.HospitalDTO;
import com.hsms.clinicalService.entity.Hospital;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HospitalMapper {
  private final ModelMapper modelMapper;

  public HospitalDTO toDTO(Hospital hospital) {
    return modelMapper.map(hospital, HospitalDTO.class);
  }

  public Hospital toEntity(HospitalDTO hospitalDTO) {
    return modelMapper.map(hospitalDTO, Hospital.class);
  }
}
