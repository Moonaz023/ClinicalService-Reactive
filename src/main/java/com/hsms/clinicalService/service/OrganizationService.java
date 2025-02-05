package com.hsms.clinicalService.service;

import com.hsms.clinicalService.dto.OrganizationDTO;
import com.hsms.clinicalService.entity.Organization;
import java.util.Optional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrganizationService {
  Flux<OrganizationDTO> findOrganizations(Integer pageNumber,Integer pageSize);

  Mono<OrganizationDTO> addOrganization(OrganizationDTO organizationDTO);

  Mono<OrganizationDTO> updateOrganization(long id, OrganizationDTO organizationDTO);

  Mono<Void> deleteOrganization(long id);
}
