package com.hsms.clinicalService.mapper;

import com.hsms.clinicalService.dto.OrganizationDTO;
import com.hsms.clinicalService.entity.Organization;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationMapper {
  private final ModelMapper modelMapper;

  public Organization toEntity(OrganizationDTO organizationDTO) {
    return modelMapper.map(organizationDTO, Organization.class);
  }

  public OrganizationDTO toDTO(Organization organization) {
    return modelMapper.map(organization, OrganizationDTO.class);
  }
}
