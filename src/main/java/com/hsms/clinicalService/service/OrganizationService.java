package com.hsms.clinicalService.service;

import com.hsms.clinicalService.dto.OrganizationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrganizationService {
  Flux<OrganizationDTO> findOrganizations(Integer pageNumber,Integer pageSize);

  Mono<OrganizationDTO> addOrganization(OrganizationDTO organizationDTO);

  Mono<OrganizationDTO> findOrganization(Long id);

  Mono<OrganizationDTO> updateOrganization(Long id, OrganizationDTO organizationDTO);

  Mono<Void> deleteOrganization(Long id);
}
